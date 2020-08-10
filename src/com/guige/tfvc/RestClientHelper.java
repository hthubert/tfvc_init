// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.

package com.guige.tfvc;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.guige.tfvc.utils.SystemHelper;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.VersionInfo;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.glassfish.jersey.client.spi.ConnectorProvider;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * JAXRS Rest Client helper
 */
public class RestClientHelper {
    public enum Type {VSO_DEPLOYMENT, VSO, TFS}

    private static SSLContext mySslContext;
    private synchronized static SSLContext getSslContext() {
        if (mySslContext == null) {
            mySslContext = getSystemSslContext();
        }
        return mySslContext;
    }

    private static SSLContext getSystemSslContext() {
        // NOTE: SSLContext.getDefault() should not be called because it automatically creates
        // default context which can't be initialized twice
        try {
            // actually TLSv1 support is mandatory for Java platform
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            return context;
        }
        catch (NoSuchAlgorithmException e) {
            //LOG.error(e);
            throw new AssertionError("Cannot get system SSL context");
        }
        catch (KeyManagementException e) {
            //LOG.error(e);
            throw new AssertionError("Cannot initialize system SSL context");
        }
    }

    private static Client createNewClient(ClientConfig clientConfig) {
        return ClientBuilder.newBuilder()
                .withConfig(clientConfig)
                .sslContext(getSslContext())
                .build();
    }

    public static Client getClient(final String serverUri, final String accessTokenValue) {
        final Credentials credentials = new UsernamePasswordCredentials("accessToken", accessTokenValue);
        final ClientConfig clientConfig = getClientConfig(Type.VSO_DEPLOYMENT, credentials, serverUri);

        return createNewClient(clientConfig);
    }

    public static Client getClient(final Type type, final AuthenticationInfo authenticationInfo) {
        final ClientConfig clientConfig = getClientConfig(type, authenticationInfo);
        return createNewClient(clientConfig);
    }

    public static ClientConfig getClientConfig(final Type type,
                                               final Credentials credentials,
                                               final String serverUri) {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        final ConnectorProvider connectorProvider = new ApacheConnectorProvider();
        // custom json provider ignores new fields that aren't recognized
        final JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final ClientConfig clientConfig = new ClientConfig(jacksonJsonProvider).connectorProvider(connectorProvider);
        clientConfig.property(ApacheClientProperties.CREDENTIALS_PROVIDER, credentialsProvider);
        clientConfig.property(ClientProperties.REQUEST_ENTITY_PROCESSING, RequestEntityProcessing.BUFFERED);

        // For TFS OnPrem we only support NTLM authentication right now. Since 2016 servers support Basic as well,
        // we need to let the server and client negotiate the protocol instead of preemptively assuming Basic.
        // TODO: This prevents PATs from being used OnPrem. We need to fix this soon to support PATs onPrem.
        clientConfig.property(ApacheClientProperties.PREEMPTIVE_BASIC_AUTHENTICATION, type != Type.TFS);

        // register a filter to set the User Agent header
        clientConfig.register(new ClientRequestFilter() {
            @Override
            public void filter(final ClientRequestContext requestContext) throws IOException {
                // The default user agent is something like "Jersey/2.6"
                final String userAgent = VersionInfo.getUserAgent("Apache-HttpClient", "org.apache.http.client", HttpClientBuilder.class);
                // Finally, we can add the header
                requestContext.getHeaders().add(HttpHeaders.USER_AGENT, userAgent);
            }
        });

        return clientConfig;
    }

    /**
     * Returns an NTCredentials object for given username and password
     *
     * @param userName
     * @param password
     * @return
     */
    public static NTCredentials getNTCredentials(final String userName, final String password) {
        assert userName != null;
        assert password != null;

        String user = userName;
        String domain = "";
        final String workstation = SystemHelper.getComputerName();

        // If the username has a backslash, then the domain is the first part and the username is the second part
        if (userName.contains("\\")) {
            String[] parts = userName.split("[\\\\]");
            if (parts.length == 2) {
                domain = parts[0];
                user = parts[1];
            }
        } else if (userName.contains("/")) {
            // If the username has a slash, then the domain is the first part and the username is the second part
            String[] parts = userName.split("[/]");
            if (parts.length == 2) {
                domain = parts[0];
                user = parts[1];
            }
        } else if (userName.contains("@")) {
            // If the username has an asterisk, then the domain is the second part and the username is the first part
            String[] parts = userName.split("[@]");
            if (parts.length == 2) {
                user = parts[0];
                domain = parts[1];
            }
        }

        return new org.apache.http.auth.NTCredentials(user, password, workstation, domain);
    }

    /**
     * Returns the NTCredentials or UsernamePasswordCredentials object
     *
     * @param type
     * @param authenticationInfo
     * @return
     */
    public static Credentials getCredentials(final Type type, final AuthenticationInfo authenticationInfo) {
        if (type == Type.TFS) {
            return getNTCredentials(authenticationInfo.getUserName(), authenticationInfo.getPassword());
        } else {
            return new UsernamePasswordCredentials(authenticationInfo.getUserName(), authenticationInfo.getPassword());
        }
    }

    public static ClientConfig getClientConfig(final Type type, final AuthenticationInfo authenticationInfo) {
        final Credentials credentials = getCredentials(type, authenticationInfo);

        return getClientConfig(type, credentials, authenticationInfo.getServerUri());
    }
}

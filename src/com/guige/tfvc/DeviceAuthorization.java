// Copyright (c) guige.com. All rights reserved.
// Licensed under the MIT license. See License.txt in the project root.


package com.guige.tfvc;

import com.guige.tfvc.common.utils.UrlHelper;
import com.microsoft.alm.auth.PromptBehavior;
import com.microsoft.alm.auth.oauth.OAuth2Authenticator;
import com.microsoft.alm.auth.pat.VstsPatAuthenticator;
import com.microsoft.alm.helpers.Action;
import com.microsoft.alm.secret.Token;
import com.microsoft.alm.secret.TokenPair;
import com.microsoft.alm.storage.InsecureInMemoryStore;
import com.microsoft.alm.storage.SecretStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class DeviceAuthorization {
    private final static Logger logger = LoggerFactory.getLogger(DeviceAuthorization.class);
    private static String DEVICE_FLOW_PROPERTY = "userAgentProvider";
    private static String ENABLE_DEVICE_FLOW_VALUE = "none";
    private static final String CLIENT_ID = "97877f11-0fc6-4aee-b1ff-febb0519dd00";
    private static final String REDIRECT_URL = "https://java.visualstudio.com";
    private static final String VSO_AUTH_URL = "https://app.vssps.visualstudio.com";

    private final SecretStore<TokenPair> accessTokenStore;
    private final SecretStore<Token> tokenStore;
    private final DeviceFlowResponsePrompt deviceFlowResponsePrompt;
    private final VstsPatAuthenticator vstsPatAuthenticator;

    public DeviceAuthorization() {
        accessTokenStore = new InsecureInMemoryStore<TokenPair>();
        tokenStore = new InsecureInMemoryStore<Token>();
        deviceFlowResponsePrompt = new DeviceFlowResponseConsolePrompt();
        vstsPatAuthenticator = new VstsPatAuthenticator(CLIENT_ID, REDIRECT_URL, accessTokenStore, tokenStore);
    }

    public void getAuthenticationInfo() {
        // Callback for the Device Flow dialog to cancel the current authenticating process.
        // Normally this is hooked up to the cancel button so if user cancels, we do not wait forever in
        // a polling loop.
        final Action<String> cancellationCallback = reasonForCancel -> {
        };

        //
        vstsPatAuthenticator.signOut(URI.create(VSO_AUTH_URL));
        System.setProperty(DEVICE_FLOW_PROPERTY, ENABLE_DEVICE_FLOW_VALUE);

        // Must share the same accessTokenStore with the member variable vstsPatAuthenticator to avoid prompt the user
        // when we generate PersonalAccessToken
        final OAuth2Authenticator.OAuth2AuthenticatorBuilder oAuth2AuthenticatorBuilder = new OAuth2Authenticator.OAuth2AuthenticatorBuilder()
                .withClientId(CLIENT_ID)
                .redirectTo(REDIRECT_URL)
                .backedBy(accessTokenStore)
                .manage(OAuth2Authenticator.MANAGEMENT_CORE_RESOURCE)
                .withDeviceFlowCallback(deviceFlowResponsePrompt.getCallback(cancellationCallback));

        final OAuth2Authenticator oAuth2Authenticator = oAuth2AuthenticatorBuilder.build();
        final URI encodedServerUri = UrlHelper.createUri(VSO_AUTH_URL);
        final TokenPair tokenPair = oAuth2Authenticator.getOAuth2TokenPair(encodedServerUri, PromptBehavior.AUTO);
    }

}

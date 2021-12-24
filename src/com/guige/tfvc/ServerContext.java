package com.guige.tfvc;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ServerContext {
    private static ServerContext instance = new ServerContext();
    private String collection;

    public static ServerContext getInstance()
    {
        return instance;
    }

    private AuthenticationInfo authenticationInfo;
    private Map<String, String> map = new HashMap<String, String>();

    public String collectionURI() {
        return collection;
    }

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public String getProperty(final String propertyName) {
        return map.get(propertyName);
    }

    public void removeProperty(final String propertyName) {
        map.remove(propertyName);
    }

    public void setProperty(final String propertyName, final String value) {
        if (value == null) {
            removeProperty(propertyName);
        } else {
            map.put(propertyName, value);
        }
    }

    public void authenticationInfo(final AuthenticationInfo info) {
        authenticationInfo = info;
    }

    public void collectionURI(String collection) {
        this.collection = collection;
    }
}

package com.example.uaa.configuration.local;

import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.server.ApacheDSContainer;

public final class LocalLdapServer {

    private LdapContextSource contextSource;

    public LocalLdapServer() {
        String root = "dc=testdat,dc=com";
        int port = 33389;

        try {
            ApacheDSContainer apacheDsContainer = new ApacheDSContainer(root, "classpath:users.ldif");
            apacheDsContainer.setPort(port);
            apacheDsContainer.afterPropertiesSet();

            String url = "ldap://127.0.0.1:" + port + "/" + root;
            contextSource = new DefaultSpringSecurityContextSource(url);
            contextSource.setBase(root);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public LdapContextSource getContextSource() {
        return contextSource;
    }
}

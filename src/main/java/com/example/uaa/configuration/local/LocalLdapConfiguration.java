package com.example.uaa.configuration.local;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@Profile("dev")
public class LocalLdapConfiguration {

    static LocalLdapServer localLdapServer = new LocalLdapServer();

    @Bean
    public LdapContextSource contextSource() {
        return localLdapServer.getContextSource();
    }

}

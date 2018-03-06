package com.example.uaa.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
@Profile("prod")
public class LdapConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "ldap.contextSource")
    public LdapContextSource contextSource() throws Exception {
        LdapContextSource contextSource = new LdapContextSource();
        return contextSource;
    }

}

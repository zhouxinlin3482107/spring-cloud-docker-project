package com.example.uaa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
public class GlobalAuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private LdapContextSource contextSource;

    @Autowired
    private LdapAuthoritiesPopulator ldapAuthoritiesPopulator;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .ldapAuthoritiesPopulator(ldapAuthoritiesPopulator)
                .userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource(contextSource);
    }

}
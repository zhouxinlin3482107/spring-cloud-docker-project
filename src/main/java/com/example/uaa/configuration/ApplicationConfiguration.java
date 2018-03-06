package com.example.uaa.configuration;

import com.example.uaa.authorities.CustomAuthoritiesPopulator;
import com.example.uaa.users.UserAuthoritiesRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }

    @Bean
    public LdapAuthoritiesPopulator ldapAuthoritiesPopulator(ContextSource contextSource, UserAuthoritiesRepository userAuthoritiesRepository) {
        CustomAuthoritiesPopulator authoritiesPopulator = new CustomAuthoritiesPopulator(contextSource, "ou=groups", userAuthoritiesRepository);
        authoritiesPopulator.setGroupRoleAttribute("cn");
        authoritiesPopulator.setGroupSearchFilter("(uniqueMember={0})");
        authoritiesPopulator.setRolePrefix("ROLE_");
        return authoritiesPopulator;
    }
}

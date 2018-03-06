package com.example.uaa.authorities;

import com.example.uaa.users.UserAuthorities;
import com.example.uaa.users.UserAuthoritiesRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;

import java.util.List;
import java.util.Set;

public class CustomAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator {

    private UserAuthoritiesRepository userAuthoritiesRepository;

    public CustomAuthoritiesPopulator(ContextSource contextSource, String groupSearchBase, UserAuthoritiesRepository userAuthoritiesRepository) {
        super(contextSource, groupSearchBase);
        this.userAuthoritiesRepository = userAuthoritiesRepository;
    }

    @Override
    protected Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) {
        List<UserAuthorities> authorities = this.userAuthoritiesRepository.getAuthorities(username);

        List<GrantedAuthority> grantedAuthorities = Lists.transform(authorities, (auth) -> (GrantedAuthority) () -> auth.getDataSetId());

        return Sets.newHashSet(grantedAuthorities);
    }
}

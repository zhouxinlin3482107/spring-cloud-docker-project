package com.example.uaa.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class GroupDetailsRepository {

    private LdapName baseLdapPath = LdapUtils.newLdapName("dc=cecdat,dc=com");
    @Autowired
    private LdapTemplate ldapTemplate;

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public void addMemberToGroup(UserDetails userDetails) {
        Group group = findByName("user");
        group.addMember(buildDn(userDetails));
        ldapTemplate.update(group);
    }

    public void removeMemberFromGroup(UserDetails userDetails) {
        Group group = findByName("user");
        group.removeMember(buildDn(userDetails));
        ldapTemplate.update(group);
    }


    public List<Group> findAll() {
        return ldapTemplate.findAll(Group.class);
    }

    public Group findByName(String groupName) {
        return ldapTemplate.findOne(query().where("cn").is(groupName), Group.class);
    }


    public List<String> getAllGroupNames() {
        LdapQuery query = query().attributes("cn").where("objectclass").is("groupOfUniqueNames");
        return ldapTemplate.search(query, new AttributesMapper<String>() {
            @Override
            public String mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get("cn").get().toString();
            }
        });
    }

    private Name buildDn(UserDetails userDetails) {
        return LdapNameBuilder.newInstance(baseLdapPath)
                .add("ou", userDetails.getCompany())
                .add("uid", userDetails.getUserName())
                .build();
    }


}

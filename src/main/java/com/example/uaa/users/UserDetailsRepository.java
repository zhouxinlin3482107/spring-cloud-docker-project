package com.example.uaa.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class UserDetailsRepository {

    private LdapTemplate ldapTemplate;
    private static final String DefaultGroupId = "100";
    private static final String DefaultLoginShell = "/bin/bash";
    private static final String DefaultHomeDirectory = "/home/";

    public UserDetailsRepository(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    public List<UserDetails> list() {
        return ldapTemplate.search(query()
                        .where("objectclass").is("person"),
                PERSON_CONTEXT_MAPPER);
    }

    public UserDetails getUserByName(String userName) {
        LdapName dn = buildDn("people", userName);
        return ldapTemplate.lookup(dn, PERSON_CONTEXT_MAPPER);
    }

    public UserDetails getUserWithPassWdByName(String userName) {
        LdapName dn = buildDn("people", userName);
        return ldapTemplate.lookup(dn, PERSON_CONTEXT_MAPPER_WITH_PASSWD);
    }

    public void update(UserDetails userDetails) {
        Name dn = buildDn(userDetails);
        DirContextAdapter contextAdapter = (DirContextAdapter) ldapTemplate.lookup(dn);
        contextAdapter.setAttributeValue("cn", userDetails.getFullName());
//        contextAdapter.setAttributeValue("sn", userDetails.getLastName());
        contextAdapter.setAttributeValue("mail", userDetails.getMail());
        contextAdapter.setAttributeValue("telephoneNumber", userDetails.getTelephoneNumber());
        ldapTemplate.modifyAttributes(dn, contextAdapter.getModificationItems());
    }

    public void resetPassword(UserDetails userDetails, String newPassword) {
        Name dn = buildDn(userDetails);
        DirContextAdapter contextAdapter = (DirContextAdapter) ldapTemplate.lookup(dn);
        contextAdapter.setAttributeValue("userPassword", newPassword);
        ldapTemplate.modifyAttributes(dn, contextAdapter.getModificationItems());
    }


    public ResponseEntity<?> verifyAndResetPassword(ResetPasswordDto resetPasswordDto){
        UserDetails userDetails = getUserWithPassWdByName(resetPasswordDto.getUserName());
        if (!(userDetails.getUserPassword().equals(resetPasswordDto.getOldPassword()))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        resetPassword(userDetails, resetPasswordDto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void create(UserDetails userDetails) {
        Name dn = buildDn(userDetails);
        DirContextAdapter contextAdapter = new DirContextAdapter(dn);
        createMapToContext(userDetails, contextAdapter);
        ldapTemplate.bind(dn, contextAdapter, null);
    }


    private LdapName buildDn(UserDetails userDetails) {
        return buildDn("people", userDetails.getUserName());
    }

    private LdapName buildDn(String company, String userName) {
        return LdapNameBuilder.newInstance()
                .add("ou", company)
                .add("uid", userName)
                .build();
    }

    private final static ContextMapper<UserDetails> PERSON_CONTEXT_MAPPER = new AbstractContextMapper<UserDetails>() {
        @Override
        protected UserDetails doMapFromContext(DirContextOperations ctx) {
            UserDetails userDetails = new UserDetails();
            LdapName dn = LdapUtils.newLdapName(ctx.getDn());
            userDetails.setCompany(LdapUtils.getStringValue(dn, 0));
            userDetails.setUserName(LdapUtils.getStringValue(dn, 1));
            userDetails.setFullName(ctx.getStringAttribute("cn"));
            userDetails.setLastName(ctx.getStringAttribute("sn"));
            userDetails.setMail(ctx.getStringAttribute("mail"));
            userDetails.setTelephoneNumber(ctx.getStringAttribute("telephoneNumber"));
            //   String passwd = new String((byte[]) ctx.getObjectAttribute("userPassword"));
            //   userDetails.setUserPassword(passwd);
            userDetails.setHomeDirectory(ctx.getStringAttribute("homeDirectory"));
            userDetails.setLoginShell(ctx.getStringAttribute("loginShell"));
            userDetails.setUidNumber(ctx.getStringAttribute("uidNumber"));
            userDetails.setGidNumber(ctx.getStringAttribute("gidNumber"));
            return userDetails;
        }
    };

    private final static ContextMapper<UserDetails> PERSON_CONTEXT_MAPPER_WITH_PASSWD = new AbstractContextMapper<UserDetails>() {
        @Override
        protected UserDetails doMapFromContext(DirContextOperations ctx) {
            UserDetails userDetails = new UserDetails();
            LdapName dn = LdapUtils.newLdapName(ctx.getDn());
            userDetails.setCompany(LdapUtils.getStringValue(dn, 0));
            userDetails.setUserName(LdapUtils.getStringValue(dn, 1));
            userDetails.setFullName(ctx.getStringAttribute("cn"));
            userDetails.setLastName(ctx.getStringAttribute("sn"));
            userDetails.setMail(ctx.getStringAttribute("mail"));
            userDetails.setTelephoneNumber(ctx.getStringAttribute("telephoneNumber"));
            String passwd = new String((byte[]) ctx.getObjectAttribute("userPassword"));
            userDetails.setUserPassword(passwd);
            userDetails.setHomeDirectory(ctx.getStringAttribute("homeDirectory"));
            userDetails.setLoginShell(ctx.getStringAttribute("loginShell"));
            userDetails.setUidNumber(ctx.getStringAttribute("uidNumber"));
            userDetails.setGidNumber(ctx.getStringAttribute("gidNumber"));
            return userDetails;
        }
    };

    private void createMapToContext(UserDetails userDetails, DirContextAdapter contextAdapter) {
        contextAdapter.setAttributeValues("objectclass", new String[]{"top", "person", "organizationalPerson", "inetOrgPerson", "posixAccount"});
        contextAdapter.setAttributeValue("cn", userDetails.getFullName());
        contextAdapter.setAttributeValue("sn", userDetails.getLastName());
        contextAdapter.setAttributeValue("mail", userDetails.getMail());
        contextAdapter.setAttributeValue("telephoneNumber", userDetails.getTelephoneNumber());
        contextAdapter.setAttributeValue("userPassword", userDetails.getUserPassword());
        contextAdapter.setAttributeValue("homeDirectory", DefaultHomeDirectory + userDetails.getUserName());
        contextAdapter.setAttributeValue("loginShell", DefaultLoginShell);
        contextAdapter.setAttributeValue("uidNumber", userDetails.getUidNumber());
        contextAdapter.setAttributeValue("gidNumber", DefaultGroupId);
    }
}

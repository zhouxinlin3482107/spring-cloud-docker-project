package com.example.uaa.users;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.Set;

@Entry(objectClasses = {"top", "groupOfUniqueNames"}, base = "ou=groups")
public final class Group {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    @DnAttribute(value = "cn", index = 1)
    private String groupName;

    @Attribute(name = "uniqueMember")
    private Set<Name> uniqueMembers;

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<Name> getUniqueMembers() {
        return uniqueMembers;
    }

    public void setUniqueMembers(Set<Name> uniqueMembers) {
        this.uniqueMembers = uniqueMembers;
    }

    public void addMember(Name newUniqueMember){
        uniqueMembers.add(newUniqueMember);
    }

    public void removeMember(Name uniqueMember){
        uniqueMembers.remove(uniqueMember);
    }
}

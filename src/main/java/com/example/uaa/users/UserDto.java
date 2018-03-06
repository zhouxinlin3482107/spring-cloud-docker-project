package com.example.uaa.users;

import com.google.common.collect.Lists;

import java.util.List;

public class UserDto {
    private String userName;
    private String fullName;
    private String lastName;
    private String mail;
    private String telephoneNumber;
    private List<Link> links = Lists.newArrayList();

    public UserDto() {
    }

    public static UserDto create(UserDetails userDetails, boolean isAdmin) {
        UserDto userDto = new UserDto();
        userDto.setFullName(userDetails.getFullName());
        userDto.setLastName(userDetails.getLastName());
        userDto.setUserName(userDetails.getUserName());
        userDto.setMail(userDetails.getMail());
        userDto.setTelephoneNumber(userDetails.getTelephoneNumber());
        if (isAdmin) {
            userDto.getLinks().add(new Link("user-management", "/user-management"));
        }
        return userDto;
    }

    public static UserDto create(UserDetails userDetails) {
        return create(userDetails, false);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}

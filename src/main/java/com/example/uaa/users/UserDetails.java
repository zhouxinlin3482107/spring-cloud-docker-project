package com.example.uaa.users;

public class UserDetails {

    private String userName;

    private String fullName;

    private String lastName;

    private String company;

    private String mail;

    private String telephoneNumber;

    private String userPassword;

    private String loginShell;

    private String uidNumber;

    private String gidNumber;

    private String homeDirectory;

    public UserDetails() {
    }

    public UserDetails(String userName, String fullName, String lastName, String company) {
        this.userName = userName;
        this.fullName = fullName;
        this.lastName = lastName;
        this.company = company;
    }

    public UserDetails(UserDto user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.lastName = user.getLastName();
        this.mail = user.getMail();
        this.telephoneNumber = user.getTelephoneNumber();
        this.userPassword = "password";
        this.company = "people";
        this.gidNumber = "100";
    }

    public String getLoginShell() {
        return loginShell;
    }

    public void setLoginShell(String loginShell) {
        this.loginShell = loginShell;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    public String getGidNumber() {
        return gidNumber;
    }

    public void setGidNumber(String gidNumber) {
        this.gidNumber = gidNumber;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void merge(UserDetailsUpdateDto updateDto) {
        this.userName = updateDto.getUserName();
        this.fullName = updateDto.getFullName();
        this.lastName = updateDto.getLastName();
        this.mail = updateDto.getMail();
        this.telephoneNumber = updateDto.getTelephoneNumber();
    }
}

package com.example.uaa.users;

public class UserDetailsUpdateDto {

    String userName;
    String fullName;
    String lastName;
    String mail;
    String telephoneNumber;

    public UserDetailsUpdateDto() {

    }

    public UserDetailsUpdateDto(String userName, String fullName, String lastName, String mail, String telephoneNumber) {
        this.userName = userName;
        this.fullName = fullName;
        this.lastName = lastName;
        this.mail = mail;
        this.telephoneNumber = telephoneNumber;
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

}

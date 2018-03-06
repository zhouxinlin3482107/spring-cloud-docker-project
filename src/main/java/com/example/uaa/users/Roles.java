package com.example.uaa.users;

public enum Roles {

    USER,
    CLIENT,
    ADMIN;

    String value() {
        return "ROLE_" + name();
    }

    boolean equalsTo(String role) {
        return value().equals(role);
    }
}

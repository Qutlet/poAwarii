package com.maciejBigos.poAwarii.exceptions;

public class UserAlreadyHaveRoleException extends Exception {

    public UserAlreadyHaveRoleException(String role) {
        super("User already have " + role + "role");
    }
}

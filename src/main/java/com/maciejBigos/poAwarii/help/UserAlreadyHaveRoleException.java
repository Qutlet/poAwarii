package com.maciejBigos.poAwarii.help;

public class UserAlreadyHaveRoleException extends Exception {

    public UserAlreadyHaveRoleException(String role) {
        super("User already have " + role + "role");
    }
}

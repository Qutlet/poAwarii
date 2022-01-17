package com.maciejBigos.poAwarii.exceptions;

public class UserIsNotSpecialistException extends Exception{

    public UserIsNotSpecialistException(String userID) {
        super("User: " + userID + " is not specialist");
    }

}

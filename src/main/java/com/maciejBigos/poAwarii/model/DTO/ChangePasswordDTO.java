package com.maciejBigos.poAwarii.model.DTO;

import com.maciejBigos.poAwarii.validators.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ChangePasswordDTO {

    private String oldPassword;
    private String password;
    private String matchingPassword;

    public ChangePasswordDTO(String oldPassword, String password, String matchingPassword) {
        this.oldPassword = oldPassword;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}

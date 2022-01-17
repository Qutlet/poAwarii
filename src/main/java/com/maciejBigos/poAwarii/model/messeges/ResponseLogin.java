package com.maciejBigos.poAwarii.model.messeges;

import com.maciejBigos.poAwarii.model.Role;

import java.util.List;

public class ResponseLogin {

    private String token;
    private int roleLevel;
    private String userId;
    private Long specId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public ResponseLogin(String token, int roleLevel, String userId, Long specId) {
        this.token = token;
        this.roleLevel = roleLevel;
        this.userId = userId;
        this.specId = specId;
    }

    public ResponseLogin(String token, int roleLevel, String userId) {
        this.token = token;
        this.roleLevel = roleLevel;
        this.userId = userId;
    }
}

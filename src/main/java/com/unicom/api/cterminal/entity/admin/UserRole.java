package com.unicom.api.cterminal.entity.admin;

import java.io.Serializable;

public class UserRole implements Serializable {

    private Integer user_id;
    private Integer role_id;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public UserRole(Integer user_id, Integer role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public UserRole() {
    }
}

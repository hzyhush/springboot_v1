package com.unicom.api.cterminal.entity.admin;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

    private Integer role_id;
    private String role_name;
    private String role_description;
    private boolean ischeck;

    public String getRole_description() {
        return role_description;
    }

    public void setRole_description(String role_description) {
        this.role_description = role_description;
    }

    public boolean isIscheck() {
        return ischeck;
    }
    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

}

package com.unicom.api.cterminal.entity.admin;

import java.io.Serializable;

public class RoleMenu implements Serializable {

    private Integer role_id;
    private String menu_id;

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public RoleMenu(Integer role_id, String menu_id) {
        this.role_id = role_id;
        this.menu_id = menu_id;
    }

    public RoleMenu() {
    }
}

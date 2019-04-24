package com.unicom.api.cterminal.entity;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

    private Integer role_id;
    private String role_name;
    private String role_description;
    private Integer role_available;
    private List<User> users;
    private List<Menu> menus;

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

    public String getRole_description() {
        return role_description;
    }

    public void setRole_description(String role_description) {
        this.role_description = role_description;
    }

    public Integer getRole_available() {
        return role_available;
    }

    public void setRole_available(Integer role_available) {
        this.role_available = role_available;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}

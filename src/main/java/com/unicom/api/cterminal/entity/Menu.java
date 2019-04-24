package com.unicom.api.cterminal.entity;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

    private Integer menu_id;
    private String menu_name;
    private String menu_type;
    private String menu_url;
    private String menu_permission;
    private Integer parentId;
    private String parentIds;
    private Integer menu_available;
    private List<Role> roles;

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public String getMenu_permission() {
        return menu_permission;
    }

    public void setMenu_permission(String menu_permission) {
        this.menu_permission = menu_permission;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getMenu_available() {
        return menu_available;
    }

    public void setMenu_available(Integer menu_available) {
        this.menu_available = menu_available;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}

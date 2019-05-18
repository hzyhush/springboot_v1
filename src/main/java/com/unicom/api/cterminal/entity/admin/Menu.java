package com.unicom.api.cterminal.entity.admin;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Menu implements Serializable {

    private String text;
    private Integer id;
    private String icon;
    private Integer menu_id;
    private String menu_name;
    private String menu_icon;
    private String menu_url;
    private Integer parent_id;
    private String permission;
    private Integer order_number;
    private Integer menu_type;
    private String create_time;
    private String menu_descripion;
    private List<Menu> nodes;
    private String data;
    private Map<String,Object> state;//选中参数
    private boolean isChilren;

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isChilren() {
        return isChilren;
    }

    public void setChilren(boolean chilren) {
        isChilren = chilren;
    }

    public List<Menu> getNodes() {
        return nodes;
    }

    public void setNodes(List<Menu> nodes) {
        this.nodes = nodes;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.id = menu_id;
        this.menu_id = menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.text = menu_name;
        this.menu_name = menu_name;
    }

    public String getMenu_descripion() {
        return menu_descripion;
    }

    public void setMenu_descripion(String menu_descripion) {
        this.menu_descripion = menu_descripion;
    }

    public String getMenu_icon() {
        return menu_icon;
    }

    public void setMenu_icon(String menu_icon) {
        this.icon = menu_icon;
        this.menu_icon = menu_icon;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }


    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public Integer getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(Integer menu_type) {
        this.menu_type = menu_type;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}

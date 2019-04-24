package com.unicom.api.cterminal.entity;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class User implements Serializable {

    private Integer user_id;
    private String user_name;
    private String user_pwd;
    private String user_salt;
    private Integer user_status;
    private List<Role> roles;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_salt() {
        return user_salt;
    }

    public void setUser_salt(String user_salt) {
        this.user_salt = user_salt;
    }

    public Integer getUser_status() {
        return user_status;
    }

    public void setUser_status(Integer user_status) {
        this.user_status = user_status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User() {
    }

    public User(Integer user_id, String user_name) {
        this.user_id = user_id;
        this.user_name = user_name;
    }
}

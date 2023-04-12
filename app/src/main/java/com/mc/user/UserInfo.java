package com.mc.user;

import java.io.Serializable;

/**
 * 用户信息实体类
 */

public class UserInfo implements Serializable {
    private String name;
    private String password;

    public UserInfo() {
    }

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

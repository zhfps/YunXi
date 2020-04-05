package com.yunxi.zhang.admin.project.features.entity;

import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2020-03-21 15:05:25
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 729515054681801442L;
    
    private Long id;
    
    private String name;
    
    private String nickname;
    
    private String password;


    public Object getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
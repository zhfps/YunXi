package com.yunxi.zhang.admin.project.features.entity;

import lombok.Builder;

import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author makejava
 * @since 2020-03-21 15:06:12
 */
@Builder
public class SysRole implements Serializable {
    private static final long serialVersionUID = 898779609353806637L;
    
    private Long id;
    /**
    * 角色名称：管理员；用户
    */
    private String namezh;
    /**
    * 角色：ROLE_ADMIN;ROLE_USER
    */
    private String name;


    public Object getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamezh() {
        return namezh;
    }

    public void setNamezh(String namezh) {
        this.namezh = namezh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
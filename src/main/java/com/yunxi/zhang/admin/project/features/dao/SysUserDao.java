package com.yunxi.zhang.admin.project.features.dao;

import com.yunxi.zhang.admin.project.features.entity.SysRole;
import com.yunxi.zhang.admin.project.features.entity.SysUser;
import com.yunxi.zhang.admin.project.features.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-21 15:05:25
 */
@Mapper
public interface SysUserDao {

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    UserDetail findByUsername(@Param("name") String name);

    /**
     * 创建新用户
     * @param userDetail
     */
    void insert(UserDetail userDetail);

    /**
     * 创建用户角色
     * @param userId
     * @param roleId
     * @return
     */
    int insertRole(@Param("userId") long userId, @Param("roleId") long roleId);

    /**
     * 根据角色id查找角色
     * @param roleId
     * @return
     */
    SysRole findRoleById(@Param("roleId") long roleId);

    /**
     * 根据用户id查找该用户角色
     * @param userId
     * @return
     */
    SysRole findRoleByUserId(@Param("userId") long userId);

}
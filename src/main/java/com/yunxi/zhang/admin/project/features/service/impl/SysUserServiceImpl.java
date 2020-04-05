package com.yunxi.zhang.admin.project.features.service.impl;

import com.yunxi.zhang.admin.project.features.dao.SysRoleDao;
import com.yunxi.zhang.admin.project.features.entity.SysRole;
import com.yunxi.zhang.admin.project.features.entity.SysUser;
import com.yunxi.zhang.admin.project.features.dao.SysUserDao;
import com.yunxi.zhang.admin.project.features.entity.UserDetail;
import com.yunxi.zhang.admin.project.features.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-03-21 15:05:25
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserDetail userDetail = sysUserDao.findByUsername(name);
        if (userDetail == null) {
            throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", name));
        }
        SysRole role = sysUserDao.findRoleByUserId(userDetail.getId());
        userDetail.setRole(role);
        return userDetail;
    }

    @Override
    public SysUser queryById(Object id) {
        return null;
    }

    @Override
    public SysUser queryName(String name) {
        return null;
    }

    @Override
    public List<SysUser> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public List<SysUser> queryAll(SysUser sysUser) {
        return null;
    }

    @Override
    public int insert(SysUser sysUser) {
        return 0;
    }

    @Override
    public int update(SysUser sysUser) {
        return 0;
    }

    @Override
    public int deleteById(Object id) {
        return 0;
    }
}
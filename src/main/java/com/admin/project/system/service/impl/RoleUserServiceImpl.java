package com.admin.project.system.service.impl;
import com.admin.project.system.base.result.Results;
import com.admin.project.system.dao.RoleUserDao;
import com.admin.project.system.entity.SysRoleUser;
import com.admin.project.system.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Resource
    private RoleUserDao roleUserDao;

    @Override
    public Results getSysRoleUserByUserId(Integer userId) {
        SysRoleUser sysRoleUser = roleUserDao.getSysRoleUserByUserId(userId);
        if(sysRoleUser != null){
            return Results.success(sysRoleUser);
        }else{
            return Results.success();
        }
    }
}

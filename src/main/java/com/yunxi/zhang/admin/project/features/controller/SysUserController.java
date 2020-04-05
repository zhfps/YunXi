package com.yunxi.zhang.admin.project.features.controller;

import com.yunxi.zhang.admin.project.features.entity.SysUser;
import com.yunxi.zhang.admin.project.features.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-03-21 15:05:26
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUser selectOne(Object id) {
        return this.sysUserService.queryById(id);
    }

}
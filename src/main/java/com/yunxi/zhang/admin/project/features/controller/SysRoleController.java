package com.yunxi.zhang.admin.project.features.controller;

import com.yunxi.zhang.admin.project.features.entity.SysRole;
import com.yunxi.zhang.admin.project.features.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysRole)表控制层
 *
 * @author makejava
 * @since 2020-03-21 15:06:12
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysRole selectOne(Object id) {
        return this.sysRoleService.queryById(id);
    }

}
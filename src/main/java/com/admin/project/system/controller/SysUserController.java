package com.admin.project.system.controller;

import com.admin.project.system.entity.SysUser;
import com.admin.project.system.service.UserService;
import com.admin.project.system.untils.MyPage;
import com.admin.project.system.untils.Result;
import com.admin.project.system.untils.ResultBuilder;
import com.admin.project.system.untils.ResultCode;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "api")
public class SysUserController {
    @Resource
    private UserService userService;

    @GetMapping(value = "/sysUser/All")
    @ResponseBody
    public Result<MyPage<SysUser>> getAllPage(int page, int pageSize) {
        Result<MyPage<SysUser>> result;
        MyPage<SysUser> data = userService.getUserByPage(page, pageSize);
        result = ResultBuilder.success(data, ResultCode.SUCCESS);
        return result;
    }
}

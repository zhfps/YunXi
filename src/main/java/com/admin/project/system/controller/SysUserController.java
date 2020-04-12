package com.admin.project.system.controller;

import com.admin.project.system.entity.SysUser;
import com.admin.project.system.service.UserService;
import com.admin.project.system.untils.*;
import com.github.pagehelper.PageInfo;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping(value = "api")
public class SysUserController {
    @Resource
    private UserService userService;

    @Resource
    private Producer captchaProducer;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping(value = "/sysUser/All")
    @ResponseBody
    public Result<MyPage<SysUser>> getAllPage(int page, int pageSize) {
        Result<MyPage<SysUser>> result;
        MyPage<SysUser> data = userService.getUserByPage(page, pageSize);
        result = ResultBuilder.success(data, ResultCode.SUCCESS);
        return result;
    }
    @GetMapping(value = "/code")
    public void getCode (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String capStr;
            BufferedImage bufferedImage = null;
            String capText = captchaProducer.createText();
            redisTemplate.opsForValue().set("Code",capText);
            bufferedImage = captchaProducer.createImage(capText);
            // ResponseUtils工具类文尾有附
            ResponseUtil.responseBufferedImage(req, resp, bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.yunxi.zhang.admin.project.features.controller;
import com.yunxi.zhang.admin.project.features.entity.ResponseUserToken;
import com.yunxi.zhang.admin.project.features.entity.SysRole;
import com.yunxi.zhang.admin.project.features.entity.SysUser;
import com.yunxi.zhang.admin.project.features.entity.UserDetail;
import com.yunxi.zhang.admin.project.features.service.AuthService;
import com.yunxi.zhang.admin.project.system.untils.Result;
import com.yunxi.zhang.admin.project.system.untils.ResultBuilder;
import com.yunxi.zhang.admin.project.system.untils.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author JoeTao
 * createAt: 2018/9/17
 */

@RestController
@Api(description = "登陆注册及刷新token")
@RequestMapping
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;
    @Resource
    private AuthService authService;
    @PostMapping(value = "/login")
    @ApiOperation(value = "登陆", notes = "登陆成功返回token,登陆之前请先注册账号")
    public Result<ResponseUserToken> login(
            @Valid @RequestBody SysUser user){
        Result<ResponseUserToken> result=null;
        ResponseUserToken response = authService.login(user.getName(), user.getPassword());
        result = ResultBuilder.success(response, ResultCode.SUCCESS);
        return result;
    }

    @GetMapping(value = "/logout")
    @ApiOperation(value = "登出", notes = "退出登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public Result<String> logout(HttpServletRequest request) throws Exception {
        Result<String> result=null;
        String token = request.getHeader(tokenHeader);
        if (token == null) {
            result = ResultBuilder.faile(ResultCode.PERMISSION_NO_ACCESS);
            return result;
        }
        authService.logout(token);
        return result;
    }

    @GetMapping(value = "/user")
    @ApiOperation(value = "根据token获取用户信息", notes = "根据token获取用户信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header")})
    public Result<UserDetail> getUser(HttpServletRequest request) throws Exception {
        Result<UserDetail> result=null;
        String token = request.getHeader(tokenHeader);
        if (token == null) {
             result = ResultBuilder.faile(ResultCode.PERMISSION_NO_ACCESS);
            return result;
        }
        UserDetail userDetail = authService.getUserByToken(token);
        if (userDetail == null) {
            result = ResultBuilder.faile(ResultCode.FAILE);
            return result;
        }
        result =  result = ResultBuilder.success(userDetail, ResultCode.SUCCESS);
        return result;
    }

    @PostMapping(value = "/sign")
    @ApiOperation(value = "用户注册")
    public Result<UserDetail> sign(@RequestBody SysUser user) {
        Result<UserDetail> result=null;
        if (StringUtils.isAnyBlank(user.getName(), user.getPassword())) {
            result = ResultBuilder.faile(ResultCode.PERMISSION_NO_ACCESS);
            return result;
        }
        UserDetail userDetail = new UserDetail(user.getName(), user.getPassword(), SysRole.builder().id(1l).build());
        result = ResultBuilder.success(authService.register(userDetail),ResultCode.SUCCESS);
        return result;
    }
}

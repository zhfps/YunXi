package com.yunxi.zhang.admin.project.features.service;

import com.yunxi.zhang.admin.project.features.entity.ResponseUserToken;
import com.yunxi.zhang.admin.project.features.entity.UserDetail;

public interface AuthService {
    /**
     * 注册用户
     * @param userDetail
     * @return
     */
    UserDetail register(UserDetail userDetail);

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    ResponseUserToken login(String username, String password);

    /**
     * 登出
     * @param token
     */
    void logout(String token) throws Exception;

    /**
     * 刷新Token
     * @param oldToken
     * @return
     */
    ResponseUserToken refresh(String oldToken) throws Exception;

    /**
     * 根据Token获取用户信息
     * @param token
     * @return
     */
    UserDetail getUserByToken(String token) throws Exception;
}

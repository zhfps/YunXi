package com.yunxi.zhang.admin.project.features.service.impl;
import com.yunxi.zhang.admin.project.features.dao.SysUserDao;
import com.yunxi.zhang.admin.project.features.entity.ResponseUserToken;
import com.yunxi.zhang.admin.project.features.entity.SysRole;
import com.yunxi.zhang.admin.project.features.entity.UserDetail;
import com.yunxi.zhang.admin.project.features.service.AuthService;
import com.yunxi.zhang.admin.project.system.untils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private  AuthenticationManager authenticationManager;
    @Resource
    private  UserDetailsService userDetailsService;
    @Resource
    private  JwtUtils jwtTokenUtil;
    @Resource
    private  SysUserDao userDao;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    public UserDetail register(UserDetail userDetail) {
         String username = userDetail.getUsername();
        if(userDao.findByUsername(username)!=null) {
            throw new RuntimeException("用户已存在");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userDetail.getPassword();
        userDetail.setPassword(encoder.encode(rawPassword));
        userDetail.setLastPasswordResetDate(new Date());
        userDao.insert(userDetail);
        long roleId = (long) userDetail.getRole().getId();
        SysRole role = userDao.findRoleById(roleId);
        userDetail.setRole(role);
        userDao.insertRole(userDetail.getId(), roleId);
        return userDetail;
    }

    @Override
    public ResponseUserToken login(String username, String password) {
        //用户验证
         Authentication authentication = authenticate(username, password);
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token
         UserDetail userDetail = (UserDetail) authentication.getPrincipal();
         String token = jwtTokenUtil.generateAccessToken(userDetail);
        //存储token
        jwtTokenUtil.putToken(username, token);
        return new ResponseUserToken(token, userDetail);

    }

    @Override
    public void logout(String token) throws Exception {
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        jwtTokenUtil.deleteToken(userName);
    }

    @Override
    public ResponseUserToken refresh(String oldToken) throws Exception {
        String token = oldToken;
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserDetail userDetail = (UserDetail) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, userDetail.getLastPasswordResetDate())){
            token =  jwtTokenUtil.refreshToken(token);
            return new ResponseUserToken(token, userDetail);
        }
        return null;
    }

    @Override
    public UserDetail getUserByToken(String token) throws Exception {
        return jwtTokenUtil.getUserFromToken(token);
    }

    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，如果正确，则存储该用户名密码到“security 的 context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
           throw new RuntimeException(e);
        }
    }
}

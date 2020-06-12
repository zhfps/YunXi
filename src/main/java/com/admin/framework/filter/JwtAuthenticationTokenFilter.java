package com.admin.framework.filter;

import com.admin.framework.exception.NotFoundUserException;
import com.admin.framework.security.authentication.MyAuthenctiationFailureHandler;
import com.admin.project.system.untils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component("jwtAuthenticationTokenFilter")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter implements Filter {
    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;
    @Resource
    private UserDetailsService userDetailsService;
    private String[] whitelist = {
            "/",
            "/login",
            "/login.html",
            "/api/code"
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        boolean cheack =useList(whitelist,url);
        try{
            if(!cheack){
                parsingToken(new ServletWebRequest(request));
            }
        }catch (NotFoundUserException e) {
            // 2. 捕获步骤1中校验出现异常，交给失败处理类进行进行处理
            myAuthenctiationFailureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        // 3. 校验通过，就放行
        filterChain.doFilter(request, response);
    }
    private void parsingToken(ServletWebRequest request) throws ServletRequestBindingException {
        // 1. 获取请求中的验证码
        String token = request.getHeader("Token");
        // 2. 校验空值情况
        if(StringUtils.isEmpty(token)) {
            throw new NotFoundUserException("没有token");
        }
        // 5. token 解析
        try {
            String username = JwtTokenUtil.getProperties(token);
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            throw new NotFoundUserException(e.getMessage());
        }catch (Exception e){
            throw new NotFoundUserException(e.getMessage());
        }



    }
    public static boolean useList(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }
}

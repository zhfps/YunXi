package com.yunxi.zhang.admin.framework.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunxi.zhang.admin.framework.exception.YJwtException;
import com.yunxi.zhang.admin.project.features.entity.UserDetail;
import com.yunxi.zhang.admin.project.system.untils.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.header}")
    private String token_header;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException,YJwtException {
        String auth_token = request.getHeader(this.token_header);
        String username = null;
        try {
            username = jwtUtils.getUsernameFromToken(auth_token);
        } catch (ExpiredJwtException e){
            FilterUntils.timeOut(response,e.getMessage());
            return;
        } catch (Exception e) {
//            e.printStackTrace();
        }

        logger.info(String.format("Checking authentication for userDetail %s.", username));
//        jwtUtils.containToken(username, auth_token) &&
        if ( username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetail userDetail = null;
            try {
                userDetail = jwtUtils.getUserFromToken(auth_token);
            }  catch (ExpiredJwtException e){
                throw new ExpiredJwtException(e.getHeader(),e.getClaims(),e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean valid = false;
            try {
                valid = jwtUtils.validateToken(auth_token, userDetail);
            } catch (ExpiredJwtException e){
                throw new ExpiredJwtException(e.getHeader(),e.getClaims(),e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (valid) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Authenticated userDetail %s, setting security context", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}

package com.yunxi.zhang.admin.framework.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunxi.zhang.admin.project.system.untils.Result;
import com.yunxi.zhang.admin.project.system.untils.ResultBuilder;
import com.yunxi.zhang.admin.project.system.untils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component("RestAuthenticationAccessDeniedHandler")
@Slf4j
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        log.info("权限不足：" + e.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        Result<String> result = ResultBuilder.faileData(e.getMessage(),ResultCode.PERMISSION_NO_ACCESS);
        String body =mapper.writeValueAsString(result);
        printWriter.write(body);
        printWriter.flush();
    }
}

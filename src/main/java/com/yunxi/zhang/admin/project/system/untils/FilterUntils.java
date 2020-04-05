package com.yunxi.zhang.admin.project.system.untils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FilterUntils {
    static ObjectMapper mapper = new ObjectMapper();
    public static void timeOut(HttpServletResponse response,String msg) throws IOException {
        Result<String> result= ResultBuilder.faileData(msg, ResultCode.TIME_OUT);
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body =mapper.writeValueAsString(result);
        printWriter.write(body);
        printWriter.flush();
    }
}

package com.yunxi.zhang.admin.framework.exception;
import com.yunxi.zhang.admin.project.system.untils.Result;
import com.yunxi.zhang.admin.project.system.untils.ResultBuilder;
import com.yunxi.zhang.admin.project.system.untils.ResultCode;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@ControllerAdvice
@ResponseBody
@Slf4j
public class YunExceptionHandler {
    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        Result<String> result= ResultBuilder.faileData(e.getBindingResult().getFieldError().getDefaultMessage(), ResultCode.DATA_IS_WRONG);
        return result;
    }
    /**
     * 处理授权失败异常
     * @param e
     * @return
     */
    @ExceptionHandler(JwtAuthFaileException.class)
    public Result<String> handleMethodAuthFaile(JwtAuthFaileException e){
        Result<String> result= ResultBuilder.faileData(e.getMessage(), ResultCode.LOGIN_ERROR);
        return result;
    }
    /**
     * jwt验证
     * @param e
     * @return
     */
    @ExceptionHandler(YJwtException.class)
    public Result<String> handleExpiredJwtException(YJwtException e){
        Result<String> result= ResultBuilder.faileData(e.getMessage(), ResultCode.LOGIN_ERROR);
        return result;
    }
    /**
     * jwt验证
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e){
        Result<String> result= ResultBuilder.faileData(e.getMessage(), ResultCode.LOGIN_ERROR);
        return result;
    }
}

package com.admin.framework.exception;
import com.admin.project.system.untils.Result;
import com.admin.project.system.untils.ResultBuilder;
import com.admin.project.system.untils.ResultCode;
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
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundUserException.class)
    public Result<String> handleNotFoundUserException(NotFoundUserException e){
        log.error(e.getMessage());
        Result<String> result= ResultBuilder.faileData(e.getMessage(), ResultCode.DATA_IS_WRONG);
        return result;
    }
}

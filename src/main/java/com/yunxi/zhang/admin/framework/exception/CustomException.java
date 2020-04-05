package com.yunxi.zhang.admin.framework.exception;
import lombok.Getter;

/**
 * @author Joetao
 * Created at 2018/8/24.
 */
@Getter
public class CustomException extends RuntimeException{
    public CustomException() {
    }

    public CustomException(String s) {
        super(s);
    }
}

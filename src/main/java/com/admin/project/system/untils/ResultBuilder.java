package com.admin.project.system.untils;

/**
 * @program: YunXi
 * @author: it_dog
 * @create: 2019-11-10
 **/
public class ResultBuilder {
    //成功，不返回具体数据
    public static <T> Result<T> successNoData(ResultCode code){
        Result<T> result = new Result<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }
    //成功，返回数据
    public static <T> Result<T> success(T t,ResultCode code){
        Result<T> result = new Result<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        result.setData(t);
        return result;
    }

    //失败，返回失败信息
    public static <T> Result<T> faile(ResultCode code){
        Result<T> result = new Result<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        return result;
    }
    //失败，返回数据
    public static <T> Result<T> faileData(T t,ResultCode code){
        Result<T> result = new Result<T>();
        result.setCode(code.getCode());
        result.setMsg(code.getMsg());
        result.setData(t);
        return result;
    }
}

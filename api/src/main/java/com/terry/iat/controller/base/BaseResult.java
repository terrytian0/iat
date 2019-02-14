package com.terry.iat.controller.base;

import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.common.bean.ResultCode;

/**
 * @Description 接口返回基类
 * @author terry
 * @Date 2019/1/22 11:33
 * @Version 1.0
 **/
public abstract class BaseResult<T> {
    /**
     * @Description 不带消息的成功返回
     * @author terry
     * @Date 2019/2/13 14:49
     * @Param []
     * @return com.terry.iat.service.common.bean.Result
     **/
    public Result success(){
        return success(null);
    }
    /**
     * @Description 自定义对象成功返回
     * @author terry
     * @Date 2019/2/13 14:50
     * @Param [t]
     * @return com.terry.iat.service.common.bean.Result
     **/
    public  Result success(T t){
        return success(ResultCode.SUCCESS,t);
    }
    /**
     * @Description 自定义返回码和对象的成功返回
     * @author terry
     * @Date 2019/2/13 14:51
     * @Param [code, t]
     * @return com.terry.iat.service.common.bean.Result
     **/
    public  Result success(ResultCode code ,T t){
        return Result.builder().status(true).code(code.getCode()).message(code.getMessage()).content(t).build();
    }

    /**
     * @Description 不带消息的错误返回
     * @author terry
     * @Date 2019/2/13 14:51
     * @Param [code]
     * @return com.terry.iat.service.common.bean.Result
     **/
    public  Result failed(ResultCode code){
        return failed(code,null);
    }
    /**
     * @Description 自定义返回码和对象的失败返回
     * @author terry
     * @Date 2019/2/13 14:52
     * @Param [code, t]
     * @return com.terry.iat.service.common.bean.Result
     **/
    public Result failed(ResultCode code,T t){
        return Result.builder().status(false).code(code.getCode()).message(code.getMessage()).content(t).build();
    }
}

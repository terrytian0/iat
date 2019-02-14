/**
 * 
 */
package com.terry.iat.service.common.exception;


import com.terry.iat.service.common.bean.ResultCode;

/**
 * @author terry
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -7932831191582273531L;

    private ResultCode resultCode;

    public BusinessException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ResultCode code() {
        return this.resultCode;
    }

}

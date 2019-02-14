package com.terry.iat.service.common.bean;

import lombok.Builder;
import lombok.Data;

/**
 * @author terry
 * @Date: 2018/10/20 14:38
 * @Description:
 */
@Builder
@Data
public class Result<T> {
    boolean status;
    String code;
    String message;
    T content;
}

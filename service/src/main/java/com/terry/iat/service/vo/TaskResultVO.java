package com.terry.iat.service.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

/**
 * @author terry
 * @version 1.0
 * @class name TaskResultVO
 * @description TODO
 * @date 2019/2/22 12:37
 **/
@Data
public class TaskResultVO {
    @NotBlank
    private Long id;
    @NotBlank
    private String status;
    private String message;
    @NotBlank
    private Timestamp startTime;
    @NotBlank
    private Timestamp endTime;
    @NotBlank
    private String client;
    @NotBlank
    private String key;
}

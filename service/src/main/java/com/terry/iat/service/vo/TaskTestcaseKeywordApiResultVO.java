package com.terry.iat.service.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

/**
 * @author terry
 * @version 1.0
 * @class name TaskResultVO
 * @description TODO
 * @date 2019/2/22 12:37
 **/
@Data
public class TaskTestcaseKeywordApiResultVO {
    @NotEmpty
    private Long taskId;
    private Long testplanId;
    @NotEmpty
    private Long testcaseId;
    @NotEmpty
    private Long parameterId;
    @NotEmpty
    private Long testcaseKeywordId;
    @NotEmpty
    private Long keywordId;
    @NotEmpty
    private Long keywordApiId;
    @NotEmpty
    private Long apiId;
    private String requestHeaders;
    private String requestFormdatas;
    private String requestBody;
    @NotBlank
    private String responseHeaders;
    @NotBlank
    private String responseBody;
    private String extractors;
    private String asserts;
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

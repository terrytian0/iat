package com.terry.iat.service.core;

import com.terry.iat.dao.entity.AssertEntity;
import com.terry.iat.dao.entity.ExtractorEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpResult {
    private Long apiId;
    private Long keywordApiId;
    private Long testcaseKeywordId;
    private String error;
    private boolean successful = true;
    /**
     * 请求参数
     */
    private Map<String, String> parameters;
    /**
     * 提取器
     */
    private List<ExtractorEntity> extractors;
    /**
     * 断言
     */
    private List<AssertEntity> asserts;
    /**
     * 基础信息
     */
    private Map<String, String> general;
    /**
     * 请求header
     */
    private Map<String, String> requestHeader;
    /**
     * 请求formdata
     */
    private Map<String, String> requestFormData;
    /**
     * 请求body
     */
    private String requestBody;
    /**
     * 返回header
     */
    private Map<String, String> responseHeader;
    /**
     * 返回body
     */
    private String responseBody;
    /**
     * 返回status
     */
    private Integer statusCode;
}

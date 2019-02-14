package com.terry.iat.service.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class TestcaseAddParameterVO {
    private Long testcaseId;
    private Integer rowNum;
    private Map<String,String> parameters;
}

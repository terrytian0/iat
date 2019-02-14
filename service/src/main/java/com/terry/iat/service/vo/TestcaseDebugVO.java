package com.terry.iat.service.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TestcaseDebugVO {
    private Long testcaseId;
    private Map<String,String> parameters;
    private Long envId;
}

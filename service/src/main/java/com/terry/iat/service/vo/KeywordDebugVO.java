package com.terry.iat.service.vo;

import lombok.Data;

import java.util.List;

@Data
public class KeywordDebugVO {
    private Long keywordId;
    private String host;
    private List<ParameterVO> parameters;
    private Long envId;
}

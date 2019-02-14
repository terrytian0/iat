package com.terry.iat.service.vo;

import com.terry.iat.service.common.enums.ExtractorType;
import lombok.Data;

@Data
public class ExtractorVO {
    private Long id;
    private Long keywordApiId;
    private ExtractorType type;
    private String rule;
    private String name;
    private String description;
}

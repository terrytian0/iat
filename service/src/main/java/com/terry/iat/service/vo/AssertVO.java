package com.terry.iat.service.vo;

import com.terry.iat.service.common.enums.AssertLocale;
import com.terry.iat.service.common.enums.AssertMethod;
import com.terry.iat.service.common.enums.AssertType;
import com.terry.iat.service.common.enums.ExtractorType;
import lombok.Data;

@Data
public class AssertVO {
    private Long id;
    private Long keywordApiId;
    private AssertType type;
    private AssertLocale locale;
    private AssertMethod method;
    private String rule;
    private String value;
    private String description;
}

package com.terry.iat.service.vo;

import com.terry.iat.service.common.enums.Index;
import lombok.Data;

@Data
public class KeywordIndexVO {
    private Long keywordId;
    private Long keywordApiId;
    /**
     * up;down,first;last
     */
    private Index index;
}

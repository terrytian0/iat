package com.terry.iat.service.vo;

import com.terry.iat.service.common.enums.Index;
import lombok.Data;

@Data
public class TestcaseIndexVO {
    private Long testcaseId;
    private Long testcaseKeywordId;
    /**
     * up;down,first;last
     */
    private Index index;
}

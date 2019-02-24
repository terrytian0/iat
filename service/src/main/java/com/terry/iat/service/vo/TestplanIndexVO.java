package com.terry.iat.service.vo;

import com.terry.iat.service.common.enums.Index;
import lombok.Data;

@Data
public class TestplanIndexVO {
    private Long testplanId;
    private Long testplanTestcaseId;
    /**
     * up;down,first;last
     */
    private Index index;
}

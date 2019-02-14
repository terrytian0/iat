package com.terry.iat.service.core;

import com.terry.iat.service.core.KeywordResult;
import lombok.Data;

import java.util.List;

@Data
public class TestcaseResult {
    private Long testcaseId;
    private List<KeywordResult> keywordResults;
    private boolean status = true;
}

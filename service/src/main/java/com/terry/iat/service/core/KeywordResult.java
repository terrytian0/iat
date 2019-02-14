package com.terry.iat.service.core;

import com.terry.iat.service.core.HttpResult;
import lombok.Data;

import java.util.List;

@Data
public class KeywordResult {
    private Long keywordId;
    private Long testcaseKeywordId;
    private List<HttpResult> httpResults;
    private boolean status = true;
}

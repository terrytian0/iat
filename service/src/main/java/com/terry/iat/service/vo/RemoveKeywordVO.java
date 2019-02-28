package com.terry.iat.service.vo;

import lombok.Data;

import java.util.List;

@Data
public class RemoveKeywordVO {
    private List<Long> ids;
    private Long testcaseId;
}

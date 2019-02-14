package com.terry.iat.service;

import com.terry.iat.dao.entity.ParameterKeyEntity;

import java.util.List;
import java.util.Map;

public interface ParameterKeyService {
    /**
     * 获取testcase下的所有参数key
     * @param testcaseId
     * @return
     */
    Map<Long,String> getByTestcaseId(Long testcaseId);

    int create(Long testcaseId,List<String> keys);

    int delete(List<Long> ids);
}

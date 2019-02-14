package com.terry.iat.service;


import com.terry.iat.dao.entity.TestcaseKeywordApiEntity;
import com.terry.iat.service.vo.AddKeywordVO;
import com.terry.iat.service.vo.TestcaseIndexVO;

import java.util.List;

/**
 * @author terry
 */
public interface TestcaseKeywordApiService {
    /**
     * 创建testcaseKeywordsApi
     *
     * @param addKeywordVO
     * @return
     */
    List<TestcaseKeywordApiEntity> create(AddKeywordVO addKeywordVO);

    /**
     * 从testcase中移除keywords
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);

    /**
     * 通过testcaseId获取keywords列表
     *
     * @param testcaseId
     * @return
     */
    List<TestcaseKeywordApiEntity> getByTestcaseId(Long testcaseId);

    void updateIdx(TestcaseIndexVO testcaseIndexVO);

}

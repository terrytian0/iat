package com.terry.iat.service;


import com.terry.iat.dao.entity.TestcaseKeywordEntity;
import com.terry.iat.service.vo.AddKeywordVO;
import com.terry.iat.service.vo.TestcaseIndexVO;

import java.util.List;

/**
 * @author terry
 */
public interface TestcaseKeywordService {
    /**
     * 创建testcaseKeywordsApi
     *
     * @param addKeywordVO
     * @return
     */
    List<TestcaseKeywordEntity> create(AddKeywordVO addKeywordVO);

    /**
     * 从testcase中移除keywords
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/26 12:24
     * @Param [testcaseId]
     * @return int
     **/
    int deleteByTestcaseId(Long testcaseId);

    /**
     * 通过testcaseId获取keywords列表
     *
     * @param testcaseId
     * @return
     */
    List<TestcaseKeywordEntity> getByTestcaseId(Long testcaseId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:10
     * @Param [testcaseIndexVO]
     * @return void
     **/
    void updateIdx(TestcaseIndexVO testcaseIndexVO);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 21:26
     * @Param [testcaseIds]
     * @return java.util.List<com.terry.iat.dao.entity.TestcaseKeywordEntity>
     **/
    List<TestcaseKeywordEntity> getByTestcaseIds(List<Long> testcaseIds);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/26 12:19
     * @Param [keywordId]
     * @return java.util.List<com.terry.iat.dao.entity.TestcaseKeywordEntity>
     **/
    List<TestcaseKeywordEntity> getByKeywordId(Long keywordId);

}

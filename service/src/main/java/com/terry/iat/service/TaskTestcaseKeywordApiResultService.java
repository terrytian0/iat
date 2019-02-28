package com.terry.iat.service;


import com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity;
import com.terry.iat.service.vo.TaskTestcaseKeywordApiResultVO;

import java.util.List;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/20 12:38
 * @Version 1.0 
 **/
public interface TaskTestcaseKeywordApiResultService {
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 14:08
     * @Param [taskTestcaseKeywordApiResultVO]
     * @return java.lang.Boolean
     **/
    Boolean create(TaskTestcaseKeywordApiResultVO taskTestcaseKeywordApiResultVO);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 20:47
     * @Param [taskId, testcaseId, parameterId, testcaseKeywordId, keywordId, keywordApiId, apiId]
     * @return com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity
     **/
    TaskTestcaseKeywordApiResultEntity get(Long taskId, Long testcaseId, Long parameterId,Long testcaseKeywordId, Long keywordId,Long keywordApiId, Long apiId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 21:14
     * @Param [taskId, testcaseId, parameterId, testcaseKeeywordId, keywordId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity>
     **/
    Integer checkKeyword(Long taskId, Long testcaseId, Long parameterId, Long testcaseKeeywordId, Long keywordId);

}

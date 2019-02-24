package com.terry.iat.service;


import com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity;
import com.terry.iat.service.vo.TaskTestcaseKeywordApiResultVO;

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
     * @Date 2019/2/22 12:44
     * @Param [taskId, testcaseId, parameterId, keywordId, apiId]
     * @return com.terry.iat.dao.entity.TaskResultEntity
     **/
    TaskTestcaseKeywordApiResultEntity get(Long taskId, Long testcaseId, Long parameterId, Long keywordId, Long apiId);
}

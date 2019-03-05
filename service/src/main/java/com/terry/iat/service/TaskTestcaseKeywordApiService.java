package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity;

import java.util.List;

/**
 * @author terry
 * @Description
 * @Date 2019/2/18 20:14
 * @Version 1.0
 **/
public interface TaskTestcaseKeywordApiService {
    /**
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity>
     * @Description TODO
     * @author terry
     * @Date 2019/2/21 12:19
     * @Param [taskId, testplanId, testcaseId, testcaseKeywordId, keywordId]
     **/
    List<TaskTestcaseKeywordApiEntity> create(Long taskId, Long testplanId, Long testcaseId, Long testcaseKeywordId, Long keywordId);

    /**
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity>
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 11:28
     * @Param [taskId]
     **/
    List<TaskTestcaseKeywordApiEntity> getByTaskId(Long taskId);

    /**
     * @return com.github.pagehelper.PageInfo
     * @Description TODO
     * @author terry
     * @Date 2019/2/21 12:33
     * @Param [pn, ps, taskId, testcaseId, testcaseKeywordId, keywordId]
     **/
    PageInfo getByTaskIdAndTestcaseIdAndKeywordId(Integer pn, Integer ps, Long taskId, Long testcaseId, Long parameterId, Long testcaseKeywordId, Long keywordId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/4 13:18
     * @Param [taskId, testplanId, testcaseId, testcaseKeywordId, keywordId, keywordApiId, apiId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity>
     **/
    TaskTestcaseKeywordApiEntity get(Long taskId, Long testcaseId, Long testcaseKeywordId, Long keywordId,Long keywordApiId,Long apiId);

}

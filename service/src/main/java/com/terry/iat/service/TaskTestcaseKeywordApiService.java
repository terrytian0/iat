package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity;
import com.terry.iat.dao.entity.TaskTestcaseKeywordEntity;

import java.util.List;

/**
 * @Description
 * @author terry
 * @Date 2019/2/18 20:14
 * @Version 1.0
 **/
public interface TaskTestcaseKeywordApiService {
   /**
    * @Description TODO
    * @author terry
    * @Date 2019/2/21 12:19
    * @Param [taskId, testplanId, testcaseId, testcaseKeywordId, keywordId]
    * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity>
    **/
    List<TaskTestcaseKeywordApiEntity> create(Long taskId,Long testplanId, Long testcaseId,Long testcaseKeywordId,Long keywordId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 11:28
     * @Param [taskId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity>
     **/
    List<TaskTestcaseKeywordApiEntity> getByTaskId(Long taskId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/21 12:33
     * @Param [pn, ps, taskId, testcaseId, testcaseKeywordId, keywordId]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getByTaskIdAndTestcaseIdAndKeywordId(Integer pn,Integer ps,Long taskId,Long testcaseId,Long testcaseKeywordId,Long keywordId);

}

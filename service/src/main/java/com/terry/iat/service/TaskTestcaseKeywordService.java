package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TaskTestcaseKeywordEntity;

import java.util.List;

/**
 * @Description
 * @author terry
 * @Date 2019/2/18 20:14
 * @Version 1.0
 **/
public interface TaskTestcaseKeywordService {
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 9:15
     * @Param [taskId, testplanId, testcaseId]
     * @return com.terry.iat.dao.entity.TaskTestcaseKeywordEntity
     **/
    List<TaskTestcaseKeywordEntity> create(Long taskId, Long testplanId, Long testcaseId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 11:28
     * @Param [taskId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseKeywordEntity>
     **/
    List<TaskTestcaseKeywordEntity> getByTaskId(Long taskId);
   /**
    * @Description TODO
    * @author terry
    * @Date 2019/2/25 20:35
    * @Param [pn, ps, taskId, testcaseId, parameterId]
    * @return com.github.pagehelper.PageInfo
    **/
    PageInfo getByTaskIdAndTestcaseId(Integer pn,Integer ps,Long taskId,Long testcaseId,Long parameterId);
}

package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TaskTestcaseKeywordEntity;
import com.terry.iat.dao.entity.TaskTestcaseParameterEntity;
import com.terry.iat.service.vo.TaskTestcaseParameterResultVO;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @author terry
 * @Date 2019/2/18 20:14
 * @Version 1.0
 **/
public interface TaskTestcaseParameterService {
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 9:49
     * @Param [taskId, testplanId, testcaseId]
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     **/
    List<TaskTestcaseParameterEntity> create(Long taskId, Long testplanId, Long testcaseId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 12:44
     * @Param [taskId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseParameterEntity>
     **/
    List<TaskTestcaseParameterEntity> getByTaskId(Long taskId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 21:27
     * @Param [taskId, testcaseId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseParameterEntity>
     **/
    List<TaskTestcaseParameterEntity> getByTaskIdAndTestcaseId(Long taskId, Long testcaseId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/20 11:08
     * @Param [pn, ps, taskId, testcaseId]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getByTaskIdAndTestcaseId(Integer pn, Integer ps, Long taskId, Long testcaseId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 11:06
     * @Param [id]
     * @return com.terry.iat.dao.entity.TaskTestcaseParameterEntity
     **/
    TaskTestcaseParameterEntity getById(Long id);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 11:01
     * @Param [taskTestcaseParameterResultVO]
     * @return java.lang.Boolean
     **/
    Boolean updateStatus(TaskTestcaseParameterResultVO taskTestcaseParameterResultVO);
}

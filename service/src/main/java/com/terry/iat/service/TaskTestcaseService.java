package com.terry.iat.service;


import com.terry.iat.dao.entity.TaskTestcaseEntity;

import java.util.List;

/**
 * @Description
 * @author terry
 * @Date 2019/2/18 20:14
 * @Version 1.0
 **/
public interface TaskTestcaseService {
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 9:23
     * @Param [taskId, testplanId]
     * @return com.terry.iat.dao.entity.TaskTestcaseEntity
     **/
    List<TaskTestcaseEntity> create(Long taskId, Long testplanId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 11:28
     * @Param [taskId]
     * @return java.util.List<com.terry.iat.dao.entity.TaskTestcaseEntity>
     **/
    List<TaskTestcaseEntity> getByTaskId(Long taskId);
}

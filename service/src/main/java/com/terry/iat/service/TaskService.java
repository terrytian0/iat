package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TaskEntity;
import com.terry.iat.dao.entity.TaskTestcaseEntity;
import com.terry.iat.dao.entity.TaskTestcaseParameterEntity;
import com.terry.iat.service.vo.NodeVO;
import com.terry.iat.service.vo.TaskResultVO;
import com.terry.iat.service.vo.TestplanVO;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Map;

/**
 * @Description 测试任务服务
 * @author terry
 * @Date 2019/2/18 20:14
 * @Version 1.0
 **/
public interface TaskService {
    /**
     * @Description 创建测试任务
     * @author terry
     * @Date 2019/2/18 20:14
     * @Param [testplanId]
     * @return com.terry.iat.dao.entity.TaskEntity
     **/
    TaskEntity create(Long testplanId);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 12:44
     * @Param [pn, ps, searchText, serviceId]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 10:21
     * @Param [client, key]
     * @return com.terry.iat.dao.entity.TaskEntity
     **/
    TaskEntity get(String client,String key);
    
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/3 19:28
     * @Param [taskId]
     * @return java.util.List<com.terry.iat.service.vo.NodeVO>
     **/
    List<NodeVO> detail(Long taskId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 11:05
     * @Param [taskId]
     * @return com.terry.iat.dao.entity.TaskEntity
     **/
    TaskEntity getById(Long taskId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 10:46
     * @Param [taskResultVO]
     * @return java.lang.Boolean
     **/
    Boolean updateStatus(TaskResultVO taskResultVO);


    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:21
     * @Param []
     * @return java.lang.Integer
     **/
    Integer getCount();

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:39
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     **/
    List<Map<String,Object>> getWeekChart();
}

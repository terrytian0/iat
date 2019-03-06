package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestplanEntity;
import com.terry.iat.dao.entity.TestplanTestcaseEntity;
import com.terry.iat.service.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author terry
 * @Description TODO
 * @Date 2019/2/18 10:34
 * @Version 1.0
 **/
public interface TestplanService {
    /**
     * @return com.terry.iat.dao.entity.TestplanEntity
     * @Description 创建测试计划
     * @author terry
     * @Date 2019/2/18 10:36
     * @Param [testplanVO]
     **/
    TestplanEntity create(TestplanVO testplanVO);

    /**
     * @return java.lang.Integer
     * @Description 删除测试计划
     * @author terry
     * @Date 2019/2/18 10:37
     * @Param [testplanId]
     **/
    Integer delete(Long testplanId);

    /**
     * @return com.terry.iat.dao.entity.TestplanEntity
     * @Description 修改测试计划
     * @author terry
     * @Date 2019/2/18 10:38
     * @Param [testplanVO]
     **/
    TestplanEntity update(TestplanVO testplanVO);

    /**
     * @return com.github.pagehelper.PageInfo
     * @Description 获取测试计划列表
     * @author terry
     * @Date 2019/2/18 10:39
     * @Param [pn, ps, searchText, serviceId]
     **/
    PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId);

    /**
     * @return com.terry.iat.dao.entity.TestplanEntity
     * @Description 测试计划详情
     * @author terry
     * @Date 2019/2/18 10:54
     * @Param [testplanId]
     **/
    TestplanEntity getById(Long testplanId);

    /**
     * @return java.util.List<com.terry.iat.dao.entity.TestplanTestcaseEntity>
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:23
     * @Param [addTestcaseVO]
     **/
    List<TestplanTestcaseEntity> addTestcase(AddTestcaseVO addTestcaseVO);

    /**
     * @return java.lang.Integer
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:23
     * @Param [removeTestcaseVO]
     **/
    Integer removeTestcase(RemoveTestcaseVO removeTestcaseVO);

    /**
     * @return java.util.List<com.terry.iat.service.vo.TestplanEnvVO>
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 11:07
     * @Param [testplanId]
     **/
    List<TestplanEnvVO> getEnvById(Long testplanId);

    /**
     * @return void
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 12:06
     * @Param [envs]
     **/
    void createEnv(List<TestplanCreateEnvVO> envs);

    /**
     * @return java.lang.Integer
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:21
     * @Param []
     **/
    Integer getCount();

    /**
     * @return java.util.List<java.util.Map       <       java.lang.String       ,       java.lang.String>>
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:39
     * @Param []
     **/
    List<Map<String, Object>> getWeekChart();
}

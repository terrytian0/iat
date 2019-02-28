package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.entity.TestplanEntity;
import com.terry.iat.dao.entity.TestplanTestcaseEntity;
import com.terry.iat.service.vo.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/18 10:34
 * @Version 1.0 
 **/
public interface TestplanService {
    /**
     * @Description 创建测试计划
     * @author terry
     * @Date 2019/2/18 10:36
     * @Param [testplanVO]
     * @return com.terry.iat.dao.entity.TestplanEntity
     **/
    TestplanEntity create(TestplanVO testplanVO);
    /**
     * @Description 删除测试计划
     * @author terry
     * @Date 2019/2/18 10:37
     * @Param [testplanId]
     * @return java.lang.Integer
     **/
    Integer delete(Long testplanId);
    /**
     * @Description 修改测试计划
     * @author terry
     * @Date 2019/2/18 10:38
     * @Param [testplanVO]
     * @return com.terry.iat.dao.entity.TestplanEntity
     **/
    TestplanEntity update(TestplanVO testplanVO);
    /**
     * @Description 获取测试计划列表
     * @author terry
     * @Date 2019/2/18 10:39
     * @Param [pn, ps, searchText, serviceId]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId);
    /**
     * @Description 测试计划详情
     * @author terry
     * @Date 2019/2/18 10:54
     * @Param [testplanId]
     * @return com.terry.iat.dao.entity.TestplanEntity
     **/
    TestplanEntity getById(Long testplanId);
    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/24 17:23
     * @Param [addTestcaseVO]
     * @return java.util.List<com.terry.iat.dao.entity.TestplanTestcaseEntity>
     **/
    List<TestplanTestcaseEntity> addTestcase(AddTestcaseVO addTestcaseVO);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:23
     * @Param [removeTestcaseVO]
     * @return java.lang.Integer
     **/
    Integer removeTestcase(RemoveTestcaseVO removeTestcaseVO);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 11:07
     * @Param [testplanId]
     * @return java.util.List<com.terry.iat.service.vo.TestplanEnvVO>
     **/
    List<TestplanEnvVO>  getEnvById(Long testplanId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 12:06
     * @Param [envs]
     * @return void
     **/
    void createEnv(List<TestplanCreateEnvVO> envs);
}

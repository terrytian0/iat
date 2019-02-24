package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestplanEntity;
import com.terry.iat.service.vo.TestplanVO;

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

}

package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestplanTestcaseEntity;
import com.terry.iat.service.vo.AddTestcaseVO;
import com.terry.iat.service.vo.TestplanIndexVO;

import java.util.List;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/18 10:34
 * @Version 1.0 
 **/
public interface TestplanTestcaseService {
    /**
     * @Description 创建测试计划
     * @author terry
     * @Date 2019/2/18 10:36
     * @Param [testplanAddTestcaseVO]
     * @return com.terry.iat.dao.entity.TestplanEntity
     **/
    List<TestplanTestcaseEntity> create(AddTestcaseVO addTestcaseVO);


     /**
      * @Description 删除测试计划中的测试用例
      * @author terry
      * @Date 2019/2/18 12:41
      * @Param [testcaseIds]
      * @return int
      **/
    int delete(List<Long> testcaseIds);

    /**
     * @Description 获取测试计划中的所有测试用例
     * @author terry
     * @Date 2019/2/18 12:35
     * @Param [testplanId]
     * @return java.util.List<com.terry.iat.dao.entity.TestplanTestcaseEntity>
     **/
    List<TestplanTestcaseEntity> getByTestplanId(Long testplanId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/26 12:27
     * @Param [testcaseId]
     * @return java.util.List<com.terry.iat.dao.entity.TestplanTestcaseEntity>
     **/
    List<TestplanTestcaseEntity> getByTestcaseId(Long testcaseId);
    /**
     * 修改测试用例在测试计划中的排序
     * @param testplanIndexVO
     */
    void updateIdx(TestplanIndexVO testplanIndexVO);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/18 14:05
     * @Param [pageNumber, pageSize, searchText, serviceId, testplanId]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getNotAddedTestcases(Integer pageNumber, Integer pageSize, String searchText, Long serviceId,Long testplanId);
}

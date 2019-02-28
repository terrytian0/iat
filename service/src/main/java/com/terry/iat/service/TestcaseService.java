package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestcaseEntity;
import com.terry.iat.dao.entity.TestcaseKeywordEntity;
import com.terry.iat.service.vo.*;
import com.terry.iat.service.core.TestcaseResult;

import java.util.List;
import java.util.Map;

/**
 * @author terry
 */
public interface TestcaseService {
    /**
     * 创建testcase
     *
     * @param testcaseVO
     * @return
     */
    TestcaseEntity create(TestcaseVO testcaseVO);

    /**
     * 修改testcase
     *
     * @param testcaseVO
     * @return
     */
    TestcaseEntity update(TestcaseVO testcaseVO);

    /**
     * 批量删除testcase
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);
    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/24 17:12
     * @Param [addKeywordVO]
     * @return java.util.List<com.terry.iat.dao.entity.TestcaseKeywordEntity>
     **/
    List<TestcaseKeywordEntity> addKeyword(AddKeywordVO addKeywordVO);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:12
     * @Param [removeKeywordVO]
     * @return java.lang.Integer
     **/
    Integer removeKeyword(RemoveKeywordVO removeKeywordVO);


    /**
     * 获取testcase详情
     *
     * @param id
     * @return
     */
    TestcaseEntity getById(Long id);

    /**
     * @param pn
     * @param ps
     * @param serviceId
     * @return
     */
    PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/18 13:52
     * @Param [pn, ps, searchText, serviceId, ids]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getByServiceIdAndNotInIds(Integer pn, Integer ps, String searchText, Long serviceId,List<Long> ids);

    /**
     * 测试用例debug
     * @param testcaseDebugVO
     */
    TestcaseResult debug(TestcaseDebugVO testcaseDebugVO);

    /**
     * 获取测试用例中的参数
     * @param testcaseId
     * @return
     */
    Map<String,String> getParameters(Long testcaseId);

    /**
     * @Description 获取测试用例
     * @author terry
     * @Date 2019/2/18 12:56
     * @Param [testcaseIds]
     * @return java.util.List<com.terry.iat.dao.entity.TestcaseEntity>
     **/
    List<TestcaseEntity> getByIds(List<Long> testcaseIds);
}

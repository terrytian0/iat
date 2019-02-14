package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestcaseEntity;
import com.terry.iat.service.vo.TestcaseDebugVO;
import com.terry.iat.service.core.TestcaseResult;
import com.terry.iat.service.vo.TestcaseVO;

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
}

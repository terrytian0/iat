package com.terry.iat.service;

import com.terry.iat.dao.entity.ParameterValueEntity;
import com.terry.iat.service.vo.TestcaseAddParameterVO;

import java.util.List;
import java.util.Map;

public interface ParameterValueService {
    /**
     * 获取testcase下的所有参数
     * @param testcaseId
     * @return
     */
    List<Map<String,String>> getByTestcaseId(Long testcaseId);

    int deleteByKeyIds(List<Long> ids);

    /**
     * 创建或更新测试用例中的参数
     * @param testcaseAddParameterVOS
     * @return
     */
    int createOrUpdate(List<TestcaseAddParameterVO> testcaseAddParameterVOS);

    /**
     * 删除测试用例中的一行参数
     * @param testcaseId
     * @param rowNum
     * @return
     */
    int delete(Long testcaseId,Integer rowNum);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 18:14
     * @Param [testcaseId]
     * @return java.lang.Integer
     **/
    Integer getCountByTestcaseId(Long testcaseId);

}

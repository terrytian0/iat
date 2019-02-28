package com.terry.iat.service;

import com.terry.iat.dao.entity.AssertEntity;
import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.service.vo.AssertVO;
import com.terry.iat.service.vo.ExtractorVO;
import com.terry.iat.service.vo.ParameterVO;

import java.util.List;

public interface AssertService {
    /**
     * 创建断言
     * @param assertVO
     * @return
     */
    AssertEntity create(AssertVO assertVO);


    /**
     * 获取Api下的所有断言
     * @param keywordApiId
     * @return
     */
    List<AssertEntity> getByKeywordApiId(Long keywordApiId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/26 13:55
     * @Param [keywordApiId]
     * @return java.util.List<com.terry.iat.service.vo.ParameterVO>
     **/
    List<ParameterVO> getParameters(Long keywordApiId);

    /**
     * 删除断言
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 删除api下的所有提取器
     * @param keywordApiId
     * @return
     */
    int deleteByKeywordApiId(Long keywordApiId);

}

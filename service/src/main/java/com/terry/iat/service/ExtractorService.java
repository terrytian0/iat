package com.terry.iat.service;

import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.service.vo.ExtractorVO;

import java.util.List;

public interface ExtractorService {
    /**
     * 创建提取器
     * @param extractorVO
     * @return
     */
    ExtractorEntity create(ExtractorVO extractorVO);

    /**
     * 修改提取器
     * @param extractorVO
     * @return
     */
    ExtractorEntity update(ExtractorVO extractorVO);

    /**
     * 获取Api下的所有提取器
     * @param keywordApiId
     * @return
     */
    List<ExtractorEntity> getByKeywordApiId(Long keywordApiId);
    /**
     * @Description 获取keyword下的提取器并合并
     * @author terry
     * @Date 2019/2/14 9:57
     * @Param [keywordApiId, extractors]
     * @return java.util.List<com.terry.iat.dao.entity.ExtractorEntity>
     **/
    List<ExtractorEntity> getExtractors(Long keywordApiId,List<ExtractorEntity> extractors );

    /**
     * 删除提取器
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

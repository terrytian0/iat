package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.dao.entity.KeywordApiEntity;
import com.terry.iat.dao.entity.KeywordEntity;
import com.terry.iat.service.core.KeywordResult;
import com.terry.iat.service.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author terry
 */
public interface KeywordService {
    /**
     * 创建keywords
     *
     * @param keywordVO
     * @return
     */
    KeywordEntity create(KeywordVO keywordVO);

    /**
     * 修改keywords
     *
     * @param keywordVO
     * @return
     */
    KeywordEntity update(KeywordVO keywordVO);

    /**
     * 批量删除keywords
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);

    /**
     * 获取keywords详情
     *
     * @param id
     * @return
     */
    KeywordEntity getById(Long id);

    /**
     * 通过服务Id获取关键字列表
     *
     * @param pn
     * @param ps
     * @param serviceId
     * @return
     */
    PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId);

    /**
     * 通过ids获取keywords
     *
     * @param ids
     * @return
     */
    List<KeywordEntity> getByIds(List<Long> ids);

    /**
     * 获取keyword下的所有参数
     * @param keywordId
     * @param extractors
     * @return
     */
    List<ParameterVO> getParameters(Long keywordId, List<ExtractorEntity> extractors);

    /**
     * 获取keyword下的所有提取器，并合并
     * @param keywordId
     * @param extractors
     * @return
     */
    List<ExtractorEntity> getExtractor(Long keywordId, List<ExtractorEntity> extractors);

    /**
     * keyword调试
     *
     * @param keywordDebugVO
     */
    KeywordResult debug(KeywordDebugVO keywordDebugVO);

    /**
     * keyword调试
     *
     * @param keywordEntity
     * @param parameters
     * @param envEntity
     * @return
     */
    KeywordResult debug(KeywordEntity keywordEntity, Map<String, String> parameters, EnvEntity envEntity);

    /**
     *  向Keyword中添加Api
     * @param addApiVO
     * @return
     */
    List<KeywordApiEntity> addApi(AddApiVO addApiVO);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:05
     * @Param [removeApiVO]
     * @return java.lang.Integer
     **/
    Integer removeApi(RemoveApiVO removeApiVO);
}

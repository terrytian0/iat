package com.terry.iat.service;


import com.terry.iat.dao.entity.KeywordApiEntity;
import com.terry.iat.service.vo.AddApiVO;
import com.terry.iat.service.vo.KeywordIndexVO;

import java.util.List;

/**
 * @author terry
 */
public interface KeywordApiService {
    /**
     * 创建keywordsApi
     *
     * @param addApiVO
     * @return
     */
    List<KeywordApiEntity> create(AddApiVO addApiVO);

    /**
     * 从keywords中移除api
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);

    /**
     * 删除Keyword下的所有Api
     * @param keywordId
     * @return
     */
    int delete(Long keywordId);

    /**
     * 通过keywordsId查询Api
     *
     * @param keywordId
     * @return
     */
    List<KeywordApiEntity> getByKeywordId(Long keywordId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 21:29
     * @Param [keywordIds]
     * @return java.util.List<com.terry.iat.dao.entity.KeywordApiEntity>
     **/
    List<KeywordApiEntity> getByKeywordIds(List<Long> keywordIds);

    /**
     * 修改Keyword排序
     * @param keywordIndexVO
     */
    void updateIdx(KeywordIndexVO keywordIndexVO);
}

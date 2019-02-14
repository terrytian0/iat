package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.KeywordApiEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 *
 * @author houyin.tian
 * @date 2017/11/1
 */
@Repository
@DataSource(value="iat")
public interface KeywordApiMapper extends BaseMapper<KeywordApiEntity> {
    @Select("SELECT MAX(idx) FROM keyword_api WHERE keyword_id=#{keywordId}")
    Integer getMaxIdx(Long keywordId);

    @Update("UPDATE keyword_api SET idx=idx+1 WHERE keyword_id=#{keywordId} AND idx<#{idx}")
    Integer updateFrist(@Param("keywordId") Long keywordId, @Param("idx") Integer idx);
    @Update("UPDATE keyword_api SET idx=idx-1 WHERE keyword_id=#{keywordId} AND idx>#{idx}")
    Integer updateLast(@Param("keywordId") Long keywordId,@Param("idx")Integer idx);
}

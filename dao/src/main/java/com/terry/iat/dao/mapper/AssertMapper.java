package com.terry.iat.dao.mapper;

import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.AssertEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DataSource(value="iat")
public interface AssertMapper extends BaseMapper<AssertEntity> {
    @Select("SELECT * FROM assert WHERE keyword_api_id=#{keywordApiId}")
    public List<AssertEntity> getByKeywordApiId(Long keywordApiId);
    @Delete("DELETE FROM assert WHERE keyword_api_id=#{keywordApiId}")
    public Integer deleteByKeywordApiId(Long keywordApiId);

}

package com.terry.iat.dao.mapper;

import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.ExtractorEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DataSource(value="iat")
public interface ExtractorMapper extends BaseMapper<ExtractorEntity> {
    @Select("SELECT * FROM extractor WHERE keyword_api_id=#{keywordApiId}")
    public List<ExtractorEntity> getByKeywordApiId(Long keywordApiId);
    @Delete("DELETE FROM extractor WHERE keyword_api_id=#{keywordApiId}")
    public Integer deleteByKeywordApiId(Long keywordApiId);

}

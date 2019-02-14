package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TestcaseKeywordApiEntity;
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
public interface TestcaseKeywordsApiMapper extends BaseMapper<TestcaseKeywordApiEntity> {
    @Select("SELECT MAX(idx) FROM testcase_keyword_api WHERE testcase_id=#{testcaseId}")
    Integer getMaxIdx(Long testcaseId);

    @Update("UPDATE testcase_keyword_api SET idx=idx+1 WHERE testcase_id=#{testcaseId} AND idx<#{idx}")
    Integer updateFrist(@Param("testcaseId") Long testcaseId, @Param("idx") Integer idx);
    @Update("UPDATE testcase_keyword_api SET idx=idx-1 WHERE testcase_id=#{testcaseId} AND idx>#{idx}")
    Integer updateLast(@Param("testcaseId") Long testcaseId,@Param("idx")Integer idx);
}

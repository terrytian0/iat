package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/20 12:58
 * @Version 1.0 
 **/
@Repository
@DataSource(value="iat")
public interface TaskTestcaseKeywordApiResultMapper extends BaseMapper<TaskTestcaseKeywordApiResultEntity> {
    @Select("select * from task_testcase_keyword_api_result where task_id=#{taskId} AND testcase_id=#{testcaseId} AND parameter_id=#{parameterId} AND testcase_keyword_id=#{testcaseKeywordId} AND keyword_id=#{keywordId}")
    List<TaskTestcaseKeywordApiResultEntity> getByKeyword(@Param("taskId") Long taskId, @Param("testcaseId")Long testcaseId, @Param("parameterId")Long parameterId,@Param("testcaseKeywordId")Long testcaseKeywordId, @Param("keywordId")Long keywordId);

    @Select("select * from task_testcase_keyword_api_result where task_id=#{taskId} AND testcase_id=#{testcaseId} AND parameter_id=#{parameterId} and testcase_keyword_id=#{testcaseKeywordId}  AND keyword_id=#{keywordId} and keyword_api_id=#{keywordApiId} and api_id=#{apiId}")
    TaskTestcaseKeywordApiResultEntity get(@Param("taskId") Long taskId, @Param("testcaseId")Long testcaseId, @Param("parameterId")Long parameterId,@Param("testcaseKeywordId")Long testcaseKeywordId, @Param("keywordId")Long keywordId,@Param("keywordApiId")Long keywordApiId, @Param("apiId")Long apiId);
}

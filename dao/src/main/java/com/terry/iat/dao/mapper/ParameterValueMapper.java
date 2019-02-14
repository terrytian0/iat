package com.terry.iat.dao.mapper;

import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.entity.ParameterValueEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@DataSource(value="iat")
public interface ParameterValueMapper  extends BaseMapper<ParameterValueEntity> {
    @Select("SELECT MAX(row_num) FROM parameter_value WHERE testcase_id=#{testcaseId}")
    Integer getMaxRowNum(Long testcaseId);
}

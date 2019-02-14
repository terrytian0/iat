package com.terry.iat.dao.mapper;

import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.entity.ParameterKeyEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@DataSource(value="iat")
public interface ParameterKeyMapper extends BaseMapper<ParameterKeyEntity> {
    /**
     *
     * @param testcaseId
     * @return
     */
    @Select("SELECT * FROM parameter_key WHERE testcase_id=#{testcaseId}")
    List<ParameterKeyEntity> getByTestcaseId(Long testcaseId);
}

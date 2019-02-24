package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TaskTestcaseParameterEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author terry         
 * @Date 2019/2/18 10:29
 * @Version 1.0 
 **/
@Repository
@DataSource(value="iat")
public interface TaskTestcaseParameterMapper extends BaseMapper<TaskTestcaseParameterEntity> {
}

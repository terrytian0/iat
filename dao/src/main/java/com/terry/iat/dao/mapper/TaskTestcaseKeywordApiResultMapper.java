package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/20 12:58
 * @Version 1.0 
 **/
@Repository
@DataSource(value="iat")
public interface TaskTestcaseKeywordApiResultMapper extends BaseMapper<TaskTestcaseKeywordApiResultEntity> {
}

package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TaskEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author terry         
 * @Date 2019/2/18 10:29
 * @Version 1.0 
 **/
@Repository
@DataSource(value="iat")
public interface TaskMapper extends BaseMapper<TaskEntity> {
    @Select("select * from task where status in ('CREATE') limit 1")
    TaskEntity getNotRun();
}

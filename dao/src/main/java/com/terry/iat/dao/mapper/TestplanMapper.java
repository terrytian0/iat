package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TestplanEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/18 10:35
 * @Version 1.0 
 **/
@Repository
@DataSource(value="iat")
public interface TestplanMapper extends BaseMapper<TestplanEntity> {
}

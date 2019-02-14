package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.ServiceEntity;
import org.springframework.stereotype.Repository;


@Repository
@DataSource(value="iat")
public interface ServiceMapper extends BaseMapper<ServiceEntity> {
}

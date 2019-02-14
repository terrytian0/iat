package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.EnvEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@DataSource(value="iat")
public interface EnvMapper extends BaseMapper<EnvEntity> {
    @Select("select * from env where service_id=#{serviceId}")
    List<EnvEntity> getByServiceId(Long serviceId);
}

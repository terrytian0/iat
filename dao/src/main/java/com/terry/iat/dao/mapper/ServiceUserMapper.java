package com.terry.iat.dao.mapper;


import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.entity.ServiceUserEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author houyin.tian
 * @date 2017/11/1
 */
@Repository
@DataSource(value="iat")
public interface ServiceUserMapper extends BaseMapper<ServiceUserEntity> {
    @Select("SELECT * FROM service_user WHERE service_id = #{serviceId}")
    List<ServiceUserEntity> getByServiceId(Long serviceId);
}

package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.ClientEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 21:19
 * @Param 
 * @return 
 **/
@Repository
@DataSource(value="iat")
public interface ClientMapper extends BaseMapper<ClientEntity> {
}

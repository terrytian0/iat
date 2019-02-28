package com.terry.iat.service;


import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.service.vo.EnvVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author terry
 */
public interface EnvService {
    /**
     * 创建Env
     * @param envVO
     * @return
     */
    EnvEntity create(EnvVO envVO);

    /**
     *
     * @param serviceId
     * @return
     */
    List<EnvEntity> getByServiceId(Long serviceId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 21:37
     * @Param [serviceIds]
     * @return java.util.Map<java.lang.Long,java.util.List<com.terry.iat.dao.entity.EnvEntity>>
     **/
    Map<Long,List<EnvEntity>> getByServiceIds(Set<Long> serviceIds);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 12:10
     * @Param [ids]
     * @return java.util.List<com.terry.iat.dao.entity.EnvEntity>
     **/
    List<EnvEntity> getByIds(List<Long> ids);

    /**
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     *
     * @param envId
     * @return
     */
    EnvEntity getById(Long envId);
}

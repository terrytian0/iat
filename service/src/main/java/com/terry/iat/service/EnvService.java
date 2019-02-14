package com.terry.iat.service;


import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.service.vo.EnvVO;

import java.util.List;

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

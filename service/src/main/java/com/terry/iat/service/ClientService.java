package com.terry.iat.service;

import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ClientEntity;
import com.terry.iat.service.common.enums.ClientStatus;

/**
 * @author terry
 * @version 1.0
 * @class name ClientService
 * @description TODO
 * @date 2019/2/23 20:23
 **/
public interface ClientService {
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 20:24
     * @Param [client]
     * @return com.terry.iat.dao.entity.ClientEntity
     **/
    ClientEntity register(String client);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 21:28
     * @Param [client, key]
     * @return void
     **/
    void heartbeat(String client,String key);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 21:15
     * @Param [client]
     * @return com.terry.iat.dao.entity.ClientEntity
     **/
    ClientEntity getByClient(String client);

    /**
     * @Description 检查Client合法性
     * @author terry
     * @Date 2019/2/23 21:21
     * @Param [client, key]
     * @return void
     **/
    void check(String client,String key);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 21:06
     * @Param []
     * @return void
     **/
    void initClientCache();

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 11:07
     * @Param [pn, ps, searchText]
     * @return com.github.pagehelper.PageInfo
     **/
    PageInfo getByKeys(Integer pn, Integer ps, String searchText);

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 11:23
     * @Param [id, status]
     * @return void
     **/
    void updateStatus(Long id,ClientStatus status);
}

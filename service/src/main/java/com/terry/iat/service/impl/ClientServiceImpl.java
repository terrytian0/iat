package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ClientEntity;
import com.terry.iat.dao.mapper.ClientMapper;
import com.terry.iat.service.ClientService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.ClientStatus;
import com.terry.iat.service.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author terry
 * @version 1.0
 * @class name ClientServiceImpl
 * @description TODO
 * @date 2019/2/23 20:24
 **/
@Service
public class ClientServiceImpl extends BaseServiceImpl implements ClientService {

    private final static Map<String, ClientEntity> CLIENTS_CACHE = new ConcurrentHashMap<>();
    /**
     * Client 1分钟失联
     */
    private final static Long CLIENT_MISSING_TIME = 300000L;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public ClientEntity register(String client) {
        ClientEntity clientEntity = getByClient(client);
        if (clientEntity == null) {
            clientEntity = getFromDb(client);
        }
        if (clientEntity == null) {
            clientEntity = create(client);
        } else {
            clientEntity = update(clientEntity);
        }
        if (clientEntity != null) {
            CLIENTS_CACHE.put(client, clientEntity);
        } else {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage(client + "注册失败！"));
        }
        return clientEntity;
    }

    @Override
    public void heartbeat(String client, String key) {
        check(client, key);
        ClientEntity clientEntity = CLIENTS_CACHE.get(client);
        clientEntity.setLastTime(getTimestamp());
        int rows = clientMapper.updateByPrimaryKey(clientEntity);
        if (rows == 1) {
            CLIENTS_CACHE.put(client, clientEntity);
        }
    }

    @Override
    public ClientEntity getByClient(String client) {
        return CLIENTS_CACHE.get(client);
    }

    @Override
    public void check(String client, String key) {
        ClientEntity clientEntity = CLIENTS_CACHE.get(client);
        if (clientEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Client " + client + " 未注册！！"));
        }
        if (!clientEntity.getKey().equals(key)) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Client Key 错误！！"));
        }
        if (checkMissing(clientEntity.getLastTime())) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Client " + client + " 已失联！！"));
        }
    }

    @Override
    public void initClientCache() {
        List<ClientEntity> clientEntityList = clientMapper.selectAll();
        clientEntityList.forEach(clientEntity -> {
            if (!checkMissing(clientEntity.getLastTime())) {
                CLIENTS_CACHE.put(clientEntity.getClient(), clientEntity);
            }
        });
    }

    @Override
    public PageInfo getByKeys(Integer pn, Integer ps, String searchText) {
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(ClientEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("client", key);
        criteria.orLike("status", key);
        return new PageInfo(clientMapper.selectByExample(example));
    }

    @Override
    public void updateStatus(Long id,ClientStatus status) {
        ClientEntity clientEntity = clientMapper.selectByPrimaryKey(id);
        if (clientEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Client 不存在！"));
        }
        clientEntity.setStatus(status.name());
        int rows = clientMapper.updateByPrimaryKey(clientEntity);
        if (rows != 1) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("修改Client状态失败"));
        }
    }


    private ClientEntity getFromDb(String client) {
        Example example = new Example(ClientEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("client", client);
        return clientMapper.selectOneByExample(example);
    }

    private ClientEntity create(String client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClient(client);
        clientEntity.setStatus(ClientStatus.NORMAL.name());
        clientEntity.setRegistrationTime(getTimestamp());
        clientEntity.setLastTime(getTimestamp());
        clientEntity.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
        int rows = clientMapper.insert(clientEntity);
        if (rows == 1) {
            return clientEntity;
        } else {
            return null;
        }
    }

    private ClientEntity update(ClientEntity clientEntity) {
        clientEntity.setStatus(ClientStatus.NORMAL.name());
        clientEntity.setLastTime(getTimestamp());
        clientEntity.setKey(UUID.randomUUID().toString().replaceAll("-", ""));
        int rows = clientMapper.updateByPrimaryKey(clientEntity);
        if (rows == 1) {
            return clientEntity;
        } else {
            return null;
        }
    }


    @Scheduled(cron = "0 0/1 * * * ?")
    private void clear() {
        Set<String> clients = new HashSet<>();
        for (ClientEntity clientEntity : CLIENTS_CACHE.values()) {
            if (checkMissing(clientEntity.getLastTime())) {
                clients.add(clientEntity.getClient());
            }
        }
        clients.forEach(client -> {
            ClientEntity clientEntity = CLIENTS_CACHE.get(client);
            clientEntity.setStatus(ClientStatus.MISSING.name());
            int rows = clientMapper.updateByPrimaryKey(clientEntity);
            if (rows == 1) {
                CLIENTS_CACHE.remove(client);
            }
        });
    }

    /**
     * @return boolean
     * @Description 失联判断
     * @author terry
     * @Date 2019/2/23 21:11
     * @Param [lasTime]
     **/
    private boolean checkMissing(Timestamp lasTime) {
        if ((lasTime.getTime() + CLIENT_MISSING_TIME) < getTimestamp().getTime()) {
            return true;
        }
        return false;
    }

}

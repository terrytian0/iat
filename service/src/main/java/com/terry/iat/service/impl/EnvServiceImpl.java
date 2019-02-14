package com.terry.iat.service.impl;

import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.mapper.EnvMapper;
import com.terry.iat.service.EnvService;
import com.terry.iat.service.vo.EnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnvServiceImpl extends BaseServiceImpl implements EnvService {

    @Autowired
    private EnvMapper envMapper;

    @Override
    public EnvEntity create(EnvVO envVO) {
        List<EnvEntity> envEntityList = getByServiceId(envVO.getServiceId());
        Map<String, EnvEntity> envs = new HashMap<>();
        envEntityList.forEach(envEntity -> {
            envs.put(envEntity.getEnv(), envEntity);
        });
        EnvEntity envEntity = envs.get(envVO.getEnv());
        if (envEntity == null) {
            envEntity = new EnvEntity();
            envEntity.setServiceId(envVO.getServiceId());
            envEntity.setEnv(envVO.getEnv());
            envEntity.setHost(envVO.getHost());
            envEntity.setPort(envVO.getPort());
            envMapper.insert(envEntity);
        } else {
            envEntity.setHost(envVO.getHost());
            envMapper.updateByPrimaryKey(envEntity);
        }
        return envEntity;
    }

    @Override
    public List<EnvEntity> getByServiceId(Long serviceId) {
        return envMapper.getByServiceId(serviceId);
    }

    @Override
    public int delete(Long id) {
        return envMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EnvEntity getById(Long envId) {
        return envMapper.selectByPrimaryKey(envId);
    }
}

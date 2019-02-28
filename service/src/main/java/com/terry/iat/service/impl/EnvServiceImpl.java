package com.terry.iat.service.impl;

import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.mapper.EnvMapper;
import com.terry.iat.service.EnvService;
import com.terry.iat.service.vo.EnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
    public Map<Long, List<EnvEntity>> getByServiceIds(Set<Long> serviceIds) {
        if(serviceIds.isEmpty()){
            return Collections.EMPTY_MAP;
        }
        Example example = new Example(EnvEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("serviceId",serviceIds);
        List<EnvEntity> envEntityList = envMapper.selectByExample(example);
        Map<Long, List<EnvEntity>> map = new HashMap<>();
        for (EnvEntity envEntity : envEntityList) {
            List<EnvEntity> envEntities = map.get(envEntity.getServiceId());
            if(envEntities==null){
                envEntities = new ArrayList<>();
            }
            envEntities.add(envEntity);
            map.put(envEntity.getServiceId(),envEntities);
        }
        return map;
    }

    @Override
    public List<EnvEntity> getByIds(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(EnvEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",ids);
        return envMapper.selectByExample(example);
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

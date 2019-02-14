package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.Role;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.entity.ServiceEntity;
import com.terry.iat.dao.entity.ServiceUserEntity;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.dao.mapper.ServiceMapper;
import com.terry.iat.service.EnvService;
import com.terry.iat.service.ServiceService;
import com.terry.iat.service.ServiceUserService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.vo.ServiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceServiceImpl extends BaseServiceImpl implements ServiceService {

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private EnvService envService;

    @Autowired
    private ServiceUserService serviceUserService;

    @Override
    public ServiceEntity create(ServiceVO serviceVO) {
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName(serviceVO.getName());
        serviceEntity.setDescription(serviceVO.getDescription());
        serviceEntity.setCreateTime(getTimestamp());
        serviceEntity.setCreateUser(getCurrentUser().getName());
        serviceEntity.setUpdateTime(getTimestamp());
        serviceEntity.setUpdateUser(getCurrentUser().getName());
        serviceEntity.setUniqueKey(UUID.randomUUID().toString());
        serviceMapper.insert(serviceEntity);
        return serviceEntity;
    }

    @Override
    public ServiceEntity update(ServiceVO serviceVO) {
        ServiceEntity serviceEntity = serviceMapper.selectByPrimaryKey(serviceVO.getId());
        if (serviceEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("service not exist!"));
        }
        serviceEntity.setName(serviceVO.getName());
        serviceEntity.setDescription(serviceVO.getDescription());
        serviceEntity.setUpdateTime(getTimestamp());
        serviceEntity.setUpdateUser(getCurrentUser().getName());
        serviceMapper.updateByPrimaryKey(serviceEntity);
        return serviceEntity;
    }

    @Override
    public int delete(Long id) {
        return serviceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ServiceEntity getById(Long id) {
        ServiceEntity serviceEntity = serviceMapper.selectByPrimaryKey(id);
        if (serviceEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("服务不存在！"));
        }
        List<EnvEntity> envEntityList = envService.getByServiceId(id);
        serviceEntity.setEnvs(envEntityList);
        return serviceEntity;
    }

    @Override
    public ServiceEntity getByUniqueKey(String uniqueKey) {
        Example example = new Example(ServiceEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uniqueKey", uniqueKey);
        List<ServiceEntity> serviceEntityList = serviceMapper.selectByExample(example);
        if (serviceEntityList == null || serviceEntityList.isEmpty()) {
            return null;
        }
        return serviceEntityList.get(0);
    }

    @Override
    public PageInfo search(Integer pn, Integer ps, String searchText) {
        UserEntity userEntity = getCurrentUser();
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(ServiceEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("name", key);
        Role role = Role.getRole(userEntity.getRole());
        if (Role.ADMIN != role) {
            List<Long> serviceIds = new ArrayList<>();
            List<ServiceUserEntity> serviceUserEntityList = serviceUserService.getByUserId(userEntity.getId());
            for (ServiceUserEntity serviceUserEntity : serviceUserEntityList) {
                serviceIds.add(serviceUserEntity.getServiceId());
            }
            if (serviceIds.isEmpty()) {
                return new PageInfo(Collections.EMPTY_LIST);
            } else {
                criteria.andIn("id", serviceIds);
            }
        }
        return new PageInfo(serviceMapper.selectByExample(example));
    }
}

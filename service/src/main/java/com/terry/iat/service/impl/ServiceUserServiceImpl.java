package com.terry.iat.service.impl;

import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ServiceUserEntity;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.dao.mapper.ServiceUserMapper;
import com.terry.iat.service.ServiceUserService;
import com.terry.iat.service.UserService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.vo.ServiceAddUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ServiceUserServiceImpl extends BaseServiceImpl implements ServiceUserService {

    @Autowired
    private ServiceUserMapper serviceUserMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<ServiceUserEntity> getByServiceId(Long serviceId) {
        List<ServiceUserEntity> serviceUserEntityList = serviceUserMapper.getByServiceId(serviceId);
        if(serviceUserEntityList==null){
            return Collections.emptyList();
        }
        List<Long> userIds = new ArrayList<>();

        for (ServiceUserEntity serviceUserEntity : serviceUserEntityList) {
            userIds.add(serviceUserEntity.getUserId());
        }
        List<UserEntity> userEntities = userService.getByIds(userIds);
        Map<Long,UserEntity> userEntityMap = new HashMap<>();
        for (UserEntity userEntity : userEntities) {
            userEntityMap.put(userEntity.getId(),userEntity);
        }
        for (ServiceUserEntity serviceUserEntity : serviceUserEntityList) {
            serviceUserEntity.setDetail(userEntityMap.get(serviceUserEntity.getUserId()));
        }
        return serviceUserEntityList;
    }

    @Override
    public List<ServiceUserEntity> getByUserId(Long userId) {
        Example example = new Example(ServiceUserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        return serviceUserMapper.selectByExample(example);
    }

    @Override
    public PageInfo getUnaddedUserByServiceId(Integer pn, Integer ps,Long serviceId) {
        List<ServiceUserEntity> serviceUserEntityList = serviceUserMapper.getByServiceId(serviceId);

        if(serviceUserEntityList==null){
            return new PageInfo(Collections.emptyList());
        }
        List<Long> userIds = new ArrayList<>();

        for (ServiceUserEntity serviceUserEntity : serviceUserEntityList) {
            userIds.add(serviceUserEntity.getUserId());
        }

       return userService.getByNotIds(pn,ps,userIds);

    }

    @Override
    public int delete(Long id) {
        return serviceUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int create(ServiceAddUserVO serviceAddUserVO) {
        List<ServiceUserEntity> serviceUserEntityList = new ArrayList<>();
        for (Long userId : serviceAddUserVO.getUserIds()) {
            ServiceUserEntity serviceUserEntity = new ServiceUserEntity();
            serviceUserEntity.setServiceId(serviceAddUserVO.getServiceId());
            serviceUserEntity.setUserId(userId);
            serviceUserEntityList.add(serviceUserEntity);
        }
        return serviceUserMapper.insertList(serviceUserEntityList);
    }
}

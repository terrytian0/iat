package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ServiceUserEntity;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.service.vo.ServiceAddUserVO;
import com.terry.iat.service.vo.UserVO;

import java.util.List;

/**
 * @author terry
 */
public interface ServiceUserService {
   List<ServiceUserEntity> getByServiceId(Long serviceId);
   List<ServiceUserEntity> getByUserId(Long userId);


   PageInfo getUnaddedUserByServiceId(Integer pn, Integer ps,Long serviceId);

   int delete(Long id);

   int create(ServiceAddUserVO serviceAddUserVO);
}

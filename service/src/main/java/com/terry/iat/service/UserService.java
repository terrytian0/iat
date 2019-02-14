package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ApiEntity;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.service.core.HttpResult;
import com.terry.iat.service.vo.ApiVO;
import com.terry.iat.service.vo.ParameterVO;
import com.terry.iat.service.vo.UserVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author terry
 */
public interface UserService {
    /**
     * 创建用户
     * @param userVO
     * @return
     */
    int create(UserVO userVO);

    /**
     * 更新用户
     * @param userVO
     * @return
     */
    int update(UserVO userVO);
    /**
     * 用户登录
     * @param userVO
     * @return
     */
    UserEntity login(UserVO userVO);
    /**
     * 推出登陆
     * @param token
     */
    void logout(String  token);
    /**
     * 删除用户
     * @param userId
     * @return
     */
    int delete(Long userId);

    /**
     * 清除无效session
     * @param token
     */
    void clearInvalidSession(String token);

    /**
     * 通过token获取用户信息
     * @param token
     * @return
     */
    UserEntity getByToken(String token);

    /**
     * 设置session缓存
     * @param userEntity
     */
    void setSessionCache(UserEntity userEntity);

    /**
     * 通过关键字获取用户
     * @param searchText
     * @return
     */
    PageInfo getByKeys(Integer pn, Integer ps,String  searchText);

    /**
     * 获取用户详情
     * @param userId
     * @return
     */
    UserEntity getById(Long userId);

    List<UserEntity> getByIds(List<Long> ids);

    PageInfo getByNotIds(Integer pn, Integer ps,List<Long> ids);
}

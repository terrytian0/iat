package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.Role;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.dao.mapper.UserMapper;
import com.terry.iat.service.UserService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

    private final static Map<String, UserEntity> sessionCache = new ConcurrentHashMap<>();


    @Autowired
    private UserMapper userMapper;

    @Override
    public int create(UserVO userVO) {
        UserEntity userEntity = userMapper.getByName(userVO.getName());
        if (userEntity != null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用户已经存在！"));
        }
        userEntity = new UserEntity();
        userEntity.setName(userVO.getName());
        userEntity.setPhone(userVO.getPhone());
        if(userVO.getRole()==null){
            userEntity.setRole(Role.NORMAL.name());
        }else {
            userEntity.setRole(userVO.getRole().name());
        }
        userEntity.setPassword(DigestUtils.md5DigestAsHex(userVO.getPassword().getBytes()));
        return userMapper.insert(userEntity);
    }

    @Override
    public int update(UserVO userVO) {
        UserEntity userEntity = userMapper.selectByPrimaryKey(userVO.getId());
        if (userEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用户不存在！"));
        }
        userEntity.setPhone(userVO.getPhone());
        userEntity.setRole(userVO.getRole().name());
        userEntity.setPassword(DigestUtils.md5DigestAsHex(userVO.getPassword().getBytes()));
        return userMapper.updateByPrimaryKey(userEntity);
    }

    @Override
    public UserEntity login(UserVO userVO) {
        UserEntity userEntity = userMapper.getByNameAndPassword(userVO.getName(), DigestUtils.md5DigestAsHex(userVO.getPassword().getBytes()));
        if (userEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用户或密码错误！"));
        }
        if (userEntity.getDeleted() == 1) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用户已注销！"));
        }
        String token = UUID.randomUUID().toString();
        userEntity.setToken(token);
        userEntity.setLastOperateTime(System.currentTimeMillis());
        sessionCache.put(token, userEntity);
        return userEntity;
    }

    @Override
    public void logout(String token) {
        clearInvalidSession(token);
    }

    @Override
    public int delete(Long userId) {
        UserEntity userEntity = userMapper.selectByPrimaryKey(userId);
        if (userEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用户不存在！"));
        }
        userEntity.setDeleted(1);
        userEntity.setUpdateTime(getTimestamp());
        return userMapper.updateByPrimaryKey(userEntity);
    }

    @Override
    public void clearInvalidSession(String token) {
        sessionCache.remove(token);
    }

    @Override
    public UserEntity getByToken(String token) {
        return sessionCache.get(token);
    }

    @Override
    public void setSessionCache(UserEntity userEntity) {
        sessionCache.put(userEntity.getToken(),userEntity);
    }

    @Override
    public PageInfo getByKeys(Integer pn, Integer ps,String searchText) {
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleted", 0);
        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andLike("name", key);
        criteria2.orLike("phone", key);
        example.and(criteria2);
        return new PageInfo(userMapper.selectByExample(example));
    }

    @Override
    public UserEntity getById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<UserEntity> getByIds(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.emptyList();
        }
        return userMapper.selectByIds(listToString(ids));
    }

    @Override
    public PageInfo getByNotIds(Integer pn, Integer ps,List<Long> ids) {

        if(ids.isEmpty()){
            return getByKeys(pn,ps,"");
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(UserEntity.class);
        Example.Criteria criteria =example.createCriteria();
        criteria.andNotIn("id",ids);
        return new PageInfo(userMapper.selectByExample(example));
    }
}

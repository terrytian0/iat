package com.terry.iat.dao.base;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;


/**
 * @author houyin.tian
 */
public interface BaseMapper<T> extends Mapper<T>,InsertListMapper<T>, IdsMapper<T>, ConditionMapper<T> {
    
}


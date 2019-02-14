package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.KeywordEntity;
import org.springframework.stereotype.Repository;

/**
 *
 * @author houyin.tian
 * @date 2017/11/1
 */
@Repository
@DataSource(value="iat")
public interface KeywordMapper extends BaseMapper<KeywordEntity> {
}

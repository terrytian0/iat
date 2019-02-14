package com.terry.iat.service.impl;

import com.terry.iat.service.core.JsonCompare;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.BodyEntity;
import com.terry.iat.dao.mapper.BodyMapper;
import com.terry.iat.service.BodyService;
import com.terry.iat.service.vo.BodyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author terry
 */
@Slf4j
@Service
public class BodyServiceImpl extends BaseServiceImpl implements BodyService {

    @Autowired
    private BodyMapper bodyMapper;

    @Override
    public BodyEntity getByApiId(Long apiId) {
        Example example = new Example(BodyEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("apiId", apiId);
        List<BodyEntity> bodyEntityList = bodyMapper.selectByExample(example);
        if (bodyEntityList.isEmpty()) {
            return null;
        }
        return bodyEntityList.get(0);
    }

    @Override
    public BodyEntity create(BodyVO bodyVO) {
        BodyEntity bodyEntity = new BodyEntity();
        if (bodyVO == null) {
            return bodyEntity;
        }
        bodyEntity.setApiId(bodyVO.getApiId());
        bodyEntity.setContent(bodyVO.getContent());
        bodyEntity.setDefaultValue(bodyVO.getDefaultValue());
        bodyEntity.setType(bodyVO.getType());
        bodyMapper.insert(bodyEntity);
        return bodyEntity;
    }

    @Override
    public int push(Long apiId, BodyVO bodyVO) {
        if (bodyVO == null) {
            return 0;
        }
        BodyEntity bodyEntity = getByApiId(apiId);
        if (bodyEntity == null) {
            create(bodyVO);
        } else if (!bodyEntity.getContent().equals(bodyVO.getContent()) || !bodyEntity.getType().equals(bodyVO.getType())) {
            bodyVO.setId(bodyEntity.getId());
            bodyVO.setDefaultValue(bodyEntity.getDefaultValue());
            update(bodyVO);
        } else {
            return 0;
        }
        return 1;
    }

    @Override
    public BodyEntity update(BodyVO bodyVO) {
        if(bodyVO==null){
            return null;
        }
        BodyEntity bodyEntity = bodyMapper.selectByPrimaryKey(bodyVO.getId());
        if (bodyEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Body不存在！"));
        }
        if (!JsonCompare.compareKey(bodyEntity.getContent(), bodyVO.getContent())) {
            bodyEntity.setContent(bodyVO.getContent());
            bodyEntity.setType(bodyVO.getType());
        }
        bodyEntity.setDefaultValue(bodyVO.getDefaultValue());
        bodyMapper.updateByPrimaryKey(bodyEntity);
        return bodyEntity;
    }

    @Override
    public int delete(Long id) {
        return bodyMapper.deleteByIds(String.valueOf(id));
    }
}

package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.AssertEntity;
import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.dao.mapper.AssertMapper;
import com.terry.iat.dao.mapper.ExtractorMapper;
import com.terry.iat.service.AssertService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.vo.AssertVO;
import com.terry.iat.service.vo.ExtractorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AssertServiceImpl extends BaseServiceImpl implements AssertService {

    @Autowired
    private AssertMapper assertMapper;


    @Override
    public AssertEntity create(AssertVO assertVO) {
        AssertEntity assertEntity = new AssertEntity();
        assertEntity.setKeywordApiId(assertVO.getKeywordApiId());
        assertEntity.setLocale(assertVO.getLocale().name());
        assertEntity.setMethod(assertVO.getMethod().name());
        assertEntity.setRule(assertVO.getRule());
        assertEntity.setValue(assertVO.getValue());
        assertEntity.setType(assertVO.getType().name());
        assertEntity.setDescription(assertVO.getDescription());
        assertMapper.insert(assertEntity);
        return assertEntity;
    }

    @Override
    public List<AssertEntity> getByKeywordApiId(Long keywordApiId) {
        List<AssertEntity> assertEntityList = assertMapper.getByKeywordApiId(keywordApiId);
        if(assertEntityList==null){
            return Collections.EMPTY_LIST;
        }
        return assertEntityList;
    }

    @Override
    public int delete(Long id) {
        return assertMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByKeywordApiId(Long keywordApiId) {
        return assertMapper.deleteByKeywordApiId(keywordApiId);
    }
}

package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.ParameterKeyEntity;
import com.terry.iat.dao.mapper.ParameterKeyMapper;
import com.terry.iat.service.ParameterKeyService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParameterKeyServiceImpl extends BaseServiceImpl implements ParameterKeyService {

    @Autowired
    private ParameterKeyMapper parameterKeyMapper;

    @Override
    public Map<Long,String> getByTestcaseId(Long testcaseId) {
        List<ParameterKeyEntity> parameterKeyEntityList = parameterKeyMapper.getByTestcaseId(testcaseId);
        Map<Long,String> parameterKey = new HashMap<>();
        for (ParameterKeyEntity parameterKeyEntity : parameterKeyEntityList) {
            parameterKey.put(parameterKeyEntity.getId(),parameterKeyEntity.getKeyName());
        }
        return parameterKey;
    }

    @Override
    public int create(Long testcaseId, List<String> keys) {
        if(keys.isEmpty()){
            return 0;
        }
        List<ParameterKeyEntity> parameterKeyEntityList = new ArrayList<>();
        for (String key : keys) {
            ParameterKeyEntity parameterKeyEntity = new ParameterKeyEntity();
            parameterKeyEntity.setKeyName(key);
            parameterKeyEntity.setTestcaseId(testcaseId);
            parameterKeyEntityList.add(parameterKeyEntity);
        }
        return parameterKeyMapper.insertList(parameterKeyEntityList);
    }

    @Override
    public int delete(List<Long> ids) {
        if(ids.isEmpty()){
            return 0;
        }
        return parameterKeyMapper.deleteByIds(listToString(ids));
    }
}

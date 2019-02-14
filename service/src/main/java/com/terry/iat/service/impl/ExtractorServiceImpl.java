package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.dao.mapper.ExtractorMapper;
import com.terry.iat.service.ExtractorService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.vo.ExtractorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExtractorServiceImpl extends BaseServiceImpl implements ExtractorService {

    @Autowired
    private ExtractorMapper extractorMapper;

    @Override
    public ExtractorEntity create(ExtractorVO extractorVO) {
        ExtractorEntity extractorEntity = new ExtractorEntity();
        copy(extractorVO, extractorEntity);
        extractorMapper.insert(extractorEntity);
        return extractorEntity;
    }

    @Override
    public ExtractorEntity update(ExtractorVO extractorVO) {
        ExtractorEntity extractorEntity = extractorMapper.selectByPrimaryKey(extractorVO.getId());
        if (extractorEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("提取器不存在！"));
        }
        copy(extractorVO, extractorEntity);
        extractorMapper.updateByPrimaryKey(extractorEntity);
        return extractorEntity;
    }

    private void copy(ExtractorVO extractorVO, ExtractorEntity extractorEntity) {
        extractorEntity.setKeywordApiId(extractorVO.getKeywordApiId());
        extractorEntity.setType(extractorVO.getType().name());
        extractorEntity.setRule(extractorVO.getRule());
        extractorEntity.setName(extractorVO.getName());
        extractorEntity.setDescription(extractorVO.getDescription());
    }

    @Override
    public List<ExtractorEntity> getByKeywordApiId(Long keywordApiId) {
        List<ExtractorEntity> extractorEntityList = extractorMapper.getByKeywordApiId(keywordApiId);
        if (extractorEntityList == null) {
            return Collections.EMPTY_LIST;
        }
        return extractorEntityList;
    }

    @Override
    public List<ExtractorEntity> getExtractors(Long keywordApiId, List<ExtractorEntity> extractors) {
        List<ExtractorEntity> extractorEntityList = getByKeywordApiId(keywordApiId);
        if(extractors==null||extractors.isEmpty()){
            return extractorEntityList;
        }
        if(extractorEntityList==null){
            extractorEntityList = new ArrayList<>();
        }
        Map<String,ExtractorEntity> extractorEntityMap = new HashMap<>();
        extractorEntityList.forEach(extractorEntity->{
            extractorEntityMap.put(extractorEntity.getName(),extractorEntity);
        });
        extractors.forEach(extractorEntity -> {
            extractorEntityMap.put(extractorEntity.getName(),extractorEntity);
        });
        List<ExtractorEntity> result = new ArrayList<>();
        extractorEntityMap.forEach((k,v)->{
            result.add(v);
        });
        return result;
    }

    @Override
    public int delete(Long id) {
        return extractorMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteByKeywordApiId(Long keywordApiId) {
        return extractorMapper.deleteByKeywordApiId(keywordApiId);
    }
}

package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.KeywordMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.core.HttpResult;
import com.terry.iat.service.core.KeywordResult;
import com.terry.iat.service.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
 * @author terry
 * @Description Keyword服务
 * @Date 2019/2/14 10:53
 * @Version 1.0
 **/
@Slf4j
@Service
public class KeywordServiceImpl extends BaseServiceImpl implements KeywordService {

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private KeywordApiService keywordApiService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ExtractorService extractorService;

    @Autowired
    private AssertService assertService;

    @Autowired
    private EnvService envService;

    @Autowired
    private TestcaseKeywordService testcaseKeywordService;

    @Override
    public KeywordEntity getById(Long id) {
        KeywordEntity keywordEntity = keywordMapper.selectByPrimaryKey(id);
        if (keywordEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("keyword不存在！"));
        }
        List<KeywordApiEntity> apiEntityList = keywordApiService.getByKeywordId(keywordEntity.getId());
        keywordEntity.setApis(apiEntityList);
        return keywordEntity;

    }

    @Override
    public PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId) {
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(KeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        criteria.andLike("name", key);
        example.orderBy("id").desc();
        return new PageInfo(keywordMapper.selectByExample(example));
    }

    @Override
    public List<KeywordEntity> getByIds(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(KeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        List<KeywordEntity> keywordEntityList = keywordMapper.selectByExample(example);
        if (keywordEntityList.isEmpty()) {
            return Collections.emptyList();
        } else {
            keywordEntityList.forEach(
                    k -> {
                        List<KeywordApiEntity> apiEntityList = keywordApiService.getByKeywordId(k.getId());
                        k.setApis(apiEntityList);
                    }
            );
            return keywordEntityList;
        }
    }


    @Override
    public List<ParameterVO> getParameters(Long keywordId, List<ExtractorEntity> extractors) {
        List<KeywordApiEntity> keywordApiEntityList = keywordApiService.getByKeywordId(keywordId);
        List<ParameterVO> parameterVOList = new ArrayList<>();
        Map<String, ParameterVO> parameterVOMap = new HashMap<>();
        Map<String, ExtractorEntity> extractorEntityMap = new HashMap<>();
        List<ExtractorEntity> extractorEntityList = new ArrayList<>();
        if (extractors != null && !extractors.isEmpty()) {
            extractorEntityList.addAll(extractors);
        }
        for (KeywordApiEntity keywordApiEntity : keywordApiEntityList) {
            List<ParameterVO> parameterVOS = apiService.getParameters(keywordApiEntity.getApiId());
            List<ParameterVO> assParameter  = assertService.getParameters(keywordApiEntity.getId());
            parameterVOS.addAll(assParameter);
            for (ExtractorEntity extractorEntity : extractorEntityList) {
                extractorEntityMap.put(extractorEntity.getName(), extractorEntity);
            }
            for (ParameterVO parameterVO : parameterVOS) {
                if (extractorEntityMap.get(parameterVO.getName()) != null) {
                    parameterVO.setRule(extractorEntityMap.get(parameterVO.getName()).getRule());
                }
                String key = parameterVO.getName();
                if (parameterVO.getRule() != null) {
                    key = key + "-" + parameterVO.getRule();
                }
                if (parameterVOMap.get(key) == null) {
                    parameterVOList.add(parameterVO);
                    parameterVOMap.put(key, parameterVO);
                }
            }
        }
        return parameterVOList;
    }

    @Override
    public List<ExtractorEntity> getExtractor(Long keywordId, List<ExtractorEntity> extractors) {
        List<KeywordApiEntity> keywordApiEntityList = keywordApiService.getByKeywordId(keywordId);
        List<ExtractorEntity> extractorEntityList = new ArrayList<>();
        if (extractors != null && !extractors.isEmpty()) {
            extractorEntityList.addAll(extractors);
        }
        for (KeywordApiEntity keywordApiEntity : keywordApiEntityList) {
            extractorEntityList = extractorService.getExtractors(keywordApiEntity.getId(), extractorEntityList);
        }
        return extractorEntityList;
    }


    @Override
    public KeywordResult debug(KeywordDebugVO keywordDebugVO) {
        KeywordEntity keywordEntity = getById(keywordDebugVO.getKeywordId());
        Map<String, String> parameters = convertParameters(keywordDebugVO.getParameters());
        EnvEntity envEntity = envService.getById(keywordDebugVO.getEnvId());
        return debug(keywordEntity, parameters, envEntity);
    }

    @Override
    public KeywordResult debug(KeywordEntity keywordEntity, Map<String, String> parameters, EnvEntity envEntity) {
        List<HttpResult> httpResults = new ArrayList<>();
        KeywordResult keywordResult = new KeywordResult();
        keywordResult.setKeywordId(keywordEntity.getId());
        for (KeywordApiEntity api : keywordEntity.getApis()) {
            List<ExtractorEntity> extractorEntityList = extractorService.getByKeywordApiId(api.getId());
            List<AssertEntity> assertEntityList = assertService.getByKeywordApiId(api.getId());
            ApiEntity apiEntity = api.getDetail();
            HttpResult result = apiService.debug(api.getDetail(), envEntity, parameters, extractorEntityList, assertEntityList);
            result.setKeywordApiId(api.getId());
            result.setApiId(apiEntity.getId());
            httpResults.add(result);
            if (result.isSuccessful() == false) {
                keywordResult.setStatus(false);
                break;
            }
        }
        keywordResult.setHttpResults(httpResults);

        return keywordResult;
    }

    @Override
    public List<KeywordApiEntity> addApi(AddApiVO addApiVO) {
        KeywordEntity keywordEntity = getById(addApiVO.getKeywordId());
        if (keywordEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Keyword不存在！"));
        }
        List<KeywordApiEntity> keywordApiEntityList = keywordApiService.create(addApiVO);
        return keywordApiEntityList;
    }

    @Override
    public Integer removeApi(RemoveApiVO removeApiVO) {
        KeywordEntity keywordEntity = getById(removeApiVO.getKeywordId());
        if (keywordEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Keyword不存在！"));
        }
        int rows = keywordApiService.delete(removeApiVO.getIds());
        return rows;
    }


    @Override
    public KeywordEntity create(KeywordVO keywordVO) {
        KeywordEntity keywordEntity = new KeywordEntity();
        keywordEntity.setServiceId(keywordVO.getServiceId());
        keywordEntity.setName(keywordVO.getName());
        keywordEntity.setDescription(keywordVO.getDescription());
        keywordEntity.setCreateUser(getCurrentUser().getName());
        keywordEntity.setCreateTime(getTimestamp());
        keywordMapper.insert(keywordEntity);
        return keywordEntity;
    }

    @Override
    public KeywordEntity update(KeywordVO keywordVO) {
        KeywordEntity keywordEntity = keywordMapper.selectByPrimaryKey(keywordVO.getId());
        if (keywordEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("keywords not exist!"));
        }
        keywordEntity.setName(keywordVO.getName());
        keywordEntity.setDescription(keywordVO.getDescription());
        keywordEntity.setUpdateUser(getCurrentUser().getName());
        keywordEntity.setUpdateTime(getTimestamp());
        keywordMapper.updateByPrimaryKey(keywordEntity);
        return keywordEntity;
    }

    @Override
    public int delete(List<Long> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        for (Long id : ids) {
            List<TestcaseKeywordEntity> testcaseKeywordEntityList = testcaseKeywordService.getByTestcaseId(id);
            if(testcaseKeywordEntityList!=null && !testcaseKeywordEntityList.isEmpty()){
                throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("关键字被用例引用，禁止删除！"));
            }
        }
        for (Long id : ids) {
            keywordApiService.delete(id);
        }
        return keywordMapper.deleteByIds(listToString(ids));
    }
}

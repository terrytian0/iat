package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.TestcaseMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.core.HttpResult;
import com.terry.iat.service.core.KeywordResult;
import com.terry.iat.service.core.TestcaseResult;
import com.terry.iat.service.vo.ParameterVO;
import com.terry.iat.service.vo.TestcaseDebugVO;
import com.terry.iat.service.vo.TestcaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
 * @author terry
 */
@Slf4j
@Service
public class TestcaseServiceImpl extends BaseServiceImpl implements TestcaseService {

    @Autowired
    private TestcaseMapper testcaseMapper;

    @Autowired
    private TestcaseKeywordApiService testcaseKeywordsApiService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private EnvService envService;

    @Autowired
    private ParameterKeyService parameterKeyService;

    @Autowired
    private ParameterValueService parameterValueService;

    @Override
    public TestcaseEntity getById(Long id) {
        TestcaseEntity testcaseEntity = testcaseMapper.selectByPrimaryKey(id);
        if (testcaseEntity == null) {
            return new TestcaseEntity();
        }
        List<TestcaseKeywordApiEntity> testcaseKeywordApiEntityList = testcaseKeywordsApiService.getByTestcaseId(testcaseEntity.getId());
        testcaseEntity.setKeywords(testcaseKeywordApiEntityList);
        return testcaseEntity;
    }

    @Override
    public PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId) {
       return getByServiceIdAndNotInIds(pn,ps,searchText,serviceId,null);
    }

    @Override
    public PageInfo getByServiceIdAndNotInIds(Integer pn, Integer ps, String searchText, Long serviceId, List<Long> ids) {
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(TestcaseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        criteria.andLike("name", key);
        if(ids!=null&&!ids.isEmpty()){
            criteria.andNotIn("id",ids);
        }
        return new PageInfo(testcaseMapper.selectByExample(example));
    }

    @Override
    public TestcaseResult debug(TestcaseDebugVO testcaseDebugVO) {
        TestcaseEntity testcaseEntity = getById(testcaseDebugVO.getTestcaseId());
        EnvEntity envEntity = envService.getById(testcaseDebugVO.getEnvId());
        List<HttpResult> httpResultList = new ArrayList<>();
        TestcaseResult testcaseResult = new TestcaseResult();
        testcaseResult.setTestcaseId(testcaseDebugVO.getTestcaseId());
        Map<String, String> parameter = testcaseDebugVO.getParameters();
        List<KeywordResult> keywordResults = new ArrayList<>();
        for (TestcaseKeywordApiEntity keyword : testcaseEntity.getKeywords()) {
            KeywordEntity keywordEntity = keyword.getDetail();
            KeywordResult keywordResult = keywordService.debug(keywordEntity, parameter, envEntity);
            keywordResult.setTestcaseKeywordId(keyword.getId());
            if (keywordResult.isStatus() == false) {
                testcaseResult.setStatus(false);
                keywordResults.add(keywordResult);
                break;
            } else {
                keywordResults.add(keywordResult);
            }
        }
        testcaseResult.setKeywordResults(keywordResults);
        return testcaseResult;
    }

    @Override
    public Map<String, String> getParameters(Long testcaseId) {
        List<TestcaseKeywordApiEntity> testcaseKeywordApiEntityList = testcaseKeywordsApiService.getByTestcaseId(testcaseId);
        List<Map<String, String>> parameters = new ArrayList<>();
        Map<String, String> parameterTitles = new HashMap<>();
        //TODO 性能问题，需要优化。优化方案：获取参数不再查询数据库
        List<ExtractorEntity> extractors = new ArrayList<>();
        for (TestcaseKeywordApiEntity testcaseKeywordApiEntity : testcaseKeywordApiEntityList) {
            extractors = keywordService.getExtractor(testcaseKeywordApiEntity.getKeywordId(), extractors);
            List<ParameterVO> parameterVOList = keywordService.getParameters(testcaseKeywordApiEntity.getKeywordId(), extractors);
            for (ParameterVO parameterVO : parameterVOList) {
                if (!parameterTitles.containsKey(parameterVO.getName())) {
                    if (parameterVO.getRule() == null) {
                        parameterTitles.put(parameterVO.getName(), "");
                    } else {
                        parameterTitles.put(parameterVO.getName(), parameterVO.getRule());
                    }
                }
            }
        }
        Map<Long, String> parametersKeys = parameterKeyService.getByTestcaseId(testcaseId);
        List<Long> deleteKeys = new ArrayList<>();
        for (Long key : parametersKeys.keySet()) {
            if (!parameterTitles.containsKey(parametersKeys.get(key))) {
                deleteKeys.add(key);
            }
        }
        List<String> insertKeys = new ArrayList<>();
        Collection<String> values = parametersKeys.values();
        for (String key : parameterTitles.keySet()) {
            if (!values.contains(key)) {
                insertKeys.add(key);
            }
        }
        parameterKeyService.create(testcaseId, insertKeys);
        parameterKeyService.delete(deleteKeys);
        parameterValueService.deleteByKeyIds(deleteKeys);
        return parameterTitles;
    }

    @Override
    public List<TestcaseEntity> getByIds(List<Long> testcaseIds) {
        return testcaseMapper.selectByIds(listToString(testcaseIds));
    }

    @Override
    public TestcaseEntity create(TestcaseVO testcaseVO) {
        TestcaseEntity testcaseEntity = new TestcaseEntity();
        testcaseEntity.setServiceId(testcaseVO.getServiceId());
        testcaseEntity.setName(testcaseVO.getName());
        testcaseEntity.setDescription(testcaseVO.getDescription());
        testcaseEntity.setCreateUser(getCurrentUser().getName());
        testcaseEntity.setCreateTime(getTimestamp());
        testcaseMapper.insert(testcaseEntity);
        return testcaseEntity;
    }

    @Override
    public TestcaseEntity update(TestcaseVO testcaseVO) {
        TestcaseEntity testcaseEntity = testcaseMapper.selectByPrimaryKey(testcaseVO.getId());
        if (testcaseEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("testcase not exist!"));
        }
        testcaseEntity.setName(testcaseVO.getName());
        testcaseEntity.setDescription(testcaseVO.getDescription());
        testcaseEntity.setUpdateUser(getCurrentUser().getName());
        testcaseEntity.setUpdateTime(getTimestamp());
        testcaseMapper.updateByPrimaryKey(testcaseEntity);
        return testcaseEntity;
    }

    @Override
    public int delete(List<Long> ids) {
        return testcaseMapper.deleteByIds(listToString(ids));
    }
}

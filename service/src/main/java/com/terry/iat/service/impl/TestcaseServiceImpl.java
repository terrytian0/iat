package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.TestcaseMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.common.utils.DateUtils;
import com.terry.iat.service.core.HttpResult;
import com.terry.iat.service.core.KeywordResult;
import com.terry.iat.service.core.TestcaseResult;
import com.terry.iat.service.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
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
    private TestcaseKeywordService testcaseKeywordService;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private EnvService envService;

    @Autowired
    private ParameterKeyService parameterKeyService;

    @Autowired
    private ParameterValueService parameterValueService;

    @Autowired
    private TestplanTestcaseService testplanTestcaseService;

    @Override
    public TestcaseEntity getById(Long id) {
        TestcaseEntity testcaseEntity = testcaseMapper.selectByPrimaryKey(id);
        if (testcaseEntity == null) {
            return new TestcaseEntity();
        }
        List<TestcaseKeywordEntity> testcaseKeywordEntityList = testcaseKeywordService.getByTestcaseId(testcaseEntity.getId());
        testcaseEntity.setKeywords(testcaseKeywordEntityList);
        return testcaseEntity;
    }

    @Override
    public PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId) {
        return getByServiceIdAndNotInIds(pn, ps, searchText, serviceId, null);
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
        if (ids != null && !ids.isEmpty()) {
            criteria.andNotIn("id", ids);
        }
        example.orderBy("id").desc();
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
        for (TestcaseKeywordEntity keyword : testcaseEntity.getKeywords()) {
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
        List<TestcaseKeywordEntity> testcaseKeywordEntityList = testcaseKeywordService.getByTestcaseId(testcaseId);
        List<Map<String, String>> parameters = new ArrayList<>();
        Map<String, String> parameterTitles = new HashMap<>();
        //TODO 性能问题，需要优化。优化方案：获取参数不再查询数据库
        List<ExtractorEntity> extractors = new ArrayList<>();
        for (TestcaseKeywordEntity testcaseKeywordEntity : testcaseKeywordEntityList) {
            extractors = keywordService.getExtractor(testcaseKeywordEntity.getKeywordId(), extractors);
            List<ParameterVO> parameterVOList = keywordService.getParameters(testcaseKeywordEntity.getKeywordId(), extractors);
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
        if (testcaseIds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return testcaseMapper.selectByIds(listToString(testcaseIds));
    }

    @Override
    public Integer getCount() {
        List<TestcaseEntity> testcaseEntityList = testcaseMapper.selectAll();
        if (testcaseEntityList == null) {
            return 0;
        }
        Integer count = 0;
        for (TestcaseEntity testcaseEntity : testcaseEntityList) {
            Integer pCount = parameterValueService.getCountByTestcaseId(testcaseEntity.getId());
            if (pCount == 0) {
                count++;
            } else {
                count = count + pCount;
            }
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getWeekChart() {
        List<Map<String, Object>> chart = new ArrayList<>();
        try {
            Date currentDate = new Date();
            Date startDate = DateUtils.getDate("2019-01-01", "yyyy-MM-dd");
            startDate = DateUtils.getFirstDayOfWeek(startDate);
            Date lastDate;
            int total = 0;
            while (startDate.before(currentDate)) {
                startDate = DateUtils.addDay(startDate, 7);
                if (startDate.after(currentDate)) {
                    startDate = currentDate;
                }
                lastDate = DateUtils.addDay(startDate, -7);
                String date = DateUtils.getDate(startDate, "yyyy-MM-dd");
                total = total + getWeekCount(lastDate, startDate);
                Map<String, Object> d = new HashMap<>();
                d.put("time", date);
                d.put("count", total);
                chart.add(d);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chart;
    }

    private Integer getWeekCount(Date bDate, Date eDate) {
        List<TestcaseEntity> testcaseEntityList = get(bDate, eDate);
        if (testcaseEntityList == null) {
            return 0;
        }
        Integer count = 0;
        for (TestcaseEntity testcaseEntity : testcaseEntityList) {
            int pCount = parameterValueService.getCountByTestcaseId(testcaseEntity.getId());
            if (pCount == 0) {
                count++;
            } else {
                count = count + pCount;
            }
        }
        return count;
    }

    private List<TestcaseEntity> get(Date bDate, Date eDate) {
        Example example = new Example(TaskEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andGreaterThanOrEqualTo("createTime", bDate);
        criteria.andLessThan("createTime", eDate);
        return testcaseMapper.selectByExample(example);
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
        if (ids.isEmpty()) {
            return 0;
        }
        for (Long id : ids) {
            List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseService.getByTestcaseId(id);
            if (testplanTestcaseEntityList != null && !testplanTestcaseEntityList.isEmpty()) {
                throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用例被测试计划引用，禁止删除！"));
            }
        }
        for (Long id : ids) {
            testcaseKeywordService.deleteByTestcaseId(id);
        }
        return testcaseMapper.deleteByIds(listToString(ids));
    }

    @Override
    public List<TestcaseKeywordEntity> addKeyword(AddKeywordVO addKeywordVO) {
        TestcaseEntity testcaseEntity = getById(addKeywordVO.getTestcaseId());
        if (testcaseEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Testcase不存在！"));
        }
        List<TestcaseKeywordEntity> testcaseKeywordEntityList = testcaseKeywordService.create(addKeywordVO);
        return testcaseKeywordEntityList;
    }

    @Override
    public Integer removeKeyword(RemoveKeywordVO removeKeywordVO) {
        TestcaseEntity testcaseEntity = getById(removeKeywordVO.getTestcaseId());
        if (testcaseEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Testcase不存在！"));
        }
        int rows = testcaseKeywordService.delete(removeKeywordVO.getIds());
        return rows;
    }


}

package com.terry.iat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.TestplanMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.common.utils.DateUtils;
import com.terry.iat.service.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.ParseException;
import java.util.*;

/**
 * @author terry
 * @version 1.0
 * @class name TestplanServiceImpl
 * @description 测试计划处理类
 * @date 2019/2/18 10:40
 **/
@Service
public class TestplanServiceImpl extends BaseServiceImpl implements TestplanService {

    @Autowired
    private TestplanMapper testplanMapper;

    @Autowired
    private TestplanTestcaseService testplanTestcaseService;

    @Autowired
    private TestcaseService testcaseService;

    @Autowired
    private TestcaseKeywordService testcaseKeywordService;

    @Autowired
    private KeywordApiService keywordApiService;

    @Autowired
    private ApiService apiService;

    @Autowired
    private EnvService envService;

    @Autowired
    private ServiceService serviceService;

    @Override
    public TestplanEntity create(TestplanVO testplanVO) {
        TestplanEntity testplanEntity = new TestplanEntity();
        testplanEntity.setName(testplanVO.getName());
        if (testplanVO.getStrategy() != null) {
            testplanEntity.setStrategy(testplanVO.getStrategy());
        }
        testplanEntity.setServiceId(testplanVO.getServiceId());
        testplanEntity.setCreateTime(getTimestamp());
        testplanEntity.setUpdateTime(getTimestamp());
        testplanEntity.setCreateUser(getCurrentUser().getName());
        testplanEntity.setUpdateUser(getCurrentUser().getName());
        testplanMapper.insert(testplanEntity);
        return testplanEntity;
    }

    @Override
    public Integer delete(Long testplanId) {
        return testplanMapper.deleteByPrimaryKey(testplanId);
    }

    @Override
    public TestplanEntity update(TestplanVO testplanVO) {
        TestplanEntity testplanEntity = getById(testplanVO.getId());
        testplanEntity.setName(testplanVO.getName());
        testplanEntity.setStrategy(testplanVO.getStrategy());
        testplanEntity.setUpdateTime(getTimestamp());
        testplanEntity.setUpdateUser(getCurrentUser().getName());
        testplanMapper.updateByPrimaryKey(testplanEntity);
        return testplanEntity;
    }

    @Override
    public PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId) {
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(TestcaseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        criteria.andLike("name", key);
        example.orderBy("id").desc();
        return new PageInfo(testplanMapper.selectByExample(example));
    }

    @Override
    public TestplanEntity getById(Long testplanId) {
        TestplanEntity testplanEntity = testplanMapper.selectByPrimaryKey(testplanId);
        if (testplanEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试计划不存在！"));
        }
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseService.getByTestplanId(testplanId);
        testplanEntity.setTestcases(testplanTestcaseEntityList);
        return testplanEntity;
    }

    @Override
    public List<TestplanTestcaseEntity> addTestcase(AddTestcaseVO addTestcaseVO) {
        TestplanEntity testplanEntity = getById(addTestcaseVO.getTestplanId());
        if (testplanEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Testplan不存在！"));
        }
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseService.create(addTestcaseVO);
        return testplanTestcaseEntityList;
    }

    @Override
    public Integer removeTestcase(RemoveTestcaseVO removeTestcaseVO) {
        TestplanEntity testplanEntity = getById(removeTestcaseVO.getTestplanId());
        if (testplanEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Testplan不存在！"));
        }
        int rows = testplanTestcaseService.delete(removeTestcaseVO.getTestcaseIds());
        return rows;
    }

    @Override
    public List<TestplanEnvVO> getEnvById(Long testplanId) {
        TestplanEntity testplanEntity = getById(testplanId);
        Map<Long, EnvEntity> oldEnv = new HashMap<>();
        if (testplanEntity.getEnv() != null && !testplanEntity.getEnv().isEmpty()) {
            List<TestplanEnvVO> old = JSON.parseObject(testplanEntity.getEnv(), new TypeReference<List<TestplanEnvVO>>() {
            });
            for (TestplanEnvVO testplanEnvVO : old) {
                oldEnv.put(testplanEnvVO.getServiceId(), testplanEnvVO.getEnv());
            }
        }
        Set<Long> serviceIds = getServiceIdsById(testplanId);
        List<ServiceEntity> serviceEntityList = serviceService.getByIds(serviceIds);
        Map<Long, List<EnvEntity>> envEntityMap = envService.getByServiceIds(serviceIds);
        List<TestplanEnvVO> testplanEnvVOS = new ArrayList<>();
        serviceEntityList.forEach(serviceEntity -> {
            TestplanEnvVO testplanEnvVO = new TestplanEnvVO();
            testplanEnvVO.setServiceId(serviceEntity.getId());
            testplanEnvVO.setServiceName(serviceEntity.getName());
            testplanEnvVO.setEnvs(envEntityMap.get(serviceEntity.getId()));
            if (oldEnv.get(serviceEntity.getId()) != null) {
                testplanEnvVO.setEnv(oldEnv.get(serviceEntity.getId()));
            }
            testplanEnvVOS.add(testplanEnvVO);
        });
        return testplanEnvVOS;
    }

    @Override
    public void createEnv(List<TestplanCreateEnvVO> envs) {
        if (envs == null || envs.isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试计划创建环境失败！环境不能为空！"));
        }
        TestplanEntity testplanEntity = getById(envs.get(0).getTestplanId());
        Set<Long> serviceIds = new HashSet<>();
        List<Long> envList = new ArrayList<>();
        envs.forEach(testplanCreateEnvVO -> {
            serviceIds.add(testplanCreateEnvVO.getServiceId());
            envList.add(testplanCreateEnvVO.getSelectId());
        });

        List<ServiceEntity> serviceEntityList = serviceService.getByIds(serviceIds);
        List<EnvEntity> envEntityList = envService.getByIds(envList);
        Map<Long, EnvEntity> envEntityMap = new HashMap<>();
        envEntityList.forEach(envEntity -> {
            envEntityMap.put(envEntity.getServiceId(), envEntity);
        });
        List<TestplanEnvVO> testplanEnvVOList = new ArrayList<>();
        serviceEntityList.forEach(serviceEntity -> {
            TestplanEnvVO testplanEnvVO = new TestplanEnvVO();
            testplanEnvVO.setServiceId(serviceEntity.getId());
            testplanEnvVO.setServiceName(serviceEntity.getName());
            testplanEnvVO.setEnv(envEntityMap.get(serviceEntity.getId()));
            testplanEnvVOList.add(testplanEnvVO);
        });
        testplanEntity.setEnv(JSONArray.toJSONString(testplanEnvVOList));
        int rows = testplanMapper.updateByPrimaryKey(testplanEntity);
        if (rows != 1) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试计划创建环境失败！rows=" + rows));
        }
    }

    @Override
    public Integer getCount() {
        return testplanMapper.selectCount(new TestplanEntity());
    }

    @Override
    public List<Map<String, Object>> getWeekChart() {
        List<Map<String, Object>> chart = new ArrayList<>();
        try {
            Date currentDate = new Date();
            Date startDate = DateUtils.getDate("2019-01-01", "yyyy-MM-dd");
            startDate = DateUtils.getFirstDayOfWeek(startDate);
            while (startDate.before(currentDate)) {
                startDate = DateUtils.addDay(startDate, 7);
                if (startDate.after(currentDate)) {
                    startDate = currentDate;
                }
                String date = DateUtils.getDate(startDate, "yyyy-MM-dd");
                Integer count = getCount(startDate);
                Map<String, Object> d = new HashMap<>();
                d.put("time", date);
                d.put("count", count);
                chart.add(d);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chart;
    }

    private Integer getCount(Date date) {
        Example example = new Example(TestplanEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLessThan("createTime", date);
        return testplanMapper.selectCountByExample(example);
    }


    private Set<Long> getServiceIdsById(Long testplanId) {
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseService.getByTestplanId(testplanId);
        List<Long> testcaseIds = new ArrayList<>();
        testplanTestcaseEntityList.forEach(testplanTestcaseEntity -> {
            testcaseIds.add(testplanTestcaseEntity.getTestcaseId());
        });
        List<TestcaseKeywordEntity> testcaseKeywordEntityList = testcaseKeywordService.getByTestcaseIds(testcaseIds);
        List<Long> keywordIds = new ArrayList<>();
        testcaseKeywordEntityList.forEach(testcaseKeywordEntity -> {
            keywordIds.add(testcaseKeywordEntity.getKeywordId());
        });
        List<KeywordApiEntity> keywordApiEntityList = keywordApiService.getByKeywordIds(keywordIds);
        List<Long> apiIds = new ArrayList<>();
        keywordApiEntityList.forEach(keywordApiEntity -> {
            apiIds.add(keywordApiEntity.getApiId());
        });
        List<ApiEntity> apiEntityList = apiService.getByIds(apiIds);
        Set<Long> serviceIds = new HashSet<>();
        apiEntityList.forEach(apiEntity -> {
            serviceIds.add(apiEntity.getServiceId());
        });
        return serviceIds;
    }
}

package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestcaseEntity;
import com.terry.iat.dao.entity.TestplanEntity;
import com.terry.iat.dao.entity.TestplanTestcaseEntity;
import com.terry.iat.dao.mapper.TestplanMapper;
import com.terry.iat.service.TestplanService;
import com.terry.iat.service.TestplanTestcaseService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.vo.TestplanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

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

    @Override
    public TestplanEntity create(TestplanVO testplanVO) {
        TestplanEntity testplanEntity = new TestplanEntity();
        testplanEntity.setName(testplanVO.getName());
        if(testplanVO.getStrategy()!=null){
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
        return new PageInfo(testplanMapper.selectByExample(example));
    }

    @Override
    public TestplanEntity getById(Long testplanId) {
        TestplanEntity testplanEntity = testplanMapper.selectByPrimaryKey(testplanId);
        if(testplanEntity==null){
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试计划不存在！"));
        }
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseService.getByTestplanId(testplanId);
        testplanEntity.setTestcases(testplanTestcaseEntityList);
        return testplanEntity;
    }
}

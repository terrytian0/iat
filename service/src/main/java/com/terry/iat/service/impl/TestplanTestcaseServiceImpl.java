package com.terry.iat.service.impl;

import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TestcaseEntity;
import com.terry.iat.dao.entity.TestplanEntity;
import com.terry.iat.dao.entity.TestplanTestcaseEntity;
import com.terry.iat.dao.mapper.TestplanTestcaseMapper;
import com.terry.iat.service.TestcaseService;
import com.terry.iat.service.TestplanService;
import com.terry.iat.service.TestplanTestcaseService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.Index;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.vo.AddTestcaseVO;
import com.terry.iat.service.vo.TestplanIndexVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author terry
 * @version 1.0
 * @class name TestplanTestcaseServiceImpl
 * @description TODO
 * @date 2019/2/18 12:40
 **/
@Service
public class TestplanTestcaseServiceImpl extends BaseServiceImpl implements TestplanTestcaseService {
    @Autowired
    private TestplanTestcaseMapper testplanTestcaseMapper;

    @Autowired
    private TestplanService testplanService;

    @Autowired
    private TestcaseService testcaseService;

    @Override
    public synchronized List<TestplanTestcaseEntity> create(AddTestcaseVO addTestcaseVO) {
        TestplanEntity testplanEntity = testplanService.getById(addTestcaseVO.getTestplanId());
        if (testplanEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试计划不存在！"));
        }
        Integer maxIdx = testplanTestcaseMapper.getMaxIdx(addTestcaseVO.getTestplanId());
        if (maxIdx == null) {
            maxIdx = 0;
        }
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = new ArrayList<>();
        for (Long id : addTestcaseVO.getTestcaseIds()) {
            maxIdx = maxIdx + 1;
            TestplanTestcaseEntity testplanTestcaseEntity = new TestplanTestcaseEntity();
            testplanTestcaseEntity.setTestcaseId(id);
            testplanTestcaseEntity.setTestplanId(addTestcaseVO.getTestplanId());
            testplanTestcaseEntity.setIdx(maxIdx);
            testplanTestcaseEntityList.add(testplanTestcaseEntity);
        }
        testplanTestcaseMapper.insertList(testplanTestcaseEntityList);
        return testplanTestcaseEntityList;
    }

    @Override
    public synchronized int delete(List<Long> testcaseIds) {
        return testplanTestcaseMapper.deleteByIds(listToString(testcaseIds));
    }

    @Override
    public List<TestplanTestcaseEntity> getByTestplanId(Long testplanId) {
        Example example = new Example(TestplanTestcaseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("testplanId", testplanId);
        example.orderBy("idx").asc();
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        if (!testplanTestcaseEntityList.isEmpty()) {
            testplanTestcaseEntityList.forEach(t -> ids.add(t.getTestcaseId()));
        } else {
            return Collections.emptyList();
        }
        List<TestcaseEntity> testcaseEntityList = testcaseService.getByIds(ids);
        Map<Long, TestcaseEntity> testcaseEntityHashMap = new HashMap<>();
        testcaseEntityList.forEach(k -> testcaseEntityHashMap.put(k.getId(), k));
        testplanTestcaseEntityList.forEach(tk -> tk.setDetail(testcaseEntityHashMap.get(tk.getTestcaseId())));
        return testplanTestcaseEntityList;
    }

    @Override
    public List<TestplanTestcaseEntity> getByTestcaseId(Long testcaseId) {
        Example example = new Example(TestplanTestcaseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("testcaseId", testcaseId);
        return  testplanTestcaseMapper.selectByExample(example);
    }

    @Override
    public void updateIdx(TestplanIndexVO testplanIndexVO) {
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = getByTestplanId(testplanIndexVO.getTestplanId());
        TestplanTestcaseEntity testplanTestcaseEntity = testplanTestcaseMapper.selectByPrimaryKey(testplanIndexVO.getTestplanTestcaseId());
        if(testplanIndexVO.getIndex()== Index.UP){
            testplanTestcaseUp(testplanTestcaseEntityList,testplanTestcaseEntity);
        }else if(testplanIndexVO.getIndex()== Index.DOWN){
            testplanTestcaseDown(testplanTestcaseEntityList,testplanTestcaseEntity);
        }else if(testplanIndexVO.getIndex()== Index.FIRST){
            testplanTestcaseFirst(testplanTestcaseEntity);
        }else if(testplanIndexVO.getIndex()== Index.LAST){
            testplanTestcaseLast(testplanTestcaseEntityList.size(),testplanTestcaseEntity);
        }
    }

    @Override
    public PageInfo getNotAddedTestcases(Integer pageNumber, Integer pageSize, String searchText, Long serviceId,Long testplanId) {
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = getByTestplanId(testplanId);
        List<Long> testcaseIds = new ArrayList<>();
        if(testplanTestcaseEntityList!=null) {
            for (TestplanTestcaseEntity testplanTestcaseEntity : testplanTestcaseEntityList) {
                testcaseIds.add(testplanTestcaseEntity.getTestcaseId());
            }
        }
        return testcaseService.getByServiceIdAndNotInIds(pageNumber,pageSize,searchText,serviceId,testcaseIds);
    }


    private void testplanTestcaseLast(Integer count, TestplanTestcaseEntity testplanTestcaseEntity) {
        if(testplanTestcaseEntity.getIdx()==count){
            return;
        }
        testplanTestcaseMapper.updateLast(testplanTestcaseEntity.getTestplanId(),testplanTestcaseEntity.getIdx());
        testplanTestcaseEntity.setIdx(count);
        testplanTestcaseMapper.updateByPrimaryKey(testplanTestcaseEntity);
    }

    private void testplanTestcaseFirst(TestplanTestcaseEntity testplanTestcaseEntity) {
        if(testplanTestcaseEntity.getIdx()==1){
            return;
        }
        testplanTestcaseMapper.updateFrist(testplanTestcaseEntity.getTestplanId(),testplanTestcaseEntity.getIdx());
        testplanTestcaseEntity.setIdx(1);
        testplanTestcaseMapper.updateByPrimaryKey(testplanTestcaseEntity);
    }

    private void testplanTestcaseDown(List<TestplanTestcaseEntity> testplanTestcaseEntityList, TestplanTestcaseEntity testplanTestcaseEntity) {
        if(testplanTestcaseEntity.getIdx()==testplanTestcaseEntityList.size()){
            return;
        }
        int idx = testplanTestcaseEntity.getIdx()+1;
        testplanTestcaseEntity.setIdx(idx);
        testplanTestcaseMapper.updateByPrimaryKey(testplanTestcaseEntity);
        for(TestplanTestcaseEntity tte:testplanTestcaseEntityList){
            if(tte.getIdx()==idx){
                tte.setIdx(idx-1);
                testplanTestcaseMapper.updateByPrimaryKey(tte);
                break;
            }
        }
    }

    private void testplanTestcaseUp(List<TestplanTestcaseEntity> testplanTestcaseEntityList, TestplanTestcaseEntity testplanTestcaseEntity) {
        if(testplanTestcaseEntity.getIdx()==1){
            return;
        }
        int idx = testplanTestcaseEntity.getIdx()-1;
        testplanTestcaseEntity.setIdx(idx);
        testplanTestcaseMapper.updateByPrimaryKey(testplanTestcaseEntity);
        for(TestplanTestcaseEntity tte:testplanTestcaseEntityList){
            if(tte.getIdx()==idx){
                tte.setIdx(idx+1);
                testplanTestcaseMapper.updateByPrimaryKey(tte);
                break;
            }
        }
    }
}

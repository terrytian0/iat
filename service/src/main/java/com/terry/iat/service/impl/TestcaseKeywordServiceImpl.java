package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.KeywordEntity;
import com.terry.iat.dao.entity.TestcaseKeywordEntity;
import com.terry.iat.dao.mapper.TestcaseKeywordMapper;
import com.terry.iat.service.KeywordService;
import com.terry.iat.service.TestcaseKeywordService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.enums.Index;
import com.terry.iat.service.vo.AddKeywordVO;
import com.terry.iat.service.vo.TestcaseIndexVO;
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
public class TestcaseKeywordServiceImpl extends BaseServiceImpl implements TestcaseKeywordService {

    @Autowired
    private TestcaseKeywordMapper testcaseKeywordMapper;

    @Autowired
    private KeywordService keywordsService;

    @Override
    public List<TestcaseKeywordEntity> create(AddKeywordVO addKeywordVO) {
        List<TestcaseKeywordEntity> testcaseKeywordsApiEntityList = new ArrayList<>();
        Integer maxIdx = testcaseKeywordMapper.getMaxIdx(addKeywordVO.getTestcaseId());
        if (maxIdx == null) {
            maxIdx = 0;
        }
        for (Long keywordId : addKeywordVO.getIds()) {
            maxIdx = maxIdx + 1;
            TestcaseKeywordEntity testcaseKeywordEntity = new TestcaseKeywordEntity();
            testcaseKeywordEntity.setTestcaseId(addKeywordVO.getTestcaseId());
            testcaseKeywordEntity.setKeywordId(keywordId);
            testcaseKeywordEntity.setIdx(maxIdx);
            testcaseKeywordsApiEntityList.add(testcaseKeywordEntity);
        }
        testcaseKeywordMapper.insertList(testcaseKeywordsApiEntityList);
        return testcaseKeywordsApiEntityList;
    }

    @Override
    public int delete(List<Long> ids) {
        return testcaseKeywordMapper.deleteByIds(listToString(ids));
    }

    @Override
    public int deleteByTestcaseId(Long testcaseId) {
        Example example = new Example(TestcaseKeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("testcaseId", testcaseId);
        return testcaseKeywordMapper.deleteByExample(example);
    }

    @Override
    public List<TestcaseKeywordEntity> getByTestcaseId(Long testcaseId) {
        Example example = new Example(TestcaseKeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("testcaseId", testcaseId);
        example.orderBy("idx").asc();
        List<TestcaseKeywordEntity> testcaseKeywordsApiEntityList =
                testcaseKeywordMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        if (!testcaseKeywordsApiEntityList.isEmpty()) {
            testcaseKeywordsApiEntityList.forEach(t -> ids.add(t.getKeywordId()));
        } else {
            return Collections.emptyList();
        }
        List<KeywordEntity> keywordsEntityList = keywordsService.getByIds(ids);
        Map<Long, KeywordEntity> keywordEntityMap = new HashMap<>();
        keywordsEntityList.forEach(k -> keywordEntityMap.put(k.getId(), k));
        testcaseKeywordsApiEntityList.forEach(tk -> tk.setDetail(keywordEntityMap.get(tk.getKeywordId())));
        return testcaseKeywordsApiEntityList;
    }

    @Override
    public void updateIdx(TestcaseIndexVO testcaseIndexVO) {
        List<TestcaseKeywordEntity> testcaseKeywordEntityList = getByTestcaseId(testcaseIndexVO.getTestcaseId());
        TestcaseKeywordEntity testcaseKeywordEntity = testcaseKeywordMapper.selectByPrimaryKey(testcaseIndexVO.getTestcaseKeywordId());
        if (testcaseIndexVO.getIndex() == Index.UP) {
            keywordApiUp(testcaseKeywordEntityList, testcaseKeywordEntity);
        } else if (testcaseIndexVO.getIndex() == Index.DOWN) {
            keywordApiDown(testcaseKeywordEntityList, testcaseKeywordEntity);
        } else if (testcaseIndexVO.getIndex() == Index.FIRST) {
            keywordApiFirst(testcaseKeywordEntity);
        } else if (testcaseIndexVO.getIndex() == Index.LAST) {
            testcaseKeywordLast(testcaseKeywordEntityList.size(), testcaseKeywordEntity);
        }
    }

    @Override
    public List<TestcaseKeywordEntity> getByTestcaseIds(List<Long> testcaseIds) {
        if(testcaseIds.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(TestcaseKeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("testcaseId", testcaseIds);
        return testcaseKeywordMapper.selectByExample(example);
    }

    @Override
    public List<TestcaseKeywordEntity> getByKeywordId(Long keywordId) {
        Example example = new Example(TestcaseKeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("keywordId", keywordId);
        return testcaseKeywordMapper.selectByExample(example);
    }

    private void testcaseKeywordLast(Integer count, TestcaseKeywordEntity testcaseKeywordEntity) {
        if (testcaseKeywordEntity.getIdx() == count) {
            return;
        }
        testcaseKeywordMapper.updateLast(testcaseKeywordEntity.getTestcaseId(), testcaseKeywordEntity.getIdx());
        testcaseKeywordEntity.setIdx(count);
        testcaseKeywordMapper.updateByPrimaryKey(testcaseKeywordEntity);
    }

    private void keywordApiFirst(TestcaseKeywordEntity testcaseKeywordEntity) {
        if (testcaseKeywordEntity.getIdx() == 1) {
            return;
        }
        testcaseKeywordMapper.updateFrist(testcaseKeywordEntity.getKeywordId(), testcaseKeywordEntity.getIdx());
        testcaseKeywordEntity.setIdx(1);
        testcaseKeywordMapper.updateByPrimaryKey(testcaseKeywordEntity);
    }

    private void keywordApiDown(List<TestcaseKeywordEntity> testcaseKeywordEntityList, TestcaseKeywordEntity testcaseKeywordEntity) {
        if (testcaseKeywordEntity.getIdx() == testcaseKeywordEntityList.size()) {
            return;
        }
        int idx = testcaseKeywordEntity.getIdx() + 1;
        testcaseKeywordEntity.setIdx(idx);
        testcaseKeywordMapper.updateByPrimaryKey(testcaseKeywordEntity);
        for (TestcaseKeywordEntity tkae : testcaseKeywordEntityList) {
            if (tkae.getIdx() == idx) {
                tkae.setIdx(idx - 1);
                testcaseKeywordMapper.updateByPrimaryKey(tkae);
                break;
            }
        }
    }

    private void keywordApiUp(List<TestcaseKeywordEntity> testcaseKeywordEntityList, TestcaseKeywordEntity testcaseKeywordEntity) {
        if (testcaseKeywordEntity.getIdx() == 1) {
            return;
        }
        int idx = testcaseKeywordEntity.getIdx() - 1;
        testcaseKeywordEntity.setIdx(idx);
        testcaseKeywordMapper.updateByPrimaryKey(testcaseKeywordEntity);
        for (TestcaseKeywordEntity tkae : testcaseKeywordEntityList) {
            if (tkae.getIdx() == idx) {
                tkae.setIdx(idx + 1);
                testcaseKeywordMapper.updateByPrimaryKey(tkae);
                break;
            }
        }
    }


}

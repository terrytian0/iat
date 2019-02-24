package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.KeywordEntity;
import com.terry.iat.dao.entity.TestcaseKeywordApiEntity;
import com.terry.iat.dao.mapper.TestcaseKeywordsApiMapper;
import com.terry.iat.service.KeywordService;
import com.terry.iat.service.TestcaseKeywordApiService;
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
public class TestcaseKeywordApiServiceImpl extends BaseServiceImpl
        implements TestcaseKeywordApiService {

    @Autowired
    private TestcaseKeywordsApiMapper testcaseKeywordsApiMapper;

    @Autowired
    private KeywordService keywordsService;

    @Override
    public List<TestcaseKeywordApiEntity> create(AddKeywordVO addKeywordVO) {
        List<TestcaseKeywordApiEntity> testcaseKeywordsApiEntityList = new ArrayList<>();
        Integer maxIdx = testcaseKeywordsApiMapper.getMaxIdx(addKeywordVO.getTestcaseId());
        if (maxIdx == null) {
            maxIdx = 0;
        }
        for (Long keywordId : addKeywordVO.getIds()) {
            maxIdx = maxIdx + 1;
            TestcaseKeywordApiEntity testcaseKeywordApiEntity = new TestcaseKeywordApiEntity();
            testcaseKeywordApiEntity.setTestcaseId(addKeywordVO.getTestcaseId());
            testcaseKeywordApiEntity.setKeywordId(keywordId);
            testcaseKeywordApiEntity.setIdx(maxIdx);
            testcaseKeywordsApiEntityList.add(testcaseKeywordApiEntity);
        }
        testcaseKeywordsApiMapper.insertList(testcaseKeywordsApiEntityList);
        return testcaseKeywordsApiEntityList;
    }

    @Override
    public int delete(List<Long> ids) {
        return testcaseKeywordsApiMapper.deleteByIds(listToString(ids));
    }

    @Override
    public List<TestcaseKeywordApiEntity> getByTestcaseId(Long testcaseId) {
        Example example = new Example(TestcaseKeywordApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("testcaseId", testcaseId);
        example.orderBy("idx").asc();
        List<TestcaseKeywordApiEntity> testcaseKeywordsApiEntityList =
                testcaseKeywordsApiMapper.selectByExample(example);
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
        List<TestcaseKeywordApiEntity> testcaseKeywordApiEntityList = getByTestcaseId(testcaseIndexVO.getTestcaseId());
        TestcaseKeywordApiEntity testcaseKeywordApiEntity = testcaseKeywordsApiMapper.selectByPrimaryKey(testcaseIndexVO.getTestcaseKeywordId());
        if (testcaseIndexVO.getIndex() == Index.UP) {
            keywordApiUp(testcaseKeywordApiEntityList, testcaseKeywordApiEntity);
        } else if (testcaseIndexVO.getIndex() == Index.DOWN) {
            keywordApiDown(testcaseKeywordApiEntityList, testcaseKeywordApiEntity);
        } else if (testcaseIndexVO.getIndex() == Index.FIRST) {
            keywordApiFirst(testcaseKeywordApiEntity);
        } else if (testcaseIndexVO.getIndex() == Index.LAST) {
            testcaseKeywordLast(testcaseKeywordApiEntityList.size(), testcaseKeywordApiEntity);
        }
    }

    private void testcaseKeywordLast(Integer count, TestcaseKeywordApiEntity testcaseKeywordApiEntity) {
        if (testcaseKeywordApiEntity.getIdx() == count) {
            return;
        }
        testcaseKeywordsApiMapper.updateLast(testcaseKeywordApiEntity.getTestcaseId(), testcaseKeywordApiEntity.getIdx());
        testcaseKeywordApiEntity.setIdx(count);
        testcaseKeywordsApiMapper.updateByPrimaryKey(testcaseKeywordApiEntity);
    }

    private void keywordApiFirst(TestcaseKeywordApiEntity testcaseKeywordApiEntity) {
        if (testcaseKeywordApiEntity.getIdx() == 1) {
            return;
        }
        testcaseKeywordsApiMapper.updateFrist(testcaseKeywordApiEntity.getKeywordId(), testcaseKeywordApiEntity.getIdx());
        testcaseKeywordApiEntity.setIdx(1);
        testcaseKeywordsApiMapper.updateByPrimaryKey(testcaseKeywordApiEntity);
    }

    private void keywordApiDown(List<TestcaseKeywordApiEntity> testcaseKeywordApiEntityList, TestcaseKeywordApiEntity testcaseKeywordApiEntity) {
        if (testcaseKeywordApiEntity.getIdx() == testcaseKeywordApiEntityList.size()) {
            return;
        }
        int idx = testcaseKeywordApiEntity.getIdx() + 1;
        testcaseKeywordApiEntity.setIdx(idx);
        testcaseKeywordsApiMapper.updateByPrimaryKey(testcaseKeywordApiEntity);
        for (TestcaseKeywordApiEntity tkae : testcaseKeywordApiEntityList) {
            if (tkae.getIdx() == idx) {
                tkae.setIdx(idx - 1);
                testcaseKeywordsApiMapper.updateByPrimaryKey(tkae);
                break;
            }
        }
    }

    private void keywordApiUp(List<TestcaseKeywordApiEntity> testcaseKeywordApiEntityList, TestcaseKeywordApiEntity testcaseKeywordApiEntity) {
        if (testcaseKeywordApiEntity.getIdx() == 1) {
            return;
        }
        int idx = testcaseKeywordApiEntity.getIdx() - 1;
        testcaseKeywordApiEntity.setIdx(idx);
        testcaseKeywordsApiMapper.updateByPrimaryKey(testcaseKeywordApiEntity);
        for (TestcaseKeywordApiEntity tkae : testcaseKeywordApiEntityList) {
            if (tkae.getIdx() == idx) {
                tkae.setIdx(idx + 1);
                testcaseKeywordsApiMapper.updateByPrimaryKey(tkae);
                break;
            }
        }
    }


}

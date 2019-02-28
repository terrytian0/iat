package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.KeywordEntity;
import com.terry.iat.dao.entity.TaskTestcaseKeywordApiEntity;
import com.terry.iat.dao.entity.TaskTestcaseKeywordEntity;
import com.terry.iat.dao.entity.TestcaseKeywordEntity;
import com.terry.iat.dao.mapper.TaskTestcaseKeywordMapper;
import com.terry.iat.service.TaskTestcaseKeywordApiResultService;
import com.terry.iat.service.TaskTestcaseKeywordApiService;
import com.terry.iat.service.TaskTestcaseKeywordService;
import com.terry.iat.service.TestcaseKeywordService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author terry
 * @version 1.0
 * @class name TaskTestcaseKeywordServiceImpl
 * @description TODO
 * @date 2019/2/19 9:16
 **/
@Service
public class TaskTestcaseKeywordServiceImpl extends BaseServiceImpl implements TaskTestcaseKeywordService {
    @Autowired
    private TestcaseKeywordService testcaseKeywordService;

    @Autowired
    private TaskTestcaseKeywordMapper taskTestcaseKeywordEntity;

    @Autowired
    private TaskTestcaseKeywordApiService taskTestcaseKeywordApiService;

    @Autowired
    private TaskTestcaseKeywordApiResultService taskTestcaseKeywordApiResultService;

    @Override
    public List<TaskTestcaseKeywordEntity> create(Long taskId, Long testplanId, Long testcaseId) {
        List<TestcaseKeywordEntity> testcaseKeywordEntityList = testcaseKeywordService.getByTestcaseId(testcaseId);
        if (testcaseKeywordEntityList == null || testcaseKeywordEntityList.isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试用例中不存在关键字！"));
        }
        List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntityList = new ArrayList<>();
        for (TestcaseKeywordEntity testcaseKeywordEntity : testcaseKeywordEntityList) {
            KeywordEntity keywordEntity = testcaseKeywordEntity.getDetail();
            TaskTestcaseKeywordEntity taskTestcaseKeywordEntity = new TaskTestcaseKeywordEntity();
            taskTestcaseKeywordEntity.setTestcaseId(testcaseId);
            taskTestcaseKeywordEntity.setTestcaseKeywordId(testcaseKeywordEntity.getId());
            taskTestcaseKeywordEntity.setTaskId(taskId);
            taskTestcaseKeywordEntity.setTestcaseKeywordId(testcaseKeywordEntity.getId());
            taskTestcaseKeywordEntity.setKeywordId(testcaseKeywordEntity.getKeywordId());
            taskTestcaseKeywordEntity.setIdx(testcaseKeywordEntity.getIdx());
            taskTestcaseKeywordEntity.setName(keywordEntity.getName());
            taskTestcaseKeywordEntity.setDescription(keywordEntity.getDescription());
            taskTestcaseKeywordEntityList.add(taskTestcaseKeywordEntity);
        }
        int rows = taskTestcaseKeywordEntity.insertList(taskTestcaseKeywordEntityList);
        if (rows != taskTestcaseKeywordEntityList.size()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试任务中创建keyword失败！"));
        }
        for (TaskTestcaseKeywordEntity testcaseKeywordEntity : taskTestcaseKeywordEntityList) {
            List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntityList = taskTestcaseKeywordApiService.create(taskId, testplanId, testcaseId, testcaseKeywordEntity.getTestcaseKeywordId(), testcaseKeywordEntity.getKeywordId());
            testcaseKeywordEntity.setApis(taskTestcaseKeywordApiEntityList);
        }
        return taskTestcaseKeywordEntityList;
    }

    @Override
    public List<TaskTestcaseKeywordEntity> getByTaskId(Long taskId) {
        Example example = new Example(TaskTestcaseKeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", taskId);
        return taskTestcaseKeywordEntity.selectByExample(example);
    }

    @Override
    public PageInfo getByTaskIdAndTestcaseId(Integer pn, Integer ps, Long taskId, Long testcaseId, Long parameterId) {
        PageHelper.startPage(pn, ps);
        Example example = new Example(TaskTestcaseKeywordEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId", taskId);
        criteria.andEqualTo("testcaseId", testcaseId);
        List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntityList = taskTestcaseKeywordEntity.selectByExample(example);
        for (TaskTestcaseKeywordEntity testcaseKeywordEntity : taskTestcaseKeywordEntityList) {
            Integer res = taskTestcaseKeywordApiResultService.checkKeyword(taskId, testcaseId, parameterId, testcaseKeywordEntity.getTestcaseKeywordId(), testcaseKeywordEntity.getKeywordId());
            if (res == 1) {
                testcaseKeywordEntity.setStatus("true");
            } else if (res == 2) {
                testcaseKeywordEntity.setStatus("false");
            }
        }
        return new PageInfo(taskTestcaseKeywordEntityList);
    }
}

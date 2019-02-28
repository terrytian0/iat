package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.TaskTestcaseMapper;
import com.terry.iat.service.TaskTestcaseKeywordService;
import com.terry.iat.service.TaskTestcaseParameterService;
import com.terry.iat.service.TaskTestcaseService;
import com.terry.iat.service.TestplanTestcaseService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.TaskStatus;
import com.terry.iat.service.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author terry
 * @version 1.0
 * @class name TaskTestcaseServiceImpl
 * @description TODO
 * @date 2019/2/18 21:02
 **/
@Service
public class TaskTestcaseServiceImpl extends BaseServiceImpl implements TaskTestcaseService {

    @Autowired
    private TaskTestcaseKeywordService taskTestcaseKeywordService;

    @Autowired
    private TaskTestcaseParameterService taskTestcaseParameterService;
    @Autowired
    private TestplanTestcaseService testplanTestcaseService;

    @Autowired
    private TaskTestcaseMapper taskTestcaseMapper;

    @Override
    public List<TaskTestcaseEntity> create(Long taskId, Long testplanId) {
        List<TaskTestcaseEntity> taskTestcaseEntityList = new ArrayList<>();
        List<TestplanTestcaseEntity> testplanTestcaseEntityList = testplanTestcaseService.getByTestplanId(testplanId);
        for (TestplanTestcaseEntity testplanTestcaseEntity : testplanTestcaseEntityList) {
            TestcaseEntity testcaseEntity = testplanTestcaseEntity.getDetail();
            TaskTestcaseEntity taskTestcaseEntity = new TaskTestcaseEntity();
            taskTestcaseEntity.setTaskId(taskId);
            taskTestcaseEntity.setName(testcaseEntity.getName());
            taskTestcaseEntity.setDescription(testcaseEntity.getDescription());
            taskTestcaseEntity.setTestcaseId(testcaseEntity.getId());
            taskTestcaseEntity.setIdx(testplanTestcaseEntity.getIdx());
            taskTestcaseEntity.setStatus(TaskStatus.CREATE.name());
            taskTestcaseEntityList.add(taskTestcaseEntity);
        }
        int rows = taskTestcaseMapper.insertList(taskTestcaseEntityList);
        if (rows != taskTestcaseEntityList.size()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试任务中创建测试用例失败！"));
        }
        for (TaskTestcaseEntity taskTestcaseEntity : taskTestcaseEntityList) {
            List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntityList = taskTestcaseKeywordService.create(taskId, testplanId, taskTestcaseEntity.getTestcaseId());
            taskTestcaseEntity.setKeywords(taskTestcaseKeywordEntityList);
            List<TaskTestcaseParameterEntity> parameters = taskTestcaseParameterService.create(taskId, testplanId, taskTestcaseEntity.getTestcaseId());
            taskTestcaseEntity.setParameters(parameters);
        }
        return taskTestcaseEntityList;
    }

    @Override
    public List<TaskTestcaseEntity> getByTaskId(Long taskId) {
        Example example = new Example(TaskTestcaseEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId",taskId);
        List<TaskTestcaseEntity> taskTestcaseEntityList =  taskTestcaseMapper.selectByExample(example);
        for (TaskTestcaseEntity taskTestcaseEntity : taskTestcaseEntityList) {
            List<TaskTestcaseParameterEntity> taskTestcaseParameterEntityList = taskTestcaseParameterService.getByTaskIdAndTestcaseId(taskId,taskTestcaseEntity.getTestcaseId());
            String status = "";
            for (TaskTestcaseParameterEntity taskTestcaseParameterEntity : taskTestcaseParameterEntityList) {
                if("true".equals(taskTestcaseParameterEntity.getStatus())){
                    status = "true";
                }else  if("false".equals(taskTestcaseParameterEntity.getStatus())){
                    status = "false";
                    break;
                }
            }
            taskTestcaseEntity.setStatus(status);
        }
        return taskTestcaseEntityList;
    }


}

package com.terry.iat.service.impl;

import com.terry.iat.dao.entity.TaskTestcaseKeywordApiResultEntity;
import com.terry.iat.dao.mapper.TaskTestcaseKeywordApiResultMapper;
import com.terry.iat.service.TaskService;
import com.terry.iat.service.TaskTestcaseKeywordApiResultService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.vo.TaskTestcaseKeywordApiResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author terry
 * @version 1.0
 * @class name TaskResultServiceImpl
 * @description TODO
 * @date 2019/2/22 12:45
 **/
@Service
public class TaskTestcaseKeywordApiResultServiceImpl extends BaseServiceImpl implements TaskTestcaseKeywordApiResultService {
    @Autowired
    private TaskTestcaseKeywordApiResultMapper taskTestcaseKeywordApiResultMapper;

    @Autowired
    private TaskService taskService;

    @Override
    public Boolean create(TaskTestcaseKeywordApiResultVO taskTestcaseKeywordApiResultVO) {
        //TODO 验证client是否有效
        TaskTestcaseKeywordApiResultEntity taskTestcaseKeywordApiResultEntity = new TaskTestcaseKeywordApiResultEntity();
        taskTestcaseKeywordApiResultEntity.setTaskId(taskTestcaseKeywordApiResultVO.getTaskId());
        taskTestcaseKeywordApiResultEntity.setTestplanId(taskTestcaseKeywordApiResultVO.getTestplanId());
        taskTestcaseKeywordApiResultEntity.setTestcaseId(taskTestcaseKeywordApiResultVO.getTestcaseId());
        taskTestcaseKeywordApiResultEntity.setParameterId(taskTestcaseKeywordApiResultVO.getParameterId());
        taskTestcaseKeywordApiResultEntity.setTestcaseKeywordId(taskTestcaseKeywordApiResultVO.getTestcaseKeywordId());
        taskTestcaseKeywordApiResultEntity.setKeywordId(taskTestcaseKeywordApiResultVO.getKeywordId());
        taskTestcaseKeywordApiResultEntity.setKeywordApiId(taskTestcaseKeywordApiResultVO.getKeywordApiId());
        taskTestcaseKeywordApiResultEntity.setApiId(taskTestcaseKeywordApiResultVO.getApiId());
        taskTestcaseKeywordApiResultEntity.setRequestHeaders(taskTestcaseKeywordApiResultVO.getRequestHeaders());
        taskTestcaseKeywordApiResultEntity.setRequestFormdatas(taskTestcaseKeywordApiResultVO.getRequestFormdatas());
        taskTestcaseKeywordApiResultEntity.setRequestBody(taskTestcaseKeywordApiResultVO.getRequestBody());
        taskTestcaseKeywordApiResultEntity.setResponseHeaders(taskTestcaseKeywordApiResultVO.getResponseHeaders());
        taskTestcaseKeywordApiResultEntity.setResponseBody(taskTestcaseKeywordApiResultVO.getResponseBody());
        taskTestcaseKeywordApiResultEntity.setExtractors(taskTestcaseKeywordApiResultVO.getExtractors());
        taskTestcaseKeywordApiResultEntity.setAsserts(taskTestcaseKeywordApiResultVO.getAsserts());
        taskTestcaseKeywordApiResultEntity.setStatus(taskTestcaseKeywordApiResultVO.getStatus());
        taskTestcaseKeywordApiResultEntity.setMessage(taskTestcaseKeywordApiResultVO.getMessage());
        taskTestcaseKeywordApiResultEntity.setStartTime(taskTestcaseKeywordApiResultVO.getStartTime());
        taskTestcaseKeywordApiResultEntity.setEndTime(taskTestcaseKeywordApiResultVO.getEndTime());
        int rows = taskTestcaseKeywordApiResultMapper.insert(taskTestcaseKeywordApiResultEntity);
        if (rows != 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public TaskTestcaseKeywordApiResultEntity get(Long taskId, Long testcaseId, Long parameterId,Long testcaseKeywordId, Long keywordId,Long keywordApiId, Long apiId) {
        return taskTestcaseKeywordApiResultMapper.get(taskId, testcaseId, parameterId,testcaseKeywordId, keywordId,keywordApiId, apiId);
    }

    @Override
    public  Integer checkKeyword(Long taskId, Long testcaseId, Long parameterId,Long testcaseKeeywordId, Long keywordId) {
        List<TaskTestcaseKeywordApiResultEntity> taskTestcaseKeywordApiResultEntityList = taskTestcaseKeywordApiResultMapper.getByKeyword(taskId, testcaseId, parameterId,testcaseKeeywordId, keywordId);
        if(taskTestcaseKeywordApiResultEntityList==null||taskTestcaseKeywordApiResultEntityList.isEmpty()){
            return 0;
        }
        Integer res = 1;
        for (TaskTestcaseKeywordApiResultEntity taskTestcaseKeywordApiResultEntity : taskTestcaseKeywordApiResultEntityList) {
            if("false".equals(taskTestcaseKeywordApiResultEntity.getStatus())){
                res=2;
                break;
            }
        }
        return res;
    }
}

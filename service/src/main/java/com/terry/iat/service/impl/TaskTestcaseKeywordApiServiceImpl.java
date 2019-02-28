package com.terry.iat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.TaskTestcaseKeywordApiMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.TaskStatus;
import com.terry.iat.service.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author terry
 * @version 1.0
 * @class name TaskTestcaseKeywordApiServiceImpl
 * @description TODO
 * @date 2019/2/19 9:57
 **/
@Service
public class TaskTestcaseKeywordApiServiceImpl extends BaseServiceImpl implements TaskTestcaseKeywordApiService {

    @Autowired
    private KeywordApiService keywordApiService;

    @Autowired
    private HeaderService headerService;
    @Autowired
    private FormDataService formDataService;

    @Autowired
    private TaskTestcaseKeywordApiMapper taskTestcaseKeywordApiMapper;

    @Autowired
    private ExtractorService extractorService;
    @Autowired
    private AssertService assertService;

    @Autowired
    private TaskTestcaseKeywordApiResultService taskTestcaseKeywordApiResultService;

    @Override
    public  List<TaskTestcaseKeywordApiEntity> create(Long taskId, Long testplanId, Long testcaseId,Long testcaseKeywordId, Long keywordId) {
        List<KeywordApiEntity> keywordApiEntityList = keywordApiService.getByKeywordId(keywordId);
        if(keywordApiEntityList==null||keywordApiEntityList.isEmpty()){
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("关键字中不存在Api！"));
        }
        List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntityList = new ArrayList<>();
        for (KeywordApiEntity keywordApiEntity : keywordApiEntityList) {
            ApiEntity apiEntity = keywordApiEntity.getDetail();
            TaskTestcaseKeywordApiEntity taskTestcaseKeywordApiEntity = new TaskTestcaseKeywordApiEntity();
            taskTestcaseKeywordApiEntity.setServiceId(apiEntity.getServiceId());
            taskTestcaseKeywordApiEntity.setTaskId(taskId);
            taskTestcaseKeywordApiEntity.setTestplanId(testplanId);
            taskTestcaseKeywordApiEntity.setTestcaseId(testcaseId);
            taskTestcaseKeywordApiEntity.setTestcaseKeywordId(testcaseKeywordId);
            taskTestcaseKeywordApiEntity.setKeywordId(keywordId);
            taskTestcaseKeywordApiEntity.setKeywordApiId(keywordApiEntity.getId());
            taskTestcaseKeywordApiEntity.setApiId(apiEntity.getId());
            taskTestcaseKeywordApiEntity.setPath(apiEntity.getPath());
            taskTestcaseKeywordApiEntity.setMethod(apiEntity.getMethod());
            taskTestcaseKeywordApiEntity.setVersion(apiEntity.getVersion());
            taskTestcaseKeywordApiEntity.setDescription(apiEntity.getDescription());
            taskTestcaseKeywordApiEntity.setIdx(keywordApiEntity.getIdx());
            List<HeaderEntity> headerEntityList = headerService.getByApiId(apiEntity.getId());
            List<FormDataEntity> formDataEntityList = formDataService.getByApiId(apiEntity.getId());
            String headers = JSONArray.toJSONString(headerEntityList);
            String formdatas = JSONArray.toJSONString(formDataEntityList);
            String body = JSONObject.toJSONString(apiEntity.getBody());
            taskTestcaseKeywordApiEntity.setHeaders(headers);
            taskTestcaseKeywordApiEntity.setFormdatas(formdatas);
            taskTestcaseKeywordApiEntity.setBody(body);
            List<ExtractorEntity> extractorEntityList = extractorService.getByKeywordApiId(keywordApiEntity.getId());
            List<AssertEntity> assertEntityList = assertService.getByKeywordApiId(keywordApiEntity.getId());
            String extractors = JSONArray.toJSONString(extractorEntityList);
            String asserts = JSONArray.toJSONString(assertEntityList);
            taskTestcaseKeywordApiEntity.setExtractors(extractors);
            taskTestcaseKeywordApiEntity.setAsserts(asserts);
            taskTestcaseKeywordApiEntityList.add(taskTestcaseKeywordApiEntity);
        }
        int rows = taskTestcaseKeywordApiMapper.insertList(taskTestcaseKeywordApiEntityList);
        if(rows!=taskTestcaseKeywordApiEntityList.size()){
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("测试任务中创建Api失败！"));
        }
        return taskTestcaseKeywordApiEntityList;
    }

    @Override
    public List<TaskTestcaseKeywordApiEntity> getByTaskId(Long taskId) {
        Example example = new Example(TaskTestcaseKeywordApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId",taskId);
        return taskTestcaseKeywordApiMapper.selectByExample(example);
    }

    @Override
    public PageInfo getByTaskIdAndTestcaseIdAndKeywordId(Integer pn, Integer ps, Long taskId, Long testcaseId,Long parameterId,Long testcaseKeywordId, Long keywordId) {
        Example example = new Example(TaskTestcaseKeywordApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId",taskId);
        criteria.andEqualTo("testcaseId",testcaseId);
        criteria.andEqualTo("testcaseKeywordId",testcaseKeywordId);
        criteria.andEqualTo("keywordId",keywordId);
        PageHelper.startPage(pn,ps);
        List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntityList = taskTestcaseKeywordApiMapper.selectByExample(example);
        for (TaskTestcaseKeywordApiEntity taskTestcaseKeywordApiEntity : taskTestcaseKeywordApiEntityList) {
            TaskTestcaseKeywordApiResultEntity taskTestcaseKeywordApiResultEntity = taskTestcaseKeywordApiResultService.get(taskId,testcaseId,parameterId,testcaseKeywordId,keywordId,taskTestcaseKeywordApiEntity.getKeywordApiId(),taskTestcaseKeywordApiEntity.getApiId());
            if(taskTestcaseKeywordApiResultEntity!=null){
                taskTestcaseKeywordApiEntity.setStatus(taskTestcaseKeywordApiResultEntity.getStatus());
            }
        }
        return new PageInfo(taskTestcaseKeywordApiEntityList);
    }
}

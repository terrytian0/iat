package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.TaskMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.TaskStatus;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.vo.TaskResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.beans.Transient;
import java.util.*;

/**
 * @author terry
 * @version 1.0
 * @class name TaskServiceImpl
 * @description TODO
 * @date 2019/2/18 20:38
 **/
@Service
public class TaskServiceImpl extends BaseServiceImpl implements TaskService {

    @Autowired
    private TestplanService testplanService;

    @Autowired
    private TaskTestcaseService taskTestcaseService;

    @Autowired
    private TaskTestcaseParameterService taskTestcaseParameterService;

    @Autowired
    private TaskTestcaseKeywordService taskTestcaseKeywordService;

    @Autowired
    private TaskTestcaseKeywordApiService taskTestcaseKeywordApiService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ClientService clientService;

    @Override
    public TaskEntity create(Long testplanId) {
        TestplanEntity testplanEntity = testplanService.getById(testplanId);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setServiceId(testplanEntity.getServiceId());
        taskEntity.setTestplanId(testplanId);
        taskEntity.setTestplanName(testplanEntity.getName());
        taskEntity.setCreateTime(getTimestamp());
        taskEntity.setUpdateTime(getTimestamp());
        taskEntity.setCreateUser(getCurrentUser().getName());
        taskEntity.setUpdateUser(getCurrentUser().getName());
        taskEntity.setPassRate(0);
        taskEntity.setCoverage(0);
        taskEntity.setStatus(TaskStatus.CREATE.name());
        int rows = taskMapper.insert(taskEntity);
        if (rows != 1) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("创建任务失败！"));
        }
        List<TaskTestcaseEntity> taskTestcaseEntityList = taskTestcaseService.create(taskEntity.getId(), testplanId);
        taskEntity.setTestcases(taskTestcaseEntityList);
        Integer coverage = coverage(taskEntity);
        taskEntity.setCoverage(coverage);
        taskMapper.updateByPrimaryKey(taskEntity);
        return taskEntity;
    }

    @Override
    public synchronized TaskEntity get(String client,String pkey) {
        clientService.check(client,pkey);
        TaskEntity taskEntity = getByNoRun(client);
        if (taskEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("任务不存在！"));
        }
        List<TaskTestcaseEntity> taskTestcaseEntityList = taskTestcaseService.getByTaskId(taskEntity.getId());

        List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntityList = taskTestcaseKeywordService.getByTaskId(taskEntity.getId());
        Map<String, List<TaskTestcaseKeywordEntity>> taskTestcaseKeywordEntityHashMap = new HashMap<>();
        for (TaskTestcaseKeywordEntity taskTestcaseKeywordEntity : taskTestcaseKeywordEntityList) {
            String key = "" + taskTestcaseKeywordEntity.getTestcaseId();
            List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntities = taskTestcaseKeywordEntityHashMap.get(key);
            if (taskTestcaseKeywordEntities == null) {
                taskTestcaseKeywordEntities = new ArrayList<>();
            }
            taskTestcaseKeywordEntities.add(taskTestcaseKeywordEntity);
            taskTestcaseKeywordEntityHashMap.put(key, taskTestcaseKeywordEntities);
        }


        List<TaskTestcaseParameterEntity> taskTestcaseParameterEntityList = taskTestcaseParameterService.getByTaskId(taskEntity.getId());
        Map<String, List<TaskTestcaseParameterEntity>> taskTestcaseParameterEntityHashMap = new HashMap<>();

        for (TaskTestcaseParameterEntity taskTestcaseParameterEntity : taskTestcaseParameterEntityList) {
            String key="" + taskTestcaseParameterEntity.getTestcaseId();
            List<TaskTestcaseParameterEntity> parameters = taskTestcaseParameterEntityHashMap.get(key);
            if(parameters==null){
                parameters= new ArrayList<>();
            }
            parameters.add(taskTestcaseParameterEntity);
            taskTestcaseParameterEntityHashMap.put(key, parameters);
        }

        List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntityList = taskTestcaseKeywordApiService.getByTaskId(taskEntity.getId());
        Map<String, List<TaskTestcaseKeywordApiEntity>> taskTestcaseKeywordApiEntityHashMap = new HashMap<>();
        for (TaskTestcaseKeywordApiEntity taskTestcaseKeywordApiEntity : taskTestcaseKeywordApiEntityList) {
            String key = "" + taskTestcaseKeywordApiEntity.getTestcaseId() +taskTestcaseKeywordApiEntity.getTestcaseKeywordId()+ taskTestcaseKeywordApiEntity.getKeywordId();
            List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntities = taskTestcaseKeywordApiEntityHashMap.get(key);
            if (taskTestcaseKeywordApiEntities == null) {
                taskTestcaseKeywordApiEntities = new ArrayList<>();
            }
            taskTestcaseKeywordApiEntities.add(taskTestcaseKeywordApiEntity);
            taskTestcaseKeywordApiEntityHashMap.put(key, taskTestcaseKeywordApiEntities);
        }

        for (TaskTestcaseEntity taskTestcaseEntity : taskTestcaseEntityList) {
            String testcaseId = "" + taskTestcaseEntity.getTestcaseId();
            List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntities = taskTestcaseKeywordEntityHashMap.get(testcaseId);
            for (TaskTestcaseKeywordEntity taskTestcaseKeywordEntity : taskTestcaseKeywordEntities) {
                String keywordId = "" + taskTestcaseKeywordEntity.getTestcaseId() +taskTestcaseKeywordEntity.getTestcaseKeywordId() + taskTestcaseKeywordEntity.getKeywordId();
                List<TaskTestcaseKeywordApiEntity> testcaseKeywordApiEntities = taskTestcaseKeywordApiEntityHashMap.get(keywordId);
                taskTestcaseKeywordEntity.setApis(testcaseKeywordApiEntities);
            }
            taskTestcaseEntity.setKeywords(taskTestcaseKeywordEntities);
            taskTestcaseEntity.setParameters(taskTestcaseParameterEntityHashMap.get(""+taskTestcaseEntity.getTestcaseId()));
        }
        taskEntity.setTestcases(taskTestcaseEntityList);
        return taskEntity;
    }

    @Override
    public TaskEntity getById(Long taskId) {
        TaskEntity taskEntity = taskMapper.selectByPrimaryKey(taskId);
        if (taskEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("任务不存在！"));
        }
        return taskEntity;
    }

    @Override
    public Boolean updateStatus(TaskResultVO taskResultVO) {
        //TODO 验证client是否有效
        TaskEntity taskEntity = getById(taskResultVO.getId());
        taskEntity.setStatus(taskResultVO.getStatus());
        taskEntity.setStartTime(taskResultVO.getStartTime());
        taskEntity.setEndTime(taskResultVO.getEndTime());
        taskEntity.setMessage(taskResultVO.getMessage());
        int rows = taskMapper.updateByPrimaryKey(taskEntity);
        if(rows!=1){
            return true;
        }else{
            return false;
        }
    }

    private synchronized TaskEntity getByNoRun(String client) {
        TaskEntity taskEntity = taskMapper.getNotRun();
        if (taskEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("不存在待执行任务！"));
        }
        taskEntity.setClient(client);
        taskEntity.setStatus(TaskStatus.RUNNING.name());
        int rows = taskMapper.updateByPrimaryKey(taskEntity);
        if(rows!=1){
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("修改任务状态失败！"));
        }
        return taskEntity;
    }

    @Override
    public PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId) {
        Example example = new Example(TaskEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        PageHelper.startPage(pn, ps);
        return new PageInfo(taskMapper.selectByExample(example));
    }


    private Integer coverage(TaskEntity taskEntity) {
        List<ApiEntity> apiEntityList = apiService.getByServiceId(taskEntity.getServiceId());
        Set<Long> fullApiIds = new HashSet<>();
        apiEntityList.forEach(apiEntity -> {
            fullApiIds.add(apiEntity.getId());
        });
        Set<Long> taskApiIds = new HashSet<>();
        for (TaskTestcaseEntity testcase : taskEntity.getTestcases()) {
            for (TaskTestcaseKeywordEntity keyword : testcase.getKeywords()) {
                for (TaskTestcaseKeywordApiEntity api : keyword.getApis()) {
                    taskApiIds.add(api.getApiId());
                }
            }
        }
        return taskApiIds.size() * 100 / fullApiIds.size();
    }
}

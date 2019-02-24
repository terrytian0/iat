package com.terry.iat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.TaskTestcaseKeywordEntity;
import com.terry.iat.dao.entity.TaskTestcaseParameterEntity;
import com.terry.iat.dao.mapper.TaskTestcaseParameterMapper;
import com.terry.iat.service.ClientService;
import com.terry.iat.service.ParameterValueService;
import com.terry.iat.service.TaskTestcaseParameterService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.TaskStatus;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.vo.TaskTestcaseParameterResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author terry
 * @version 1.0
 * @class name TaskTestcaseParameterServiceImpl
 * @description TODO
 * @date 2019/2/19 9:39
 **/
@Service
public class TaskTestcaseParameterServiceImpl extends BaseServiceImpl implements TaskTestcaseParameterService {
    @Autowired
    private ParameterValueService parameterValueService;
    @Autowired
    private TaskTestcaseParameterMapper taskTestcaseParameterMapper;
    @Autowired
    private ClientService clientService;

    @Override
    public List<TaskTestcaseParameterEntity> create(Long taskId, Long testplanId, Long testcaseId) {
        List<Map<String, String>> parameters = parameterValueService.getByTestcaseId(testcaseId);
        List<TaskTestcaseParameterEntity> taskTestcaseParameterEntityList = new ArrayList<>();
        for (Map<String, String> parameter : parameters) {
            String param = JSONObject.toJSONString(parameter);
            TaskTestcaseParameterEntity taskTestcaseParameterEntity = newTaskTestcaseParameterEntiry(taskId,testplanId,testcaseId,param);
            taskTestcaseParameterEntityList.add(taskTestcaseParameterEntity);
        }

        if(taskTestcaseParameterEntityList.isEmpty()){
            TaskTestcaseParameterEntity taskTestcaseParameterEntity = newTaskTestcaseParameterEntiry(taskId,testplanId,testcaseId,null);
            taskTestcaseParameterEntityList.add(taskTestcaseParameterEntity);
        }
        int rows = taskTestcaseParameterMapper.insertList(taskTestcaseParameterEntityList);
        if (rows != taskTestcaseParameterEntityList.size()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("创建测试任务中的用例参数失败！"));
        }
        return taskTestcaseParameterEntityList;
    }

    private TaskTestcaseParameterEntity newTaskTestcaseParameterEntiry(Long taskId,Long testplanId,Long testcaseId,String param){
        TaskTestcaseParameterEntity taskTestcaseParameterEntity = new TaskTestcaseParameterEntity();
        taskTestcaseParameterEntity.setTaskId(taskId);
        taskTestcaseParameterEntity.setTestplanId(testplanId);
        taskTestcaseParameterEntity.setTestcaseId(testcaseId);
        if(param!=null&&!param.isEmpty()) {
            taskTestcaseParameterEntity.setParameters(param);
        }
        taskTestcaseParameterEntity.setStatus(TaskStatus.CREATE.name());
        return taskTestcaseParameterEntity;
    }

    @Override
    public List<TaskTestcaseParameterEntity> getByTaskId(Long taskId) {
        Example example = new Example(TaskTestcaseParameterEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId",taskId);
        return taskTestcaseParameterMapper.selectByExample(example);
    }

    @Override
    public PageInfo getByTaskIdAndTestcaseId(Integer pn, Integer ps, Long taskId, Long testcaseId) {
        PageHelper.startPage(pn,ps);
        Example example = new Example(TaskTestcaseParameterEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("taskId",taskId);
        criteria.andEqualTo("testcaseId",testcaseId);
        List<TaskTestcaseParameterEntity> taskTestcaseParameterEntityList = taskTestcaseParameterMapper.selectByExample(example);
        return new PageInfo(taskTestcaseParameterEntityList);
    }

    @Override
    public TaskTestcaseParameterEntity getById(Long id) {
        TaskTestcaseParameterEntity taskTestcaseParameterEntity = taskTestcaseParameterMapper.selectByPrimaryKey(id);
        if(taskTestcaseParameterEntity==null){
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("用例参数不存在！"));
        }
        return taskTestcaseParameterEntity;
    }

    @Override
    public Boolean updateStatus(TaskTestcaseParameterResultVO taskTestcaseParameterResultVO) {
        //TODO 验证client是否有效
        TaskTestcaseParameterEntity taskTestcaseParameterEntity = getById(taskTestcaseParameterResultVO.getId());
        taskTestcaseParameterEntity.setStatus(taskTestcaseParameterResultVO.getStatus());
        taskTestcaseParameterEntity.setStartTime(taskTestcaseParameterResultVO.getStartTime());
        taskTestcaseParameterEntity.setEndTime(taskTestcaseParameterResultVO.getEndTime());
        taskTestcaseParameterEntity.setMessage(taskTestcaseParameterResultVO.getMessage());
        int row = taskTestcaseParameterMapper.updateByPrimaryKey(taskTestcaseParameterEntity);
        if(row==1){
            return true;
        }else{
            return false;
        }
    }
}

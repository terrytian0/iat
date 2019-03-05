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
import com.terry.iat.service.vo.NodeVO;
import com.terry.iat.service.vo.TaskResultVO;
import com.terry.iat.service.vo.TestplanEnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
        checkEnv(testplanEntity);
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setServiceId(testplanEntity.getServiceId());
        taskEntity.setTestplanId(testplanId);
        taskEntity.setTestplanName(testplanEntity.getName());
        taskEntity.setEnv(testplanEntity.getEnv());
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

    private void checkEnv(TestplanEntity testplanEntity) {
        List<TestplanEnvVO> testplanEnvVOList = testplanService.getEnvById(testplanEntity.getId());
        testplanEnvVOList.forEach(testplanEnvVO -> {
            if (testplanEnvVO.getEnv() == null) {
                throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("依赖服务(" + testplanEnvVO.getServiceName() + ")未设置测试环境！"));
            }
        });
    }

    @Override
    public synchronized TaskEntity get(String client, String pkey) {
        clientService.check(client, pkey);
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
            String key = "" + taskTestcaseParameterEntity.getTestcaseId();
            List<TaskTestcaseParameterEntity> parameters = taskTestcaseParameterEntityHashMap.get(key);
            if (parameters == null) {
                parameters = new ArrayList<>();
            }
            parameters.add(taskTestcaseParameterEntity);
            taskTestcaseParameterEntityHashMap.put(key, parameters);
        }

        List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntityList = taskTestcaseKeywordApiService.getByTaskId(taskEntity.getId());
        Map<String, List<TaskTestcaseKeywordApiEntity>> taskTestcaseKeywordApiEntityHashMap = new HashMap<>();
        for (TaskTestcaseKeywordApiEntity taskTestcaseKeywordApiEntity : taskTestcaseKeywordApiEntityList) {
            String key = "" + taskTestcaseKeywordApiEntity.getTestcaseId() + taskTestcaseKeywordApiEntity.getTestcaseKeywordId() + taskTestcaseKeywordApiEntity.getKeywordId();
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
                String keywordId = "" + taskTestcaseKeywordEntity.getTestcaseId() + taskTestcaseKeywordEntity.getTestcaseKeywordId() + taskTestcaseKeywordEntity.getKeywordId();
                List<TaskTestcaseKeywordApiEntity> testcaseKeywordApiEntities = taskTestcaseKeywordApiEntityHashMap.get(keywordId);
                taskTestcaseKeywordEntity.setApis(testcaseKeywordApiEntities);
            }
            taskTestcaseEntity.setKeywords(taskTestcaseKeywordEntities);
            taskTestcaseEntity.setParameters(taskTestcaseParameterEntityHashMap.get("" + taskTestcaseEntity.getTestcaseId()));
        }
        taskEntity.setTestcases(taskTestcaseEntityList);
        return taskEntity;
    }

    public void setStatus(NodeVO node, String status) {
        if ("true".equals(status)) {
            node.setStatus(status);
            node.setTags(Arrays.asList("<font color=\"#23ad44\">Pass</font>"));
        } else if ("false".equals(status)) {
            node.setStatus(status);
            node.setTags(Arrays.asList("<font color=\"#f05050\">Fail</font>"));
        } else {
            node.setStatus(status);
            node.setTags(Arrays.asList("Not Run"));
        }
    }

    @Override
    public List<NodeVO> detail(Long taskId) {
        TaskEntity taskEntity = getById(taskId);
        List<NodeVO> taskTree = new ArrayList<>();
        List<TaskTestcaseEntity> taskTestcaseEntityList = taskTestcaseService.getByTaskId(taskId);
        for (TaskTestcaseEntity taskTestcaseEntity : taskTestcaseEntityList) {
            NodeVO testcase = new NodeVO();
            testcase.setLevel(1);
            testcase.setId(taskTestcaseEntity.getTestcaseId());
            testcase.setText(taskTestcaseEntity.getName());
            testcase.setParentid(0L);
            testcase.setTaskId(taskId);
            testcase.setTestcaseId(taskTestcaseEntity.getTestcaseId());
            setStatus(testcase, taskTestcaseEntity.getStatus());
            List<NodeVO> testcaseTree = new ArrayList<>();
            PageInfo parameter = taskTestcaseParameterService.getByTaskIdAndTestcaseId(1, 10000, taskId, taskTestcaseEntity.getTestcaseId());
            List<TaskTestcaseParameterEntity> taskTestcaseParameterEntityList = parameter.getList();
            int pindx = 0;
            for (TaskTestcaseParameterEntity taskTestcaseParameterEntity : taskTestcaseParameterEntityList) {
                pindx++;
                NodeVO p = new NodeVO();
                p.setLevel(2);
                p.setId(taskTestcaseParameterEntity.getId());
//                p.setText(taskTestcaseParameterEntity.getParameters());
                p.setText("第 " + pindx + " 个参数");
                p.setParentid(taskTestcaseParameterEntity.getTestcaseId());
                p.setTaskId(taskId);
                p.setTestcaseId(taskTestcaseEntity.getTestcaseId());
                p.setParameterId(taskTestcaseParameterEntity.getId());
                setStatus(p, taskTestcaseParameterEntity.getStatus());
                List<NodeVO> parameterTree = new ArrayList<>();
                PageInfo keyword = taskTestcaseKeywordService.getByTaskIdAndTestcaseId(1, 1000, taskId, taskTestcaseParameterEntity.getTestcaseId(), taskTestcaseParameterEntity.getId());
                List<TaskTestcaseKeywordEntity> taskTestcaseKeywordEntityList = keyword.getList();
                for (TaskTestcaseKeywordEntity taskTestcaseKeywordEntity : taskTestcaseKeywordEntityList) {
                    NodeVO k = new NodeVO();
                    k.setLevel(3);
                    k.setId(taskTestcaseKeywordEntity.getTestcaseKeywordId());
                    k.setText(taskTestcaseKeywordEntity.getName());
                    k.setParentid(taskTestcaseParameterEntity.getId());
                    k.setTaskId(taskId);
                    k.setTestcaseId(taskTestcaseEntity.getTestcaseId());
                    k.setParameterId(taskTestcaseParameterEntity.getId());
                    k.setTestcaseKeywordId(taskTestcaseKeywordEntity.getTestcaseKeywordId());
                    k.setKeywordId(taskTestcaseKeywordEntity.getKeywordId());
                    setStatus(k, taskTestcaseKeywordEntity.getStatus());
                    List<NodeVO> keywordTree = new ArrayList<>();
                    PageInfo api = taskTestcaseKeywordApiService.getByTaskIdAndTestcaseIdAndKeywordId(1, 10000, taskId, taskTestcaseKeywordEntity.getTestcaseId(), taskTestcaseParameterEntity.getId(), taskTestcaseKeywordEntity.getTestcaseKeywordId(), taskTestcaseKeywordEntity.getKeywordId());
                    List<TaskTestcaseKeywordApiEntity> taskTestcaseKeywordApiEntityList = api.getList();
                    for (TaskTestcaseKeywordApiEntity taskTestcaseKeywordApiEntity : taskTestcaseKeywordApiEntityList) {
                        NodeVO a = new NodeVO();
                        a.setLevel(4);
                        a.setId(taskTestcaseKeywordApiEntity.getKeywordApiId());
                        a.setText(taskTestcaseKeywordApiEntity.getPath());
                        a.setParentid(taskTestcaseKeywordEntity.getTestcaseKeywordId());
                        a.setTaskId(taskId);
                        a.setTestcaseId(taskTestcaseEntity.getTestcaseId());
                        a.setParameterId(taskTestcaseParameterEntity.getId());
                        a.setTestcaseKeywordId(taskTestcaseKeywordEntity.getTestcaseKeywordId());
                        a.setKeywordId(taskTestcaseKeywordEntity.getKeywordId());
                        a.setKeywordApiId(taskTestcaseKeywordApiEntity.getKeywordApiId());
                        a.setApiId(taskTestcaseKeywordApiEntity.getApiId());
                        setStatus(a, taskTestcaseKeywordApiEntity.getStatus());
                        keywordTree.add(a);
                    }
                    k.setNodes(keywordTree);
                    parameterTree.add(k);
                }
                p.setNodes(parameterTree);
                testcaseTree.add(p);
            }
            testcase.setNodes(testcaseTree);
            taskTree.add(testcase);
        }
        return taskTree;
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
        if (rows != 1) {
            return true;
        } else {
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
        if (rows != 1) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("修改任务状态失败！"));
        }
        return taskEntity;
    }

    @Override
    public PageInfo getByServiceId(Integer pn, Integer ps, String searchText, Long serviceId) {
        Example example = new Example(TaskEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        example.orderBy("id").desc();
        PageHelper.startPage(pn, ps);
        List<TaskEntity> taskEntityList = taskMapper.selectByExample(example);
        for (TaskEntity taskEntity : taskEntityList) {
            if (taskEntity.getStartTime() != null && taskEntity.getEndTime() != null) {
                taskEntity.setElapsed(taskEntity.getEndTime().getTime() - taskEntity.getStartTime().getTime());
            }
            taskEntity.setPassRate(getPassRate(taskEntity.getId()));
        }
        return new PageInfo(taskEntityList);
    }

    private Integer getPassRate(Long taskId) {
        int i = 0;
        List<TaskTestcaseParameterEntity> taskTestcaseParameterEntityList = taskTestcaseParameterService.getByTaskId(taskId);
        if (taskTestcaseParameterEntityList.size() == 0) {
            return 0;
        }
        for (TaskTestcaseParameterEntity taskTestcaseParameterEntity : taskTestcaseParameterEntityList) {
            if ("true".equals(taskTestcaseParameterEntity.getStatus())) {
                i++;
            }
        }
        return i * 100 / taskTestcaseParameterEntityList.size();
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
                    if (api.getServiceId() == taskEntity.getServiceId()) {
                        taskApiIds.add(api.getApiId());
                    }
                }
            }
        }
        return taskApiIds.size() * 100 / fullApiIds.size();
    }
}

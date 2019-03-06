package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.*;
import com.terry.iat.service.common.annotation.IgnoreLogin;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.vo.TaskResultVO;
import com.terry.iat.service.vo.TaskTestcaseKeywordApiResultVO;
import com.terry.iat.service.vo.TaskTestcaseParameterResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author terry
 * @Description 测试任务接口
 * @Date 2019/2/18 10:56
 * @Version 1.0
 **/
@ResponseBody
@RestController
@RequestMapping(value = "/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskTestcaseService taskTestcaseService;

    @Autowired
    private TaskTestcaseParameterService taskTestcaseParameterService;

    @Autowired
    private TaskTestcaseKeywordService taskTestcaseKeywordService;

    @Autowired
    private TaskTestcaseKeywordApiService taskTestcaseKeywordApiService;

    @Autowired
    private TaskTestcaseKeywordApiResultService taskTestcaseKeywordApiResultService;

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 创建测试计划
     * @author terry
     * @Date 2019/2/18 10:51
     * @Param [testplanVO]
     **/
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(Long testplanId) {
        return success(taskService.create(testplanId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description TODO
     * @author terry
     * @Date 2019/2/19 12:14
     * @Param [pageNumber, pageSize, searchText, serviceId]
     **/
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(Integer pageNumber, Integer pageSize, String searchText, Long serviceId) {
        return success(taskService.getByServiceId(pageNumber, pageSize, searchText, serviceId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 获取测试任务下的所有测试用例
     * @author terry
     * @Date 2019/2/20 9:56
     * @Param [taskId]
     **/
    @GetMapping("/testcases")
    public Result getTestcases(Long taskId) {
        return success(taskTestcaseService.getByTaskId(taskId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description TODO
     * @author terry
     * @Date 2019/2/20 11:31
     * @Param [pageNumber, pageSize, taskId, testcaseId]
     **/
    @GetMapping("/parameters")
    public Result getParameters(Integer pageNumber, Integer pageSize, Long taskId, Long testcaseId) {
        return success(taskTestcaseParameterService.getByTaskIdAndTestcaseId(pageNumber, pageSize, taskId, testcaseId));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/4 14:51
     * @Param [parameterId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/parameter")
    public Result getParameter(Long parameterId) {
        return success(taskTestcaseParameterService.getById(parameterId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description TODO
     * @author terry
     * @Date 2019/2/20 11:31
     * @Param [pageNumber, pageSize, taskId, testcaseId]
     **/
    @GetMapping("/keywords")
    public Result getKeywords(Integer pageNumber, Integer pageSize, Long taskId, Long testcaseId, Long parameterId) {
        return success(taskTestcaseKeywordService.getByTaskIdAndTestcaseId(pageNumber, pageSize, taskId, testcaseId, parameterId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description TODO
     * @author terry
     * @Date 2019/2/20 11:32
     * @Param [pageNumber, pageSize, taskId, testcaseId, keywordId]
     **/
    @GetMapping("/apis")
    public Result getApis(Integer pageNumber, Integer pageSize, Long taskId, Long testcaseId, Long parameterId, Long testcaseKeywordId, Long keywordId) {
        return success(taskTestcaseKeywordApiService.getByTaskIdAndTestcaseIdAndKeywordId(pageNumber, pageSize, taskId, testcaseId, parameterId, testcaseKeywordId, keywordId));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/4 13:29
     * @Param [taskId, testcaseId, testcaseKeywordId, keywordId, keywordApiId, apiId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/api")
    public Result getApi(Long taskId, Long testcaseId, Long testcaseKeywordId, Long keywordId,Long keywordApiId,Long apiId) {
        return success(taskTestcaseKeywordApiService.get(taskId, testcaseId, testcaseKeywordId, keywordId,keywordApiId,apiId));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/3 20:05
     * @Param [taskId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/detail")
    public Result detail(Long taskId) {
        return success(taskService.detail(taskId));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/4 13:01
     * @Param [taskId, testcaseId, parameterId, testcaseKeywordId, keywordId, keywordApiId, apiId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/result/api")
    public Result getApiResult(Long taskId, Long testcaseId, Long parameterId,Long testcaseKeywordId, Long keywordId,Long keywordApiId, Long apiId) {
        return success(taskTestcaseKeywordApiResultService.get(taskId,testcaseId,parameterId,testcaseKeywordId,keywordId,keywordApiId,apiId));
    }



    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 获取未执行的测试任务
     * @author terry
     * @Date 2019/2/21 14:42
     * @Param [client]
     **/
    @IgnoreLogin
    @GetMapping("/get")
    public Result get(String client, String key) {
        return success(taskService.get(client, key));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description TODO
     * @author terry
     * @Date 2019/2/22 13:21
     * @Param [taskResultVO]
     **/
    @IgnoreLogin
    @PostMapping("/result/api")
    public Result apiResult(@RequestBody TaskTestcaseKeywordApiResultVO taskTestcaseKeywordApiResultVO) {
        return success(taskTestcaseKeywordApiResultService.create(taskTestcaseKeywordApiResultVO));
    }

    @IgnoreLogin
    @PostMapping("/result/parameter")
    public Result parameterResult(@RequestBody TaskTestcaseParameterResultVO taskTestcaseParameterResultVO) {
        return success(taskTestcaseParameterService.updateStatus(taskTestcaseParameterResultVO));
    }

    @IgnoreLogin
    @PostMapping("/result/task")
    public Result taskResult(@RequestBody TaskResultVO taskResultVO) {
        return success(taskService.updateStatus(taskResultVO));
    }

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:22
     * @Param []
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/count")
    public Result count() {
        return success(taskService.getCount());
    }

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:49
     * @Param []
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping(path = "/week-chart")
    public Result getWeekChart(){
        return success(taskService.getWeekChart());
    }

}

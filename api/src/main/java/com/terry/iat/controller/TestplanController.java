package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.TestplanService;
import com.terry.iat.service.TestplanTestcaseService;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author terry
 * @Description 测试计划接口类
 * @Date 2019/2/18 10:56
 * @Version 1.0
 **/
@ResponseBody
@RestController
@RequestMapping(value = "/testplan")
public class TestplanController extends BaseController {

    @Autowired
    private TestplanService testplanService;

    @Autowired
    private TestplanTestcaseService testplanTestcaseService;

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 创建测试计划
     * @author terry
     * @Date 2019/2/18 10:51
     * @Param [testplanVO]
     **/
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Result create(@RequestBody TestplanVO testplanVO) {
        return success(testplanService.create(testplanVO));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 修改测试计划
     * @author terry
     * @Date 2019/2/18 10:52
     * @Param [testplanVO]
     **/
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result update(@RequestBody TestplanVO testplanVO) {
        return success(testplanService.update(testplanVO));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 测试计划详情
     * @author terry
     * @Date 2019/2/18 10:55
     * @Param [testplanId]
     **/
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result info(Long testplanId) {
        return success(testplanService.getById(testplanId));
    }

    @PutMapping(path = "/idx")
    public Result updateIdx(@RequestBody TestplanIndexVO testplanIndexVO) {
        testplanTestcaseService.updateIdx(testplanIndexVO);
        return success();
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 获取测试计划列表
     * @author terry
     * @Date 2019/2/18 10:54
     * @Param [pageNumber, pageSize, searchText, serviceId]
     **/
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Result search(Integer pageNumber, Integer pageSize, String searchText, Long serviceId) {
        return success(testplanService.getByServiceId(pageNumber, pageSize, searchText, serviceId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 删除测试计划
     * @author terry
     * @Date 2019/2/18 10:53
     * @Param [testplanId]
     **/
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result delete(Long testplanId) {
        return success(testplanService.delete(testplanId));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 测试计划中添加测试用例
     * @author terry
     * @Date 2019/2/18 15:23
     * @Param [testplanAddTestcaseVO]
     **/
    @RequestMapping(value = "/testcase/add", method = RequestMethod.PUT)
    public Result addTestcases(@RequestBody AddTestcaseVO addTestcaseVO) {
        return success(testplanService.addTestcase(addTestcaseVO));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 测试计划中移除测试用例
     * @author terry
     * @Date 2019/2/24 17:24
     * @Param [removeTestcaseVO]
     **/
    @RequestMapping(value = "/testcase/remove", method = RequestMethod.DELETE)
    public Result removeTestcases(@RequestBody RemoveTestcaseVO removeTestcaseVO) {
        return success(testplanService.removeTestcase(removeTestcaseVO));
    }

    /**
     * @return com.terry.iat.service.common.bean.Result
     * @Description 获取测试计划下的所有测试用例
     * @author terry
     * @Date 2019/2/18 15:24
     * @Param [pageNumber, pageSize, searchText, serviceId, testplanId]
     **/
    @RequestMapping(value = "/testcase", method = RequestMethod.GET)
    public Result getTestcases(Integer pageNumber, Integer pageSize, String searchText, Long serviceId, Long testplanId) {
        return success(testplanTestcaseService.getNotAddedTestcases(pageNumber, pageSize, searchText, serviceId, testplanId));
    }
    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/24 22:18
     * @Param [testplanId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/env")
    public Result getEnv(Long testplanId) {
        return success(testplanService.getEnvById(testplanId));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/25 12:45
     * @Param [envs]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @PutMapping("/env")
    public Result setEnv(@RequestBody  List<TestplanCreateEnvVO> envs){
        testplanService.createEnv(envs);
        return success();
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:20
     * @Param []
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping("/count")
    public Result count() {
        return success(testplanService.getCount());
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
        return success(testplanService.getWeekChart());
    }

}

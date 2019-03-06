package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.ParameterValueService;
import com.terry.iat.service.TestcaseKeywordService;
import com.terry.iat.service.TestcaseService;
import com.terry.iat.service.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author terry
 * @Auther: houyin.tian
 * @Date: 2018/10/20 11:28
 * @Description:
 */

@ResponseBody
@RestController
@RequestMapping(value = "/testcase")
public class TestcaseController extends BaseController {

    @Autowired
    private TestcaseService testcaseService;

    @Autowired
    private TestcaseKeywordService testcaseKeywordService;

    @Autowired
    private ParameterValueService parameterValueService;
    /**
     * 创建测试用例
     * @param testcaseVO
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result create(@RequestBody TestcaseVO testcaseVO){
        return success(testcaseService.create(testcaseVO));
    }

    /**
     * 修改测试用例
     * @param testcaseVO
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result update(@RequestBody TestcaseVO testcaseVO){
        return success(testcaseService.update(testcaseVO));
    }

    /**
     * 获取测试用例
     * @param id
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Result info(Long id){
        return success(testcaseService.getById(id));
    }

    /**
     * 获取服务下测试用例列表
     * @param pageNumber
     * @param pageSize
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Result search(Integer pageNumber,Integer pageSize,String searchText,Long serviceId){
        return success(testcaseService.getByServiceId(pageNumber,pageSize,searchText,serviceId));
    }

    /**
     * 删除测试用例
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result delete(@RequestBody List<Long> ids){
        return success(testcaseService.delete(ids));
    }

    /**
     * 向测试用例中添加关键字
     * @param addKeywordVO
     * @return
     */
    @RequestMapping(value = "/keyword/add",method = RequestMethod.PUT)
    public Result addKeywords(@RequestBody AddKeywordVO addKeywordVO){
        return success(testcaseService.addKeyword(addKeywordVO));
    }

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:14
     * @Param [removeKeywordVO]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/keyword/remove",method = RequestMethod.DELETE)
    public Result removeKeywords(@RequestBody RemoveKeywordVO removeKeywordVO){
        return success(testcaseService.removeKeyword(removeKeywordVO));
    }

    /**
     * 测试用例debug
     * @param testcaseDebugVO
     * @return
     */
    @PostMapping(path = "/debug")
    public Result debug(@RequestBody TestcaseDebugVO testcaseDebugVO){
        return success(testcaseService.debug(testcaseDebugVO));
    }

    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 17:11
     * @Param [indexVO]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @PutMapping(path = "/idx")
    public Result updateIdx(@RequestBody TestcaseIndexVO indexVO){
        testcaseKeywordService.updateIdx(indexVO);
        return success();
    }

    /**
     * @Description testcase 参数名列表
     * @author terry
     * @Date 2019/2/13 15:23
     * @Param [testcaseId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @PutMapping(path = "/parameter/title")
    public Result getParameterTitle(Long testcaseId){
        return success(testcaseService.getParameters(testcaseId));
    }
    /**
     * @Description testcase参数列表
     * @author terry
     * @Date 2019/2/13 15:24
     * @Param [testcaseId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping(path = "/parameter/data")
    public Result getParameterData(Long testcaseId){
        return success(parameterValueService.getByTestcaseId(testcaseId));
    }
    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/24 17:10
     * @Param [testcaseAddParameterVOS]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @PutMapping(path = "/parameter")
    public Result saveParameter(@RequestBody List<TestcaseAddParameterVO> testcaseAddParameterVOS){
        return success( parameterValueService.createOrUpdate(testcaseAddParameterVOS));
    }
    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/24 17:10
     * @Param [testcaseId, rowNum]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @DeleteMapping(path = "/parameter")
    public Result deleteParameter(Long testcaseId,Integer rowNum){
        return success(parameterValueService.delete(testcaseId,rowNum));
    }

    @GetMapping(path = "/count")
    public Result count(){
        return success(testcaseService.getCount());
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 17:41
     * @Param []
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping(path = "/week-chart")
    public Result getWeekChart(){
        return success(testcaseService.getWeekChart());
    }
}

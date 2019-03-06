package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.ApiService;
import com.terry.iat.service.HeaderService;
import com.terry.iat.service.common.annotation.IgnoreLogin;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.vo.ApiVO;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description Api 接口
 * @author terry
 * @Date 2019/1/22 11:33
 * @Version 1.0
 **/

@ResponseBody
@RestController
@RequestMapping(value = "/api")
public class ApiController extends BaseController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private HeaderService headerService;

    /**
     * @Description 创建Api接口
     * @author terry
     * @Date 2019/1/22 11:34
     * @Param [apiVO]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result create(@RequestBody ApiVO apiVO) {
        return success(apiService.create(apiVO));
    }

    /**
     * @Description Swagger推送接口
     * @author terry
     * @Date 2019/1/22 11:34
     * @Param [apiVOS]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @IgnoreLogin
    @RequestMapping(value = "/push",method = RequestMethod.POST)
    public Result push(@RequestBody List<ApiVO> apiVOS) {
        apiService.push(apiVOS);
        return success();
    }

    /**
     * @Description 修改Api接口
     * @author terry
     * @Date 2019/1/22 11:35
     * @Param [apiVO]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result update(@RequestBody ApiVO apiVO) {
        return success(apiService.update(apiVO));
    }

    /**
     * @Description 删除Api
     * @author terry
     * @Date 2019/1/22 11:40
     * @Param [apiId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result delete(Long apiId){
        return success(apiService.delete(apiId));
    }

    /**
     * @Description 获取Api详情
     * @author terry
     * @Date 2019/1/22 11:41
     * @Param [id]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Result info(@ApiParam(value="Api id",required=true) Long id) {
        return success(apiService.getById(id));
    }

    /**
     * @Description 查找Api
     * @author terry
     * @Date 2019/1/22 11:41
     * @Param [pageNumber, pageSize, searchText, serviceId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Result search(Integer pageNumber,Integer pageSize,String searchText,Long serviceId){
        return success(apiService.getByKeys(pageNumber,pageSize,searchText,serviceId));
    }

    /**
     * @Description Api 调试
     * @author terry
     * @Date 2019/1/22 11:41
     * @Version 1.0
     **/
    @PostMapping(path = "/debug")
    public Result debug(@RequestBody ApiVO apiVO){
        return success(apiService.debug(apiVO));
    }
    /**
     * @Description 获取Api 参数
     * @author terry
     * @Date 2019/1/22 11:42
     * @Param [apiId]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping(path = "/parameters")
    public Result getParameters(Long apiId){
        return success(apiService.getParameters(apiId));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:19
     * @Param []
     * @return com.terry.iat.service.common.bean.Result
     **/
    @GetMapping(path = "/count")
    public Result getCount(){
        return success(apiService.getCount());
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
        return success(apiService.getWeekChart());
    }

}

package com.terry.iat.controller;

import com.terry.iat.service.BodyService;
import com.terry.iat.service.vo.BodyVO;
import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author terry
 * @Auther: houyin.tian
 * @Date: 2018/10/20 11:28
 * @Description:
 */

@ResponseBody
@RestController
@RequestMapping(value = "/api/body")
public class BodyController extends BaseController {

    @Autowired
    private BodyService bodyService;

    /**
     * 添加Body到Api
     * @param bodyVO
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result create(@RequestBody BodyVO bodyVO){
        return success(bodyService.create(bodyVO));
    }

    /**
     * 修改Body
     * @param bodyVO
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result update(@RequestBody BodyVO bodyVO){
        return success(bodyService.update(bodyVO));
    }

    /**
     * 删除Api中的Body
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result delete(Long id){
        return success(bodyService.delete(id));
    }

    /**
     * 通过ApiId获取Body
     * @param apiId
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result get(Long apiId){
        return success(bodyService.getByApiId(apiId));
    }
}

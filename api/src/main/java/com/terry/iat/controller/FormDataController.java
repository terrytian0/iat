package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.FormDataService;
import com.terry.iat.service.vo.FormDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@ResponseBody
@RestController
@RequestMapping(value = "/api/form-data")
public class FormDataController extends BaseController {

    @Autowired
    private FormDataService formDataService;

    /**
     * 添加FormData到Api
     * @param formDataVOList
     * @return
     */
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Result create(@RequestBody List<FormDataVO> formDataVOList){
        return success(formDataService.create(formDataVOList));
    }

    /**
     * 修改FormData
     * @param formDataVO
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result update(@RequestBody FormDataVO formDataVO){
        return success(formDataService.update(formDataVO));
    }

    /**
     * 删除Api中的FormData
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public Result delete(@RequestBody List<Long> ids){
        return success(formDataService.delete(ids));
    }

    /**
     * 通过ApiId获取FormData列表
     * @param apiId
     * @return
     */
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public Result get(Long apiId){
        return success(formDataService.getByApiId(apiId));
    }

}

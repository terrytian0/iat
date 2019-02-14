package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.EnvService;
import com.terry.iat.service.vo.EnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/service/env")
public class EnvController extends BaseController {

    @Autowired
    private EnvService envService;

    @PostMapping("/create")
    public Result create(@RequestBody EnvVO envVO) {
        return success(envService.create(envVO));
    }


    @DeleteMapping("/delete")
    public Result delete(Long id) {
        return success(envService.delete(id));
    }

    @GetMapping("/get")
    public Result get(Long serviceId) {
        return success(envService.getByServiceId(serviceId));
    }

}

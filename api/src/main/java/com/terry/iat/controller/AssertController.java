package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.AssertService;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.vo.AssertVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@ResponseBody
@RequestMapping("/assert")
public class AssertController extends BaseController {

    @Autowired
    private AssertService assertService;

    @PostMapping(path = "/create")
    public Result create(@RequestBody AssertVO assertVO){
        return success(assertService.create(assertVO));
    }

    @GetMapping(path = "/get")
    public Result getByKeywordApiId(Long keywordApiId){
        return success(assertService.getByKeywordApiId(keywordApiId));
    }

    @DeleteMapping(path = "/delete")
    public Result delete(Long assertId){
        return success(assertService.delete(assertId));
    }
}

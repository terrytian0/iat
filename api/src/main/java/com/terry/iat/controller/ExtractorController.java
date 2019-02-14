package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.ExtractorService;
import com.terry.iat.service.vo.ExtractorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/extractor")
public class ExtractorController extends BaseController {

    @Autowired
    private ExtractorService extractorService;

    @PostMapping(path = "/create")
    public Result create(@RequestBody ExtractorVO extractorVO){
        return success(extractorService.create(extractorVO));
    }

    @PutMapping(path = "/update")
    public Result update(@RequestBody ExtractorVO extractorVO){
        return success(extractorService.update(extractorVO));
    }

    @GetMapping(path = "/get")
    public Result getByKeywordApiId(Long keywordApiId){
        return success(extractorService.getByKeywordApiId(keywordApiId));
    }

    @DeleteMapping(path = "/delete")
    public Result delete(Long extractorId){
        return success(extractorService.delete(extractorId));
    }
}

package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.ServiceService;
import com.terry.iat.service.ServiceUserService;
import com.terry.iat.service.vo.ServiceAddUserVO;
import com.terry.iat.service.vo.ServiceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("/service")
public class ServiceController extends BaseController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceUserService serviceUserService;

    @PostMapping("/create")
    public Result create(@RequestBody ServiceVO serviceVO) {
        return success(serviceService.create(serviceVO));
    }

    @PutMapping("/update")
    public Result update(@RequestBody ServiceVO serviceVO) {
        return success(serviceService.update(serviceVO));
    }

    @DeleteMapping("/delete")
    public Result delete(Long id) {
        return success(serviceService.delete(id));
    }

    @GetMapping("/info")
    public Result info(Long id) {
        return success(serviceService.getById(id));
    }

    @GetMapping("/search")
    public Result search(Integer pageNumber, Integer pageSize, String searchText) {
        return success(serviceService.search(pageNumber, pageSize, searchText));
    }

    @PutMapping("/user/add")
    public Result addUser(@RequestBody  ServiceAddUserVO serviceAddUserVO) {
        return success(serviceUserService.create(serviceAddUserVO));
    }

    @DeleteMapping("/user/remove")
    public Result removeUser(Long id) {
        return success(serviceUserService.delete(id));
    }

    @GetMapping("/user/get")
    public Result getUser(Long serviceId) {
        return success(serviceUserService.getByServiceId(serviceId));
    }
    @GetMapping("/user/unadded/get")
    public Result getUnaddedUser(Integer pageNumber, Integer pageSize,Long serviceId) {
        return success(serviceUserService.getUnaddedUserByServiceId(pageNumber,pageSize,serviceId));
    }
}

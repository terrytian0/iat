package com.terry.iat.controller;

import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.ClientService;
import com.terry.iat.service.common.annotation.IgnoreLogin;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.common.enums.ClientStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 21:19
 * @Version 1.0 
 **/
@ResponseBody
@RestController
@RequestMapping(value = "/client")
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;
    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/23 21:32
     * @Param [client]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @IgnoreLogin
    @PostMapping(path = "/register")
    public Result register(String client){
        return success(clientService.register(client));
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/23 21:32
     * @Param [client, key]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @IgnoreLogin
    @PostMapping(path = "/heartbeat")
    public Result heartbeat(String client,String key){
        clientService.heartbeat(client,key);
        return success();
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 11:08
     * @Param [pageNumber, pageSize, searchText]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Result search(Integer pageNumber,Integer pageSize,String searchText){
        return success(clientService.getByKeys(pageNumber,pageSize,searchText));
    }

    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/2/24 11:24
     * @Param [id]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @PutMapping(path = "/enable")
    public Result enable(Long id){
        clientService.updateStatus(id, ClientStatus.NORMAL);
        return success();
    }
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/2/24 11:24
     * @Param [id]
     * @return com.terry.iat.service.common.bean.Result
     **/
    @PutMapping(path = "/disable")
    public Result disable(Long id){
        clientService.updateStatus(id, ClientStatus.DISABLE);
        return success();
    }


}

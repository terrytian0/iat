package com.terry.iat.controller;

import com.terry.iat.service.common.annotation.IgnoreLogin;
import com.terry.iat.service.common.bean.Result;
import com.terry.iat.controller.base.BaseController;
import com.terry.iat.service.UserService;
import com.terry.iat.service.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author terry
 * @Date: 2018/10/20 11:28
 * @Description:
 */


@ResponseBody
@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @IgnoreLogin
    @PostMapping(path = "/create")
    public Result create(@RequestBody @Valid UserVO userVO){
        return success(userService.create(userVO));
    }


    @PutMapping(path = "/update")
    public Result update(@RequestBody UserVO userVO){
        return success(userService.update(userVO));
    }

    @DeleteMapping(path = "/delete")
    public Result delete(Long userId){
        return success(userService.delete(userId));
    }

    @IgnoreLogin
    @PostMapping(path = "/login")
    public Result login(@RequestBody UserVO userVO){
        return success(userService.login(userVO));
    }


    @PostMapping(path = "/logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader("Authentication");
        userService.logout(token);
        return success();
    }

    @GetMapping(path = "/search")
    public Result search(Integer pageNumber,Integer pageSize,String searchText){
        return success(userService.getByKeys(pageNumber,pageSize,searchText));
    }
}

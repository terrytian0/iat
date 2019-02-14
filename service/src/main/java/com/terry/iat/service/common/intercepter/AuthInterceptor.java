/**
 *
 */
package com.terry.iat.service.common.intercepter;

import com.terry.iat.service.common.annotation.Auth;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.enums.Role;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Value("${session.timeout}")
    private Integer sessionTimeout;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if (auth != null) {
            String token = request.getHeader("Authentication");
            UserEntity userEntity = userService.getByToken(token);
            if(userEntity==null){
                throw new BusinessException(ResultCode.NEED_LOGIN);
            }
            Role userRole = Role.getRole(userEntity.getName());
            if(auth.role()!=userRole){
                throw new BusinessException(ResultCode.REFUSE_ACCESS.setMessage("你没有操作权限！"));
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        // to do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // to do nothing
    }

}

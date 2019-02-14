/**
 *
 */
package com.terry.iat.service.common.intercepter;

import com.terry.iat.service.common.annotation.IgnoreLogin;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.service.UserService;
import com.terry.iat.service.common.content.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Value("${session.timeout}")
    private Integer sessionTimeout;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(request.getRequestURI().contains("swagger-resources")||request.getRequestURI().contains("/v2/api-docs")){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IgnoreLogin ignoreLogin = handlerMethod.getMethodAnnotation(IgnoreLogin.class);
        if (ignoreLogin == null) {
            int invalid = sessionTimeout*60*1000;
            String token = request.getHeader("Authentication");
            if(StringUtils.isEmpty(token)){
                throw new BusinessException(ResultCode.NEED_LOGIN);
            }
            UserEntity userEntity = userService.getByToken(token);
            if(userEntity==null){
                throw new BusinessException(ResultCode.NEED_LOGIN);
            }
            long currentTime = System.currentTimeMillis();
            if((currentTime-userEntity.getLastOperateTime())>invalid){
                userService.clearInvalidSession(token);
                throw new BusinessException(ResultCode.NEED_LOGIN.setMessage("Session超时！"));
            }else{
                userEntity.setLastOperateTime(currentTime);
                userService.setSessionCache(userEntity);
                WebContext.setCurrentUser(userEntity);
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

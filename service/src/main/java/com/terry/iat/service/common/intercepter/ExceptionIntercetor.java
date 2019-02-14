/**
 * 
 */
package com.terry.iat.service.common.intercepter;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExceptionIntercetor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int status = HttpServletResponse.SC_OK;
        if (RequestMethod.POST.name().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
        response.setStatus(status);
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

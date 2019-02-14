package com.terry.iat.service.common.resolver;

import com.alibaba.fastjson.JSONObject;

import com.terry.iat.service.common.bean.Result;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.service.common.exception.InvalidParamException;
import com.terry.iat.service.common.exception.RefuseAccessException;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.SystemException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("exceptionResolver")
@Slf4j
public class WlHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final String ERROR_VIEW = "error";

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ResultCode resultCode = null;
        if (BusinessException.class.isAssignableFrom(ex.getClass())) {
            BusinessException bex = (BusinessException) ex;
            resultCode = bex.code();
            log.error("HandlerExceptionResolver catche the Business Exception", ex);
        } else if (InvalidParamException.class.isAssignableFrom(ex.getClass())) {
            InvalidParamException invalidParamException = (InvalidParamException) ex;
            // 参数异常
            resultCode = ResultCode.INVALID_PARAMS.setMessage(invalidParamException.getMessage());
            log.error("HandlerExceptionResolver catche the InvalidParam Exception", ex);
        } else if (MethodArgumentNotValidException.class.isAssignableFrom(ex.getClass())) {
            MethodArgumentNotValidException invalidParamException = (MethodArgumentNotValidException) ex;
            // 参数异常
            resultCode = ResultCode.INVALID_PARAMS.setMessage(invalidParamException.getMessage());
            log.error("HandlerExceptionResolver catche the InvalidParam Exception", ex);
        } else if (RefuseAccessException.class.isAssignableFrom(ex.getClass())) {
            // 拒绝访问
            resultCode = ResultCode.REFUSE_ACCESS;
            log.error("HandlerExceptionResolver catche the RefuseAccess Exception", ex);
        } else if (SystemException.class.isAssignableFrom(ex.getClass())) {
            // 系统异常
            String errMsg = ex.getMessage();
            if (errMsg == null || errMsg.equals("")) {
                errMsg = ex.getClass().getName();
            }
            errMsg = "请求" + request.getRequestURL() + "失败：" + errMsg;
            resultCode = ResultCode.SYSTEM_EXCEPTION.setMessage(errMsg);
            log.error("HandlerExceptionResolver catche the System Exception, ", ex);
        } else {
            String errMsg = ex.getMessage();
            if (errMsg == null || errMsg.equals("")) {
                errMsg = ex.getClass().getName();
            }
            errMsg = "请求" + request.getRequestURL() + "失败：" + errMsg;

            resultCode = ResultCode.SYSTEM_EXCEPTION.setMessage(errMsg);
            log.error("HandlerExceptionResolver catche the System Exception, ", ex);
        }

        Result result = Result.builder().code(resultCode.getCode()).message(resultCode.getMessage()).build();
        String requestHeader = request.getHeader("Accept");
        response.setCharacterEncoding("UTF-8");
        if (requestHeader == null || isAjaxHandler(handler)) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                StringBuffer responseSb = new StringBuffer();
                if (isJsonp(request)) {
                    String callback = request.getParameter("callback");
                    responseSb.append("(").append(callback).append(JSONObject.toJSONString(result)).append(")");
                } else {
                    responseSb.append(JSONObject.toJSONString(result));
                }
                response.getWriter().println(responseSb.toString());
            } catch (Exception e) {
                log.error("Response write exception", e);
            }
            return new ModelAndView();
        } else {
            // TODO 此处以后需要重新设计， 如果是业务异常返回404，如果是系统异常或为捕获的异常返回500
            response.setContentType("text/html;charset=UTF-8");
            request.setAttribute("errorCode", resultCode.getCode());
            request.setAttribute("errorMsg", resultCode.getMessage());
            return new ModelAndView(ERROR_VIEW);
        }
    }

    private boolean isAjaxHandler(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> beanType = handlerMethod.getBeanType();
            return beanType.getAnnotation(RestController.class) != null
                    || beanType.getAnnotation(ResponseBody.class) != null
                    || handlerMethod.getMethodAnnotation(ResponseBody.class) != null;
        }
        return false;
    }

    private boolean isJsonp(HttpServletRequest request) {
        String format = request.getParameter("format");
        if ("jsonp".equals(format)) {
            return true;
        }
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/jsonp")) {
            return true;
        }

        return false;
    }
}

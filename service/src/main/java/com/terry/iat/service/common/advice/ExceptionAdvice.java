package com.terry.iat.service.common.advice;

import com.terry.iat.service.common.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * @author terry
 */

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for (FieldError error : result.getFieldErrors()) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            sb.append(String.format("%s:%s; ", field, message));
        }
        log.error("参数验证失败!", sb.toString());
        return Result.builder().status(false).code("error").message(sb.toString()).build();
    }
}
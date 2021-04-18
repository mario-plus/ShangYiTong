package com.gree.commonUtil.handlers;

import com.gree.commonUtil.response.HttpResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ControllerAdvice 和 @RestControllerAdvice 功能一样
 * */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResponseResult error(Exception e){
        return HttpResponseResult.fail();
    }

}

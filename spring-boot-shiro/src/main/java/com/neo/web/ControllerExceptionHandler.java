package com.neo.web;

import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * spring-boot-shiro-ControllerExveptionHandler
 * mvc 框架异常统一处理
 *
 * @author yh
 * @since 0.0.1 2017-09-09 17:27
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({MultipartException.class, SpelEvaluationException.class})
    public ModelAndView handleMultipartException(MultipartException e, HttpServletRequest req) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("/uploadStatus");
        return mav;

    }
}

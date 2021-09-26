package com.peak.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/4 10:21
 */
@ControllerAdvice  //拦截所有Controller抛出的异常，对异常进行统一的处理
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class) //表示该方法可以处理所有异常
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        //打印异常信息
        logger.error("request URL : {} ,Exception: {}",request.getRequestURL(),e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null)
        {
            throw e;
        }
        //设置异常信息内容到页面
        ModelAndView mv =new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        //返回到页面
        mv.setViewName("error/error");
        return mv;

    }
}

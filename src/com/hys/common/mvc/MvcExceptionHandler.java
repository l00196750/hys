package com.hys.common.mvc;

import com.google.common.base.Throwables;
import com.hys.common.api.base.ApiErrorResponse;
import com.hys.common.api.base.BaseException;
import com.hys.common.utils.Loggers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MvcExceptionHandler {
    private Logger logger = Loggers.getLogger(MvcExceptionHandler.class);

    /**
     * 处理异常.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        logger.error("{}", e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(e.getClass().getSimpleName(), e.getMessage());

        List<StackTraceElement> lazyStackTrace = Throwables.lazyStackTrace(e);
        for (int i = 0; i < 5; i++) {
            apiErrorResponse.addStackTrace(lazyStackTrace.get(i).toString());
        }

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", apiErrorResponse);
        return modelAndView;
    }

    /**
     * 处理异常.
     */
    @ExceptionHandler(BaseException.class)
    public ModelAndView exceptionHandler(BaseException e) {
        logger.error("{}", e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(e.getClass().getSimpleName(), e.getMessage());

        List<StackTraceElement> lazyStackTrace = Throwables.lazyStackTrace(e);
        for (int i = 0; i < 5; i++) {
            apiErrorResponse.addStackTrace(lazyStackTrace.get(i).toString());
        }

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", apiErrorResponse);
        return modelAndView;
    }
}

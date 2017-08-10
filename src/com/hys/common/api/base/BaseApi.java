package com.hys.common.api.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.base.Throwables;

import com.hys.common.utils.Loggers;

public interface BaseApi {

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    default @ResponseBody ApiErrorResponse exceptionHandler(BaseException e) {
        Loggers.getLogger(getClass()).error("{}", e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(e.getClass().getSimpleName(), e.getMessage());

        java.util.List<StackTraceElement> lazyStackTrace = Throwables.lazyStackTrace(e);
        for (int i = 0; i < 5; i++) {
            apiErrorResponse.addStackTrace(lazyStackTrace.get(i).toString());
        }

        return apiErrorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    default @ResponseBody ApiErrorResponse exceptionHandler(Exception e) {
        Loggers.getLogger(getClass()).error("{}", e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(e.getClass().getSimpleName(), e.getMessage());

        java.util.List<StackTraceElement> lazyStackTrace = Throwables.lazyStackTrace(e);
        for (int i = 0; i < 5; i++) {
            apiErrorResponse.addStackTrace(lazyStackTrace.get(i).toString());
        }

        return apiErrorResponse;
    }
}

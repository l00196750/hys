package com.hys.common.api.base;

import com.google.common.base.Throwables;
import com.hys.common.utils.Loggers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface BaseApi {

    /**
     * 标准异常处理.
     */
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

    /**
     * 标准异常处理.
     */
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

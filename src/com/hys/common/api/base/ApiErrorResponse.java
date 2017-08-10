package com.hys.common.api.base;

import java.time.LocalDateTime;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

public class ApiErrorResponse extends ApiBaseResponse {

    private String serverTime;

    private List<String> stackTrace;

    public ApiErrorResponse(String errorCode, String errorMessage) {
        this.setOperateSuccess(false);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
        stackTrace = Lists.newLinkedList();
        serverTime = LocalDateTime.now().toString();
    }

    private ApiErrorResponse() {

    }

    public void addStackTrace(String stackTrace) {
        this.stackTrace.add(stackTrace);
    }

    public String getServerTime() {
        return serverTime;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("errorCode", getErrorCode()).add("errorMessage", getErrorMessage()).add("serverTime", serverTime)
            .add("stackTrace", stackTrace).toString();
    }

}

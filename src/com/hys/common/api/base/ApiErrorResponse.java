package com.hys.common.api.base;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorResponse extends ApiBaseResponse {

    private String serverTime;

    private List<String> stackTrace;

    /**
     * .
     */
    public ApiErrorResponse(String errorCode, String errorMessage) {
        this.setOperateSuccess(false);
        this.setErrorCode(errorCode);
        this.setErrorMessage(errorMessage);
        stackTrace = Lists.newLinkedList();
        serverTime = LocalDateTime.now().toString();
    }

    @SuppressWarnings("unused")
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
        return MoreObjects.toStringHelper(this).add("errorCode", getErrorCode()).add("errorMessage", getErrorMessage())
                .add("serverTime", serverTime).add("stackTrace", stackTrace).toString();
    }

}

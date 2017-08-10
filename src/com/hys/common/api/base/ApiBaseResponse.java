package com.hys.common.api.base;

import com.google.common.base.MoreObjects;

public class ApiBaseResponse {
    private boolean operateSuccess;

    private String errorCode;

    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isOperateSuccess() {
        return operateSuccess;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setOperateSuccess(boolean operateSuccess) {
        this.operateSuccess = operateSuccess;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("operateSuccess", operateSuccess).add("errorCode", errorCode).add("errorMessage", errorMessage)
            .toString();
    }
}

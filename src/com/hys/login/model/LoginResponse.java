package com.hys.login.model;

public class LoginResponse {
    private boolean loginSuccess;

    private String failureMessage;

    public String getFailureMessage() {
        return failureMessage;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

}

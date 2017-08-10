package com.hys.register.model;

import com.google.common.base.MoreObjects;

public class ResetPasswordRequest {

    private String userCode;

    private String newPassword;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("userCode", userCode).add("newPassword", newPassword).toString();
    }
}

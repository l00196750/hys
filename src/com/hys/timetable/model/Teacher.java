package com.hys.timetable.model;

import com.google.common.base.MoreObjects;

public class Teacher {
    private String userCode;

    private String userName;

    public String getUserCode() {
        return userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("userCode", userCode).add("userName", userName).toString();
    }
}

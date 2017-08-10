package com.hys.timetable.model;

import com.google.common.base.MoreObjects;

// 学员
public class Student {
    private String userCode;

    private String userName;

    /**
     * 初始科目
     */
    private String courseTeacherId;

    public String getCourseTeacherId() {
        return courseTeacherId;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setCourseTeacherId(String courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("userCode", userCode).add("userName", userName).add("courseTeacherId", courseTeacherId)
            .toString();
    }
}

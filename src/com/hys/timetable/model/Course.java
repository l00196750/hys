package com.hys.timetable.model;

import com.google.common.base.MoreObjects;

// 一门科程，比如语文，由很多的Lecture组成
public class Course {
    private String courseCode;

    private String courseName;

    private int courseDuration;

    public String getCourseCode() {
        return courseCode;
    }

    public int getCourseDuration() {
        return courseDuration;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseDuration(int courseDuration) {
        this.courseDuration = courseDuration;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("courseCode", courseCode).add("courseName", courseName).add("courseDuration", courseDuration)
            .toString();
    }

}

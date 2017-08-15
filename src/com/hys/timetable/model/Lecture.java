package com.hys.timetable.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.hys.common.utils.IdWorker;

// 具体的一次课
public class Lecture {
    /**
     * .
     */
    public static Lecture create(CourseTeacher courseTeacher, Student student, Period period) {
        Lecture lecture = new Lecture();
        lecture.lectureId = IdWorker.nextIdStr();
        lecture.courseTeacher = Preconditions.checkNotNull(courseTeacher);
        lecture.student = Preconditions.checkNotNull(student);
        lecture.period = Preconditions.checkNotNull(period);
        return lecture;
    }

    private String lectureId;

    private CourseTeacher courseTeacher;

    private Period period;

    // 已经参与的学生
    private Student student;

    // 是否已经开始
    private boolean started;

    public CourseTeacher getCourseTeacher() {
        return courseTeacher;
    }

    public Period getPeriod() {
        return period;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isStarted() {
        return started;
    }

    public void setCourseTeacher(CourseTeacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * .
     */
    public String toShortString() {
        return MoreObjects.toStringHelper(this).addValue(lectureId).addValue(student.getUserName())
                .addValue(courseTeacher.getTeacher().getUserName()).addValue(period.getStartWeek().getWeekOfYear())
                .addValue(period.getEndWeek().getWeekOfYear()).toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("lectureId", lectureId).add("courseTeacher", courseTeacher)
                .add("period", period).add("student", student).add("started", started).toString();
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }
}

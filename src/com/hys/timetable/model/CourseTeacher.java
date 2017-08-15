package com.hys.timetable.model;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public class CourseTeacher {
    private String id;

    private Course course;

    private Teacher teacher;

    private Amount amount;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CourseTeacher other = (CourseTeacher) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public Amount getAmount() {
        return amount;
    }

    public Course getCourse() {
        return course;
    }

    public String getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("course", course).add("teacher", teacher)
                .add("amount", amount).toString();
    }

}

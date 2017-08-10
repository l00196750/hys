package com.hys.timetable.dao;

import java.util.List;

import com.hys.timetable.model.Course;

public interface CourseMapper {
    List<Course> listCourse(String recruitPlanCode);
}

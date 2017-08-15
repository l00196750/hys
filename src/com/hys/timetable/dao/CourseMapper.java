package com.hys.timetable.dao;

import com.hys.timetable.model.Course;

import java.util.List;

public interface CourseMapper {
    List<Course> listCourse(String recruitPlanCode);
}

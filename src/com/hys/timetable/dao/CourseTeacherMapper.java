package com.hys.timetable.dao;

import java.util.List;
import java.util.Map;

public interface CourseTeacherMapper {
    List<Map<String, String>> listCourseTeacher(String recruitPlanCode);
}

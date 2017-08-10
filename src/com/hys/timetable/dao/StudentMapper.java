package com.hys.timetable.dao;

import java.util.List;

import com.hys.timetable.model.Student;

public interface StudentMapper {
    List<Student> listStudent(String recruitPlanCode);
}

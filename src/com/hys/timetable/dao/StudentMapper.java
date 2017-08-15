package com.hys.timetable.dao;

import com.hys.timetable.model.Student;

import java.util.List;

public interface StudentMapper {
    List<Student> listStudent(String recruitPlanCode);
}

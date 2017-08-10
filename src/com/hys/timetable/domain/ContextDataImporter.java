package com.hys.timetable.domain;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import com.hys.timetable.dao.CourseMapper;
import com.hys.timetable.dao.CourseTeacherMapper;
import com.hys.timetable.dao.StudentMapper;
import com.hys.timetable.model.Course;
import com.hys.timetable.model.CourseTeacher;
import com.hys.timetable.model.Student;
import com.hys.timetable.model.Teacher;

@Component
public class ContextDataImporter {

    @Autowired
    private WeekMgr weekMgr;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseTeacherMapper courseTeacherMapper;

    public void initData(RoundRobinContext context) {
        context.setWeekMgr(weekMgr);

        // 加载星期数据
        weekMgr.loadWeek(context.getBeginWeekOfYear(), context.getEndWeekOfYear());

        initStudent(context);
        initCourse(context);
        initStudyPlan(context);
        initTeachingMap(context);
    }

    private void initStudent(RoundRobinContext context) {
        List<Student> studentList = studentMapper.listStudent(context.getRecruitPlanCode());
        for (Student student : studentList) {
            context.addStudent(student);
        }
    }

    private void initCourse(RoundRobinContext context) {
        List<Course> courseList = courseMapper.listCourse(context.getRecruitPlanCode());
        for (Course course : courseList) {
            context.addCourse(course);
        }
    }

    // 学生所有要学习的课程
    private void initStudyPlan(RoundRobinContext context) {
        for (Student student : context.getAllStudent()) {
            for (Course course : context.getAllCourse()) {
                context.addStudyPlan(student.getUserCode(), course);
            }
        }
    }

    private void initTeachingMap(RoundRobinContext context) {
        List<Map<String, String>> courseTeacherList = courseTeacherMapper.listCourseTeacher(context.getRecruitPlanCode());
        for (Map<String, String> record : courseTeacherList) {
            String courseCode = record.get("COURSE_CODE");

            // 获取课程
            Course course = context.getCourse(courseCode);
            Preconditions.checkNotNull(course, "Course is null" + courseCode);

            String teacherUserCode = record.get("USER_CODE");
            Teacher teacher = context.getTeacher(teacherUserCode);
            if (teacher == null) {
                // 增加教员
                teacher = new Teacher();
                teacher.setUserCode(teacherUserCode);

                String teacherUserName = record.get("USER_NAME");
                teacher.setUserName(teacherUserName);
                context.addTeacher(teacher);
            }

            CourseTeacher courseTeacher = new CourseTeacher();
            courseTeacher.setCourse(course);
            courseTeacher.setTeacher(teacher);
            courseTeacher.setId(record.get("ID"));

            context.addCourseTeacher(courseTeacher);
        }
    }
}

package com.hys.timetable.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.hys.timetable.model.Course;
import com.hys.timetable.model.CourseTeacher;
import com.hys.timetable.model.Lecture;
import com.hys.timetable.model.Period;
import com.hys.timetable.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class SimpleRoundRobinV5 extends AbstractRoundRobin {

    List<Course> sortedCourseList;

    @Override
    public void round() {
        sortedCourseList = Lists.newArrayList(context.getAllCourse());
        Collections.shuffle(sortedCourseList);
        // Collections.sort(sortedCourseList, new Comparator<Course>() {
        // @Override
        // public int compare(Course o1, Course o2) {
        // return Ints.compare(o2.getCourseDuration(), o1.getCourseDuration());
        // }
        // });

        firstLecture();

        for (Long weekId : context.getWeekMgr().getAllWeekId()) {
            Collection<String> idleStudents = context.getIdleStudents(weekId);

            Collection<CourseTeacher> idleCourseTeacher = context.getIdleCourseTeacher(weekId);
            for (CourseTeacher courseTeacher : idleCourseTeacher) {
                if (courseTeacher.getAmount().isExistEveryDay()) {
                    for (String studentUserCode : idleStudents) {
                        assignStudentToCourseTeacher(context.getStudent(studentUserCode), courseTeacher, weekId);
                    }
                }
            }

            // 随机 分派一个学员
            for (String studentUserCode : idleStudents) {
                studentLoop(studentUserCode, weekId);
            }
        }

        context.dumpStudyPlanTable();
    }

    private boolean studentLoop(String studentUserCode, Long weekId) {
        Student student = context.getStudent(studentUserCode);

        // 按科目从大到小遍历
        // TODO: 未排序
        Collections.shuffle(sortedCourseList);
        for (Course course : sortedCourseList) {
            Collection<CourseTeacher> courseTeachers = context.getAllCourseTeacher(course.getCourseCode());
            for (CourseTeacher courseTeacher : courseTeachers) {
                if (assignStudentToCourseTeacher(student, courseTeacher, weekId)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * true:分派成功, false:分派失败.
     */
    private boolean assignStudentToCourseTeacher(Student student, CourseTeacher courseTeacher, Long weekId) {
        Course course = courseTeacher.getCourse();
        Set<String> courseStudentSet = context.getAllCourseStudent(course.getCourseCode());
        if (!courseStudentSet.contains(student.getUserCode())) {
            // 已学过此科目
            return false;
        }

        Optional<Period> period = context.getPeriod(weekId, course.getCourseDuration());
        if (!period.isPresent()) {
            // 总时间不够
            return false;
        }

        if (!context.isStudentIdle(student.getUserCode(), period.get())) {
            // 与学员其他课程时间重叠
            return false;
        }

        float avgAmountEveryDay = context.getAvgAmountEveryDay(courseTeacher.getId(), period.get());
        if (avgAmountEveryDay > courseTeacher.getAmount().getAvgAmountEveryDay()) {
            return false;
        }

        // 周期内是否会超过最大学员数
        if (context.isTooManyStudent(courseTeacher.getId(), period.get(),
                courseTeacher.getAmount().getMaxAmountIfExist())) {
            return false;
        }

        // 分派成功
        Lecture lecture = Lecture.create(courseTeacher, student, period.get());
        context.addLecture(lecture);
        return true;
    }

    private void firstLecture() {
        Multimap<String, Student> studentFirstLecture = HashMultimap.create();
        for (Student student : context.getAllStudent()) {
            studentFirstLecture.put(student.getCourseTeacherId(), student);
        }

        for (Course course : context.getAllCourse()) {
            Optional<Period> period = context.getPeriod(context.getBeginWeekOfYear(), course.getCourseDuration());
            Preconditions.checkArgument(period.isPresent(), "period.isPresent() week:" + context.getBeginWeekOfYear()
                    + ",duration " + course.getCourseDuration());

            Collection<CourseTeacher> courseTeachers = context.getAllCourseTeacher(course.getCourseCode());
            for (CourseTeacher courseTeacher : courseTeachers) {
                Collection<Student> students = studentFirstLecture.get(courseTeacher.getId());
                for (Student student : students) {
                    Lecture lecture = Lecture.create(courseTeacher, student, period.get());
                    context.addLecture(lecture);
                }
            }
        }
    }
}

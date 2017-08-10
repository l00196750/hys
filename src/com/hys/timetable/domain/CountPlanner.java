package com.hys.timetable.domain;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Floats;

import com.hys.timetable.model.Amount;
import com.hys.timetable.model.Course;
import com.hys.timetable.model.CourseTeacher;

@Component
public class CountPlanner {

    public void split(RoundRobinContext context) {
        // 总的星期数
        int totalWeekCount = context.getWeekMgr().getAllWeekId().size();

        for (Course course : context.getAllCourse()) {
            // 本科目最多周期数
            int totalPeriodCount = totalWeekCount / course.getCourseDuration();

            // 本科目的学员数
            int totalStudentCount = context.getAllCourseStudent(course.getCourseCode()).size();

            Collection<CourseTeacher> courseTeachers = context.getAllCourseTeacher(course.getCourseCode());
            Preconditions.checkArgument(!courseTeachers.isEmpty(), "CourseTeacher is empty. courseCode=" + course.getCourseCode());

            // 每个教员所带学员数量
            Multiset<Integer> studentCount4TearchMultiset = splitNumber(totalStudentCount, courseTeachers.size());

            for (CourseTeacher courseTeacher : courseTeachers) {
                // 学员数
                int studentCount = studentCount4TearchMultiset.iterator().next();
                studentCount4TearchMultiset.remove(studentCount);

                Amount amount = new Amount();
                amount.setCourseTeacherId(courseTeacher.getId());
                amount.setTotalPeriodAmount(totalPeriodCount);
                amount.setTotalStudentAmount(studentCount);
                amount.setExistEveryDay(studentCount > totalPeriodCount);

                float avg1 = 0.5f * (studentCount + 3) / totalPeriodCount;
                float avg2 = 0.5f * (studentCount + 3) / (totalPeriodCount * course.getCourseDuration());
                amount.setAvgAmountEveryDay(Floats.max(1.0f, avg1 + avg2));
                courseTeacher.setAmount(amount);
            }
        }
    }

    public Multiset<Integer> splitNumber(int totalAmount, int splitCount) {
        Preconditions.checkArgument(totalAmount > 0, "totalAmount > 0");
        Preconditions.checkArgument(splitCount > 0, "splitCount > 0");

        int average = totalAmount / splitCount;
        int overAverageCount = totalAmount - (average * splitCount);

        Multiset<Integer> result = HashMultiset.create();
        result.add(average, splitCount - overAverageCount);
        result.add(average + 1, overAverageCount);
        return result;
    }
}

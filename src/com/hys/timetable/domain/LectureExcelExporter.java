package com.hys.timetable.domain;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import com.hys.common.utils.ExcelWriter;
import com.hys.common.utils.Loggers;
import com.hys.timetable.model.CourseTeacher;
import com.hys.timetable.model.Lecture;

public class LectureExcelExporter {

    public void save(RoundRobinContext context) {
        Table<Integer, Integer, String> excelTable = HashBasedTable.create();
        Collection<Lecture> lectures = context.getAllLecture();

        int column = 2;
        Map<Long, Integer> weekId2ColumnMap = Maps.newHashMap();
        for (Long weekId : context.getWeekMgr().getAllWeekId()) {
            weekId2ColumnMap.put(weekId, column);
            excelTable.put(0, column, String.valueOf(weekId));
            column++;
        }

        excelTable.put(0, 0, "科室");
        excelTable.put(0, 1, "教师");

        Table<String, String, Integer> courseTeacher2RownumTable = HashBasedTable.create();

        for (Lecture lecture : lectures) {
            int rownum = 0;

            CourseTeacher courseTeacher = lecture.getCourseTeacher();

            String courseCode = courseTeacher.getCourse().getCourseCode();
            String teacherUserCode = courseTeacher.getTeacher().getUserCode();
            if (!courseTeacher2RownumTable.contains(courseCode, teacherUserCode)) {
                rownum = courseTeacher2RownumTable.size() + 1;
                courseTeacher2RownumTable.put(courseCode, teacherUserCode, rownum);
                excelTable.put(rownum, 0, courseTeacher.getCourse().getCourseName());
                excelTable.put(rownum, 1, courseTeacher.getTeacher().getUserName());
            }
            rownum = courseTeacher2RownumTable.get(courseCode, teacherUserCode);

            int columnStart = weekId2ColumnMap.get(lecture.getPeriod().getStartWeek().getWeekOfYear());
            for (int i = 0; i < courseTeacher.getCourse().getCourseDuration(); i++) {
                StringBuilder studentName = new StringBuilder();
                if (excelTable.contains(rownum, columnStart + i)) {
                    studentName.append(excelTable.get(rownum, columnStart + i)).append("\n");
                }
                studentName.append(lecture.getStudent().getUserName());

                excelTable.put(rownum, columnStart + i, studentName.toString());
            }

        }

        try {
            String fileName = new ExcelWriter().writer(excelTable);
            Loggers.log.debug(fileName);
        }
        catch (IOException e) {
            Loggers.log.error("{}", e);
        }
    }
}

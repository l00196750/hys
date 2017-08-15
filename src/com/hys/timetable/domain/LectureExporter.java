package com.hys.timetable.domain;

import com.google.common.collect.Maps;
import com.hys.timetable.dao.LectureMapper;
import com.hys.timetable.model.Lecture;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LectureExporter {

    @Autowired
    private LectureMapper lectureMapper;

    /**
     * 保存数据库.
     */
    @Transactional
    public void save(Collection<Lecture> lectures) {
        // TODO: 直接用Lecture，不使用hashmap

        Map<String, String> lectureMap = Maps.newHashMap();
        for (Lecture lecture : lectures) {
            lectureMap.put("COURSE_CODE", lecture.getCourseTeacher().getCourse().getCourseCode());
            lectureMap.put("TEACHER_CODE", lecture.getCourseTeacher().getTeacher().getUserCode());
            lectureMap.put("WEEK_ID", String.valueOf(lecture.getPeriod().getStartWeek().getWeekOfYear()));
            lectureMap.put("LECTURE_ID", lecture.getLectureId());
            lectureMap.put("STUDENT_CODE", lecture.getStudent().getUserCode());
            lectureMapper.insertLecture(lectureMap);
        }
    }
}

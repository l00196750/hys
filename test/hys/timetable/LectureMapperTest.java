package hys.timetable;

import com.google.common.collect.Maps;
import com.hys.timetable.dao.LectureMapper;

import hys.BaseSpringTest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

public class LectureMapperTest extends BaseSpringTest {

    @Autowired
    LectureMapper lectureMapper;

    @Test
    @Transactional
    public void t1() {
        Map<String, String> lectureMap = Maps.newHashMap();
        lectureMap.put("LECTURE_ID", "LECTURE_ID");
        lectureMap.put("COURSE_CODE", "COURSE_CODE");
        lectureMap.put("STUDENT_CODE", "STUDENT_CODE");
        lectureMap.put("TEACHER_CODE", "TEACHER_CODE");
        lectureMap.put("WEEK_ID", "23456");
        lectureMapper.insertLecture(lectureMap);
    }
}

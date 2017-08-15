package hys.timetable;

import com.hys.common.utils.Loggers;
import com.hys.timetable.domain.RandomRoundRobin;

import hys.BaseSpringTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class RandomRoundRobinTest extends BaseSpringTest {

    @Autowired
    RandomRoundRobin randomRoundRobin;

    @Test
    public void t1() {
        randomRoundRobin.init("r201701", 201732L, 202032L);
        randomRoundRobin.round();
        Loggers.log.debug("== {}", randomRoundRobin);
        Loggers.log.debug("===================");
    }

    // @Autowired
    // private StudentMapper studentMapper;
    //
    // @Test
    // public void t2() {
    // List<Student> students = studentMapper.listStudentByRecruitPlanCode("r201701");
    // for (Student student : students) {
    // Loggers.log.debug("{}", student);
    // }
    // }

    // @Autowired
    // private CourseTeacherMapper courseTeacherMapper;
    //
    // @Test
    // public void listCourseTeacher() {
    // List<Map<String, String>> listCourseTeacher =
    // courseTeacherMapper.listCourseTeacher("r201701");
    // for (Map<String, String> map : listCourseTeacher) {
    // Loggers.log.debug("{}", map);
    // }
    // }

}

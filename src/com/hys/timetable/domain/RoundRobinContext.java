package com.hys.timetable.domain;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import com.hys.timetable.model.Course;
import com.hys.timetable.model.CourseTeacher;
import com.hys.timetable.model.Lecture;
import com.hys.timetable.model.Period;
import com.hys.timetable.model.Student;
import com.hys.timetable.model.Teacher;
import com.hys.timetable.model.Week;

public class RoundRobinContext {

    /**
     * 招聘计划标识
     */
    // TODO: grade
    private String recruitPlanCode;

    /**
     * 培训开始周
     */
    private long beginWeekOfYear;

    /**
     * 培训结束周
     */
    private long endWeekOfYear;

    /**
     * 所有的学员 <br>
     * key=Student.userCode
     */
    private Map<String, Student> studentMap;

    /**
     * 所有的教员 <br>
     * key=Teacher.userCode <br>
     */
    private Map<String, Teacher> teacherMap;

    /**
     * key=Course.courseCode
     */
    private Map<String, Course> courseMap;

    /**
     * 学员还需要学习的课程 <br>
     * row=Student.userCode <br>
     * column=Course.courseCode <br>
     * value=Course.courseDuration
     */
    private Table<String, String, Integer> studyPlanTable;

    /**
     * 科目教员对应关系 <br>
     * row=Course.courseCode <br>
     * column=Teacher.userCode <br>
     * value=CourseTeacher 需要招聘的学员数
     */
    private Table<String, String, CourseTeacher> courseTeacherTable;

    /**
     * 课程表<br>
     * key=weekId <br>
     * 不要使用Table, 除非Lecture使用List存储
     */
    Multimap<Long, Lecture> lectureMultimap;

    /**
     * 星期管理
     */
    private WeekMgr weekMgr;

    private Logger logger;

    public RoundRobinContext(Logger logger) {
        this.logger = logger;

        courseMap = Maps.newHashMap();
        studentMap = Maps.newHashMap();
        teacherMap = Maps.newHashMap();

        lectureMultimap = HashMultimap.create();

        studyPlanTable = HashBasedTable.create();
        courseTeacherTable = HashBasedTable.create();
    }

    /**
     * 添加科目
     */
    public void addCourse(Course course) {
        this.courseMap.put(course.getCourseCode(), course);
    }

    /**
     * 添加教科目的教员
     */
    public void addCourseTeacher(CourseTeacher courseTeacher) {
        this.courseTeacherTable.put(courseTeacher.getCourse().getCourseCode(), courseTeacher.getTeacher().getUserCode(), courseTeacher);
    }

    /**
     * 增加课程计划
     */
    public void addLecture(Lecture lecture) {
        // 严格检查，防止意外
        Preconditions.checkNotNull(lecture.getLectureId(), "LectureId is null.");
        Preconditions.checkNotNull(lecture.getCourseTeacher(), "CourseTeacher is null.");
        Preconditions.checkNotNull(lecture.getStudent(), "Student is null.");
        Preconditions.checkNotNull(lecture.getPeriod(), "Period is null.");
        Preconditions.checkArgument(!lecture.getPeriod().getWeekIdSet().isEmpty(), "Period.weekIdSet is empty.");

        for (Long w : lecture.getPeriod().getWeekIdSet()) {
            this.lectureMultimap.put(w, lecture);
        }

        // 学员已学过此科目
        this.studyPlanTable.remove(lecture.getStudent().getUserCode(), lecture.getCourseTeacher().getCourse().getCourseCode());
    }

    /**
     * 添加学员
     */
    public void addStudent(Student student) {
        this.studentMap.put(student.getUserCode(), student);
    }

    /**
     * 增加学习计划
     */
    public void addStudyPlan(String studentUserCode, Course course) {
        this.studyPlanTable.put(studentUserCode, course.getCourseCode(), course.getCourseDuration());
    }

    /**
     * 增加教员
     */
    public void addTeacher(Teacher teacher) {
        this.teacherMap.put(teacher.getUserCode(), teacher);
    }

    public void dumpStudentLecture() {
        TreeMap<String, Lecture> lectureMap = new TreeMap<String, Lecture>();
        for (Lecture lecture : this.lectureMultimap.values()) {
            lectureMap.put(lecture.getLectureId(), lecture);
        }

        logger.debug("=================StudentLecture {}", lectureMap.size());
        logger.debug("{}, {}, {}, {}, {}", "Student", "Teacher", "CourseCode", "StartWeek", "EndWeek");
        for (Lecture lecture : lectureMap.values()) {
            logger.debug("{}, {}, {}, {}, {}", lecture.getStudent().getUserName(), lecture.getCourseTeacher().getTeacher().getUserName(), lecture
                .getCourseTeacher().getCourse().getCourseCode(), lecture.getPeriod().getStartWeek().getWeekOfYear(), lecture.getPeriod().getEndWeek()
                .getWeekOfYear());
        }
    }

    public void dumpStudyPlanTable() {
        logger.debug("=================StudyPlan {}", this.studyPlanTable.size());
        logger.debug("{} {} {} {}", "userCode", "userName", "courseCode", "courseName");
        Iterator<Cell<String, String, Integer>> iterator = this.studyPlanTable.cellSet().iterator();
        while (iterator.hasNext()) {
            Cell<String, String, Integer> next = iterator.next();
            String studentUserCode = next.getRowKey();
            String courseCode = next.getColumnKey();
            logger
                .debug("{} {} {} {}", studentUserCode, getStudent(studentUserCode).getUserName(), courseCode, getCourse(courseCode).getCourseName());
        }
    }

    /**
     * 查询所有的科目
     */
    public Collection<Course> getAllCourse() {
        return this.courseMap.values();
    }

    /**
     * 按科目查询未学习的学员
     */
    public Set<String> getAllCourseStudent(String courseCode) {
        return this.studyPlanTable.column(courseCode).keySet();
    }

    /**
     * 按科目查询所有教员
     */
    public Collection<CourseTeacher> getAllCourseTeacher(String courseCode) {
        return this.courseTeacherTable.row(courseCode).values();
    }

    public Collection<Lecture> getAllLecture() {
        TreeMap<String, Lecture> lectureMap = new TreeMap<String, Lecture>();
        for (Lecture lecture : this.lectureMultimap.values()) {
            lectureMap.put(lecture.getLectureId(), lecture);
        }
        return lectureMap.values();
    }

    /**
     * 查询所有的学员
     */
    public Collection<Student> getAllStudent() {
        return this.studentMap.values();
    }

    /**
     * 培训开始周
     */
    public long getBeginWeekOfYear() {
        return beginWeekOfYear;
    }

    /**
     * 查询科目的具体信息
     */
    public Course getCourse(String courseCode) {
        return this.courseMap.get(courseCode);
    }

    public CourseTeacher getCourseTeacher(String teacherUserCode, String courseCode) {
        return this.courseTeacherTable.get(courseCode, teacherUserCode);
    }

    /**
     * 培训结束周
     */
    public long getEndWeekOfYear() {
        return endWeekOfYear;
    }

    public Collection<CourseTeacher> getIdleCourseTeacher(long weekId) {
        Set<CourseTeacher> courseTeachers = Sets.newHashSet(this.courseTeacherTable.values());
        for (Lecture lecture : this.lectureMultimap.get(weekId)) {
            courseTeachers.remove(lecture.getCourseTeacher());
        }
        return courseTeachers;
    }

    public Collection<String> getIdleStudents(long weekId) {
        Set<String> studentCodeSet = Sets.newHashSet(this.studentMap.keySet());
        for (Lecture lecture : this.lectureMultimap.get(weekId)) {
            studentCodeSet.remove(lecture.getStudent().getUserCode());
        }
        return studentCodeSet;
    }

    public Optional<Period> getPeriod(long weekId, int courseDuration) {
        Period period = null;
        Optional<Week> beginWeek = weekMgr.getWeek(weekId);
        Optional<Week> endWeek = weekMgr.tailWeek(weekId, courseDuration);
        if (beginWeek.isPresent() && endWeek.isPresent()) {
            period = Period.create(beginWeek.get(), endWeek.get());
            period.addWeekIdSet(weekMgr.getWeekId(beginWeek.get().getWeekOfYear(), endWeek.get().getWeekOfYear()));
        }
        return Optional.ofNullable(period);
    }

    /**
     * 招聘计划
     */
    public String getRecruitPlanCode() {
        return recruitPlanCode;
    }

    /**
     * 查询学员
     */
    public Student getStudent(String studentUserCode) {
        return this.studentMap.get(studentUserCode);
    }

    /**
     * 查询教员
     */
    public Teacher getTeacher(String teacherUserCode) {
        return this.teacherMap.get(teacherUserCode);
    }

    public WeekMgr getWeekMgr() {
        return weekMgr;
    }

    public boolean isStudentIdle(String studentUserCode, Period period) {
        for (Long w : period.getWeekIdSet()) {
            Collection<Lecture> lectures = this.lectureMultimap.get(w);
            for (Lecture lecture : lectures) {
                if (studentUserCode.equals(lecture.getStudent().getUserCode())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 课程人员数是是否超过最大值 <br>
     * true-超过最大值，false-未超过最大值
     */
    public boolean isTooManyStudent(String courseTeacherId, Period period, int maxStudentCount) {
        for (Long w : period.getWeekIdSet()) {
            int totalStudentCount = 0;
            Collection<Lecture> lectures = this.lectureMultimap.get(w);
            for (Lecture lecture : lectures) {
                if (courseTeacherId.equals(lecture.getCourseTeacher().getId())) {
                    totalStudentCount++;
                }

                if (totalStudentCount > maxStudentCount - 1) {
                    // 会有一个新加的没员
                    return true;
                }
            }
        }
        return false;
    }

    public float getAvgAmountEveryDay(String courseTeacherId, Period period) {
        Set<Long> weekIdSet = period.getWeekIdSet();
        Preconditions.checkArgument(!weekIdSet.isEmpty(), "weekIdSet.isEmpty()");

        int sumStudent = 0; // 还会排一个学员
        for (Long weekId : weekIdSet) {
            Collection<Lecture> lectures = this.lectureMultimap.get(weekId);
            for (Lecture lecture : lectures) {
                if (courseTeacherId.equals(lecture.getCourseTeacher().getId())) {
                    sumStudent++;
                }
            }
        }

        return 1.0f * sumStudent / weekIdSet.size();
    }

    public void setBeginWeekOfYear(long beginWeekOfYear) {
        this.beginWeekOfYear = beginWeekOfYear;
    }

    public void setEndWeekOfYear(long endWeekOfYear) {
        this.endWeekOfYear = endWeekOfYear;
    }

    public void setRecruitPlanCode(String recruitPlanCode) {
        this.recruitPlanCode = recruitPlanCode;
    }

    public void setWeekMgr(WeekMgr weekMgr) {
        this.weekMgr = weekMgr;
    }
}

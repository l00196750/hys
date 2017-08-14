package hys.timetable;

import hys.BaseSpringTest;

import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.hys.common.utils.Loggers;
import com.hys.timetable.domain.WeekMgr;
import com.hys.timetable.model.Week;

public class WeekMgrTest extends BaseSpringTest {

    @Autowired
    WeekMgr weekMgr;

    @Test
    public void getWeek() {
        weekMgr.loadWeek(201701L, 201710L);
        Optional<Week> nextWeek = weekMgr.nextWeek(201701L, 3);
        Optional<Week> tailWeek = weekMgr.tailWeek(201701L, 3);
        Loggers.log.debug("nextWeek {}", nextWeek.get());
        Loggers.log.debug("tailWeek {}", tailWeek.get());
    }

    @Test
    public void getWeekId() {
        weekMgr.loadWeek(201701L, 201710L);
        Set<Long> weekId = weekMgr.getWeekId(201701L, 201701L);
        Loggers.log.debug("{}", weekId);
    }

    @Test
    public void t1() {
        TreeMap<Integer, String> studentDuration = new TreeMap<Integer, String>((o1, o2) -> o2.compareTo(o1));
        studentDuration.put(1, "t1");
        studentDuration.put(2, "t2");
        studentDuration.put(3, "t3");
        studentDuration.put(4, "t4");

        Loggers.log.debug("1 {}", studentDuration.keySet());
        Loggers.log.debug("2 {}", studentDuration.descendingKeySet());
        Loggers.log.debug("3 {}", studentDuration.values());
        Loggers.log.debug("4 {}", studentDuration.descendingMap().values());
    }
}

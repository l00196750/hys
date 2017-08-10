package hys.timetable;

import hys.BaseSpringTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hys.common.utils.Loggers;
import com.hys.timetable.domain.SimpleRoundRobinV5;

public class SimpleRoundRobinTest extends BaseSpringTest {

    @Autowired
    private SimpleRoundRobinV5 roundRobin;

    @Test
    public void simpleRoundRobin() {
        roundRobin.init("r201701", 201732L, 201912L);
        roundRobin.round();
        roundRobin.save();
        Loggers.log.debug("== {}", roundRobin);
        Loggers.log.debug("===================");
    }

}

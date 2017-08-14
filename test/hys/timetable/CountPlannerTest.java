package hys.timetable;

import org.testng.annotations.Test;

import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;

import com.hys.common.utils.Loggers;
import com.hys.timetable.domain.CountPlanner;

public class CountPlannerTest {

    @Test
    public void splitNumber() {
        CountPlanner countPlanner = new CountPlanner();
        Multiset<Integer> splitNumber = countPlanner.splitNumber(13, 1);

        Loggers.log.debug("{}", Ints.min(Ints.toArray(splitNumber)));
        Loggers.log.debug("{}", Ints.max(Ints.toArray(splitNumber)));
        Loggers.log.debug("{}", splitNumber);
    }
}

package hys.timetable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.TreeMap;

import org.junit.Test;
import org.slf4j.Logger;

import com.google.common.collect.Lists;

import com.hys.common.utils.Loggers;
import com.hys.timetable.model.Week;

public class WeekOfYearTest {
    Logger logger = Loggers.getLogger(WeekOfYearTest.class);

    @Test
    public void t1() {
        LocalDate localDate = LocalDate.parse("2017-08-03");
        LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        // LocalDate nextSunday = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
        logger.debug(nextSunday.toString());
        logger.debug("{}", weekNumber);

        TemporalField fieldISO = WeekFields.of(Locale.CHINESE).dayOfWeek();
        logger.debug("{}", localDate.with(fieldISO, DayOfWeek.MONDAY.getValue()));

        TreeMap<String, Week> xx;
        Lists.newLinkedList();
    }
}

package com.hys.timetable.domain;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import com.hys.timetable.dao.WeekMapper;
import com.hys.timetable.model.Week;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WeekMgr {

    @Autowired
    private WeekMapper weekMapper;

    private TreeMap<Long, Week> weekMap;

    public Set<Long> getAllWeekId() {
        return this.weekMap.keySet();
    }

    public Optional<Week> getWeek(long weekOfYear) {
        return Optional.ofNullable(this.weekMap.get(weekOfYear));
    }

    /**
     * nextWeek(201701, 1) -> 201702 <br>
     * nextWeek(201702, 1) -> 201703
     */
    public Optional<Week> nextWeek(long fromWeek, int step) {
        Preconditions.checkArgument(this.weekMap.containsKey(fromWeek), "contains week " + fromWeek);
        Preconditions.checkArgument(step > 0, "step > 0");

        return getWeek(fromWeek, step, false);
    }

    /**
     * tailWeek(201701, 1) -> 201701 <br>
     * tailWeek(201701, 2) -> 201702
     */
    public Optional<Week> tailWeek(long fromWeek, int duration) {
        Preconditions.checkArgument(this.weekMap.containsKey(fromWeek), "contains week " + fromWeek);
        Preconditions.checkArgument(duration > 0, "duration > 0");

        return getWeek(fromWeek, duration, true);
    }

    public Set<Long> getWeekId(long fromWeek, long toWeek) {
        Preconditions.checkArgument(this.weekMap.containsKey(fromWeek), "contains week " + fromWeek);
        Preconditions.checkArgument(this.weekMap.containsKey(toWeek), "contains week " + toWeek);

        Set<Long> keySet = this.weekMap.subMap(fromWeek, true, toWeek, true).keySet();
        return keySet;
    }

    private Optional<Week> getWeek(long fromWeek, int step, boolean fromWeekInclusive) {
        Week week = null;
        SortedMap<Long, Week> tailMap = this.weekMap.tailMap(fromWeek, fromWeekInclusive);
        if (!tailMap.isEmpty()) {
            Iterator<Entry<Long, Week>> iterator = tailMap.entrySet().iterator();
            while (iterator.hasNext() && step > 1) {
                iterator.next();
                step--;
            }

            if (step == 1 && iterator.hasNext()) {
                week = iterator.next().getValue();
            }
        }

        return Optional.ofNullable(week);
    }

    public void loadWeek(long beginWeekOfYear, long endWeekOfYear) {
        weekMap = new TreeMap<Long, Week>();

        List<Week> weekList = weekMapper.listWeek(beginWeekOfYear, endWeekOfYear);
        for (Week week : weekList) {
            weekMap.put(week.getWeekOfYear(), week);
        }
    }
}

package com.hys.timetable.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

// 时间段
public class Period {
    /**
     * .
     */
    public static Period create(Week startWeek, Week endWeek) {
        Period period = new Period();
        period.startWeek = Preconditions.checkNotNull(startWeek);
        period.endWeek = Preconditions.checkNotNull(endWeek);
        return period;
    }

    private Week startWeek;

    private Week endWeek;

    private Set<Long> weekIdSet = Sets.newLinkedHashSet();

    private Period() {

    }

    public void addWeekIdSet(Collection<Long> weekIdSet) {
        this.weekIdSet.addAll(weekIdSet);
    }

    public Week getEndWeek() {
        return endWeek;
    }

    public Week getStartWeek() {
        return startWeek;
    }

    public Set<Long> getWeekIdSet() {
        return weekIdSet;
    }

    /**
     * 是否冲突.
     */
    public boolean isConflict(Period otherPeriod) {
        if (this.startWeek.getWeekOfYear() >= otherPeriod.getStartWeek().getWeekOfYear()
                && this.startWeek.getWeekOfYear() <= otherPeriod.getEndWeek().getWeekOfYear()) {
            return true;
        }

        if (this.endWeek.getWeekOfYear() >= otherPeriod.startWeek.getWeekOfYear()
                && this.endWeek.getWeekOfYear() <= otherPeriod.endWeek.getWeekOfYear()) {
            return true;
        }

        return false;
    }

    public void setEndWeek(Week endWeek) {
        this.endWeek = endWeek;
    }

    public void setStartWeek(Week startWeek) {
        this.startWeek = startWeek;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("startWeek", startWeek).add("endWeek", endWeek).toString();
    }
}

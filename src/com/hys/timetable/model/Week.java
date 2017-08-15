package com.hys.timetable.model;

import com.google.common.base.MoreObjects;

public class Week {
    private long weekOfYear;

    private String beginDate;

    private String endDate;

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public long getWeekOfYear() {
        return weekOfYear;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setWeekOfYear(long weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("weekOfYear", weekOfYear).add("beginDate", beginDate)
                .add("endDate", endDate).toString();
    }
}

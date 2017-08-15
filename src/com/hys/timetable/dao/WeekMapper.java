package com.hys.timetable.dao;

import com.hys.timetable.model.Week;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WeekMapper {
    List<Week> listWeek(@Param("beginWeekOfYear") long beginWeekOfYear, @Param("endWeekOfYear") long endWeekOfYear);
}

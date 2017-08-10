package com.hys.timetable.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hys.timetable.model.Week;

public interface WeekMapper {
    List<Week> listWeek(@Param("beginWeekOfYear") long beginWeekOfYear, @Param("endWeekOfYear") long endWeekOfYear);
}

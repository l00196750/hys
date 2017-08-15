package com.hys.timetable.domain;

import com.hys.common.utils.Loggers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRoundRobin {

    protected Logger logger = Loggers.getLogger(getClass());

    protected RoundRobinContext context;

    @Autowired
    private ContextDataImporter contextDataImporter;

    // @Autowired
    // private LectureExporter lectureExporter;

    @Autowired
    private CountPlanner countPlanner;

    public abstract void round();

    /**
     * 初始化.
     */
    public void init(String recruitPlanCode, long beginWeekOfYear, long endWeekOfYear) {
        context = new RoundRobinContext(logger);
        context.setBeginWeekOfYear(beginWeekOfYear);
        context.setEndWeekOfYear(endWeekOfYear);
        context.setRecruitPlanCode(recruitPlanCode);

        // 加载数据
        contextDataImporter.initData(context);

        // 规划每个教员每个周期学员数量
        countPlanner.split(context);
    }

    public void save() {
        // lectureExporter.save(context.getAllLecture());
        new LectureExcelExporter().save(context);
    }

}

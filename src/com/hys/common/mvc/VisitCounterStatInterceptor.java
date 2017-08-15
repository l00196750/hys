package com.hys.common.mvc;

import com.google.common.collect.Lists;
import com.hys.common.stats.LogSnapshotExporter;
import com.hys.common.stats.StatsCounter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class VisitCounterStatInterceptor implements HandlerInterceptor {

    private static StatsCounter statsCounter =
            StatsCounter.createStarted("VisitCounterStatInterceptor", Lists.newArrayList(new LogSnapshotExporter()));

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex)
            throws Exception {}

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mv)
            throws Exception {}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        statsCounter.recordSuccess(request.getRequestURI(), 0);
        return true;
    }
}

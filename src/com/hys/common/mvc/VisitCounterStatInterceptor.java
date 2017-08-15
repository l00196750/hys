package com.hys.common.mvc;

import com.google.common.collect.Lists;
import com.hys.common.stats.LogSnapshotExporter;
import com.hys.common.stats.StatsCounter;
import com.hys.common.utils.Loggers;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class VisitCounterStatInterceptor implements HandlerInterceptor {

    private Logger logger = Loggers.getLogger(VisitCounterStatInterceptor.class);

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
        logger.debug("request.getRequestURI()", request.getRequestURI());
        logger.debug("request.getParameterMap() {}", request.getParameterMap());
        logger.debug("request.getClass().getName() {}", request.getClass().getName());

        // request = new ContentCachingRequestWrapper(request);
        // ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        // ContentCachingResponseWrapper wrappedResponse = new
        // ContentCachingResponseWrapper(response);
        // wrappedResponse.getResponse();
        // wrappedResponse.copyBodyToResponse();

        // Servlet3SecurityContextHolderAwareRequestWrapper x;

        // org.springframework.security.web.servletapi.
        // HttpServlet3RequestFactory.Servlet3SecurityContextHolderAwareRequestWrapper
        // x;
        // HttpServlet3RequestFactory;

        Map<String, String[]> map = request.getParameterMap();
        for (String name : map.keySet()) {
            String[] values = map.get(name);
            System.out.println(name + "=" + Arrays.toString(values));
            logger.debug(name + "=" + Arrays.toString(values));
        }
        logger.debug("{}", map);

        // logger.debug("{}", new MyRequestWrapper(request).getBody());

        statsCounter.recordSuccess(request.getRequestURI(), 0);
        return true;
    }
}

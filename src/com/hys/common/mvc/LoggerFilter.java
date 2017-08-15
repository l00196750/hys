package com.hys.common.mvc;

import com.hys.common.utils.Loggers;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class LoggerFilter implements Filter {

    private Logger logger = Loggers.getLogger(LoggerFilter.class);

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // logger.debug("========request.getRequestURI()", request.getRequestURI());

        if (request instanceof HttpServletRequest) {
            ContentCachingRequestWrapper requestWrapper =
                    new ContentCachingRequestWrapper((HttpServletRequest) request);
            logger.debug("========request.getParameterMap() {}", requestWrapper.getParameterMap());
            logger.debug("========request.getClass().getName() {}", requestWrapper.getClass().getName());
            logger.debug("{}", new String(requestWrapper.getContentAsByteArray()));
            request = requestWrapper;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

}

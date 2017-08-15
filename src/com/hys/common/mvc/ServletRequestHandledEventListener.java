package com.hys.common.mvc;

import com.hys.common.utils.Loggers;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class ServletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent> {

    private static Logger logger = Loggers.getLogger(ServletRequestHandledEventListener.class);

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        logger.debug("========================");
        logger.debug("{}", event);
    }

}

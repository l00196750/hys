package com.hys.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggers {

    public static Logger log = getLogger(Loggers.class);

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz.getName());
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }
}

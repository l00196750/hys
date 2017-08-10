package com.hys.common.api.base;

public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 0L;

    public BaseException(String msg) {
        super(msg);
    }

    public BaseException(String msg, Throwable t) {
        super(msg, t);
    }
}

package com.hys.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hys.common.api.base.BaseException;

@Controller
@RequestMapping("/auth/demo")
public class ControllerAdviceDemo {

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String staffInfo() {
        throw (new AdviceException("haha"));
    }

    public class AdviceException extends BaseException {

        private static final long serialVersionUID = 1L;

        public AdviceException(String msg) {
            super(msg);
        }
    }
}

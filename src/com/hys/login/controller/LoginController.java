package com.hys.login.controller;

import com.google.common.base.Throwables;
import com.hys.common.api.base.BaseApi;
import com.hys.common.utils.Loggers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController implements BaseApi {
    Logger logger = Loggers.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request) {
        // 不应该调用到此
        logger.debug(Throwables.getStackTraceAsString(new Throwable()));
    }

    /**
     * 退出.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/index.html";
    }
}

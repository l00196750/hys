package com.hys.login.domain;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.hys.common.utils.HttpServletResponses;
import com.hys.common.utils.Loggers;
import com.hys.login.model.LoginResponse;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    private Logger logger = Loggers.getLogger(LoginFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
        throws IOException, ServletException {
        logger.debug("{} {} ", exception.getClass().getSimpleName(), exception.getMessage());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLoginSuccess(false);
        loginResponse.setFailureMessage(exception.getClass().getSimpleName());

        HttpServletResponses.writeJson(response, loginResponse);
    }

}

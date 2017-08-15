package com.hys.login.domain;

import com.hys.common.utils.HttpServletResponses;
import com.hys.login.model.LoginResponse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLoginSuccess(true);

        HttpServletResponses.writeJson(response, loginResponse);
    }

}

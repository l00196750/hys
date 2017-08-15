package com.hys.login.domain;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.hys.common.utils.Loggers;
import com.hys.login.service.UserDetailService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailService userDetailsService;

    private Logger logger = Loggers.getLogger(SecurityProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        logger.debug(Throwables.getStackTraceAsString(new Throwable()));
        String inputUserCode = token.getName();
        String inputPassword = Strings.nullToEmpty(token.getCredentials().toString());
        logger.debug("authenticate usercode {} pwd {}", inputUserCode, inputPassword);

        if (Strings.isNullOrEmpty(inputUserCode)) {
            throw new UsernameNotFoundException("username is null or empty");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(inputUserCode);
        if (userDetails == null) {
            throw new UsernameNotFoundException("username: " + inputUserCode);
        }

        if (!inputPassword.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("username: " + inputUserCode);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}

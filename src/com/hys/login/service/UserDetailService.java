package com.hys.login.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hys.common.utils.Loggers;
import com.hys.login.dao.LoginUserMapper;
import com.hys.login.model.LoginUserDetails;

@Service
public class UserDetailService {
    private Logger logger = Loggers.getLogger(UserDetailService.class);

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Cacheable(value = "loginUserDetailsCache", sync = true)
    public UserDetails loadUserByUsername(String userCode) {
        logger.debug("loadUserByUsername {}", userCode);
        LoginUserDetails loginUser = loginUserMapper.loadUserByUserCode(userCode);
        logger.debug("xxxx {}", loginUser);
        return loginUser;
    }
}

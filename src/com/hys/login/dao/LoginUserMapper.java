package com.hys.login.dao;

import com.hys.login.model.LoginUserDetails;

public interface LoginUserMapper {
    LoginUserDetails loadUserByUserCode(String userCode);
}

package hys.login.dao;

import hys.BaseSpringTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import com.hys.common.utils.Loggers;
import com.hys.login.dao.LoginUserMapper;
import com.hys.login.model.LoginUserDetails;

public class LoginUserMapperTest extends BaseSpringTest {

    @Autowired
    LoginUserMapper loginUserMapper;

    @Test
    public void insert() {
        LoginUserDetails loginUserDetails = loginUserMapper.loadUserByUserCode("lyq");
        Preconditions.checkNotNull(loginUserDetails);
        Loggers.log.debug("== {}", loginUserDetails);
    }
}

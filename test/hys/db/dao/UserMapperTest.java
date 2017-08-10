package hys.db.dao;

import hys.BaseSpringTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hys.common.utils.Loggers;
import com.hys.db.dao.UserMapper;
import com.hys.db.model.DbUser;
import com.hys.db.service.DbUserService;

public class UserMapperTest extends BaseSpringTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DbUserService dbUserService;

    @Test
    public void insert() {
        DbUser user = userMapper.getUser();
        Loggers.log.debug("{}", userMapper);
        Loggers.log.debug("{}", dbUserService);
        Loggers.log.debug("{} {}", user.getUserName(), user.getPwd());
    }
}

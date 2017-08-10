package com.hys.db.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hys.db.dao.UserMapper;
import com.hys.db.model.DbUser;

@Service
public class DbUserService {

    private Logger log = LoggerFactory.getLogger(DbUserService.class);

    @Autowired
    private UserMapper userMapper;

    public DbUser getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("==========SecurityContextHolder.getContext().getAuthentication().getName() {}", username);
        DbUser dbUser = userMapper.getUser();
        log.debug("{}", dbUser);
        return dbUser;
    }

    @Cacheable(value = "getUserByName", sync = true)
    public DbUser getUser(String userName) {
        log.debug("==getUser {}", userName);
        DbUser dbUser = new DbUser();
        dbUser.setUserName(userName);
        dbUser.setPwd("pwd" + userName);
        return dbUser;
    }

    public List<DbUser> getAllUser() {
        // PageHelper.startPage(5, 10);
        // List<DbUser> allUser = userMapper.getAllUser();

        RowBounds rowBounds = new RowBounds(1, 10);
        List<DbUser> allUser = userMapper.getAllUserWithRowBounds(rowBounds);
        for (DbUser user : allUser) {
            log.debug("{} {}", user.getUserName(), user.getPwd());
        }
        return allUser;
    }

    @Transactional
    public void t1() {
        log.debug("begin userMapper.insertTask");
        userMapper.insertTask("t4");
        // userMapper.insertTask("t0123456789t0123456789t0123456789t0123456789t0123456789");
        log.debug("end userMapper.insertTask");
    }
}

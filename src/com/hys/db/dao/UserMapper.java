package com.hys.db.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.hys.db.model.DbUser;

public interface UserMapper {
    public DbUser getUser();

    public List<DbUser> getAllUser();

    public List<DbUser> getAllUserWithRowBounds(RowBounds rowBounds);

    public void insertTask(String tid);
}

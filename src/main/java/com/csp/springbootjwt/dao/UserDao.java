package com.csp.springbootjwt.dao;

import com.csp.springbootjwt.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public User login(User user);
}

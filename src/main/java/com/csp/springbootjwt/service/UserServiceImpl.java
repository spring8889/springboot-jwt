package com.csp.springbootjwt.service;

import com.csp.springbootjwt.dao.UserDao;
import com.csp.springbootjwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        User userDB = userDao.login(user);
        if (userDB!=null){
            return userDB;
        }
        throw new RuntimeException("登录失败");

    }
}

package com.bdmer.wxapp.service.impl;

import com.bdmer.wxapp.dao.UserMapper;
import com.bdmer.wxapp.model.User;
import com.bdmer.wxapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        int i = userMapper.insert(user);
        System.out.println(i);
    }
}

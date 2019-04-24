package com.bdmer.wxapp.service.impl;

import com.bdmer.wxapp.dao.UserDao;
import com.bdmer.wxapp.model.UserEntity;
import com.bdmer.wxapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userMapper;

    @Override
    public void insertUser(UserEntity user) {
        int i = userMapper.insert(user);
        System.out.println(i);
    }
}

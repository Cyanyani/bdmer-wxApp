package com.bdmer.wxapp.dao;

import com.bdmer.wxapp.model.TaskEntity;

public interface TaskDao {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEntity record);

    int insertSelective(TaskEntity record);

    TaskEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskEntity record);

    int updateByPrimaryKey(TaskEntity record);
}
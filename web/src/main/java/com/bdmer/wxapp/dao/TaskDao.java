package com.bdmer.wxapp.dao;

import com.bdmer.wxapp.model.TaskEntity;
import org.apache.ibatis.annotations.Param;

public interface TaskDao {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEntity record);

    int insertSelective(TaskEntity record);

    TaskEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskEntity record);

    int updateByPrimaryKey(TaskEntity record);

    int updateTaskStatus(@Param("taskId") Long taskId, @Param("taskStatus") String taskStatus);

    int updateTaskDoUid(@Param("taskId") Long taskId, @Param("doUid") Long doUid);

}
package com.bdmer.wxapp.dao;

import com.bdmer.wxapp.dto.request.QueryTaskListDTO;
import com.bdmer.wxapp.dto.response.TaskCardDTO;
import com.bdmer.wxapp.model.TaskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskDao {
    int deleteByPrimaryKey(Long id);

    int insert(TaskEntity record);

    int insertSelective(TaskEntity record);

    TaskEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TaskEntity record);

    int updateByPrimaryKey(TaskEntity record);

    int updateTaskStatus(@Param("taskId") Long taskId, @Param("taskStatus") String taskStatus);

    int updateTaskDoUid(@Param("taskId") Long taskId, @Param("doUid") Long doUid);

    int updateGivePoint(@Param("taskId") Long taskId, @Param("givePoint") Integer givePoint);

    List<TaskCardDTO> getTaskList(QueryTaskListDTO queryTaskListDTO);

    List<TaskCardDTO> getTaskListByUid(@Param("lat")Double lat, @Param("lng") Double lng, @Param("index") Integer index, @Param("size") Integer size, @Param("uid") Long uid, @Param("status1") String status1, @Param("status2") String status2);

    List<TaskCardDTO> getTaskListByDoUid(@Param("lat")Double lat, @Param("lng") Double lng, @Param("index") Integer index, @Param("size") Integer size, @Param("doUid") Long doUid, @Param("status1") String status1, @Param("status2") String status2);

}
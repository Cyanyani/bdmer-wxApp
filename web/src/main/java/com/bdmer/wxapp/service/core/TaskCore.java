package com.bdmer.wxapp.service.core;

import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.enums.TaskStatusEnum;
import com.bdmer.wxapp.common.enums.TaskTypeEnum;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.TimeUtil;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.common.tool.WxUserHolder;
import com.bdmer.wxapp.dao.TaskDao;
import com.bdmer.wxapp.dto.request.CreateTaskDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.dto.response.TaskDetailDTO;
import com.bdmer.wxapp.model.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 任务管理 - 核心类
 *
 * @since 2019-05-07
 * @author gongdl
 */
@Component
@Transactional
public class TaskCore {
    private static final Logger LOG = LoggerFactory.getLogger(TaskCore.class);

    @Autowired
    private TaskDao taskDao;

    /**
     * 插入任务
     *
     * @param createTaskDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> insertTask(CreateTaskDTO createTaskDTO) throws Exception{

        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setUid(createTaskDTO.getUid());
        taskEntity.setName(createTaskDTO.getName());
        taskEntity.setAvatarUrl(createTaskDTO.getAvatarUrl());
        taskEntity.setTitle(createTaskDTO.getTitle());
        taskEntity.setContent(createTaskDTO.getContent());
        taskEntity.setPictrues(createTaskDTO.getPictrues());
        taskEntity.setCreatstamp(TimeUtil.getTimeStamp());
        // 将时间字符串变成时间戳
        Long endStamp = TimeUtil.getTimeStampByFormat("yyyy-MM-dd HH:mm:ss", createTaskDTO.getEndDate() + " " + createTaskDTO.getEndTime() + ":00");
        taskEntity.setEndstamp(endStamp);

        taskEntity.setLat(createTaskDTO.getLat());
        taskEntity.setLng(createTaskDTO.getLng());
        taskEntity.setLocaleName(createTaskDTO.getLocaleName());
        taskEntity.setTelNumber(createTaskDTO.getTelNumber());
        taskEntity.setReward(createTaskDTO.getReward());

        // 转化type
        String taskType = TaskTypeEnum.TYPE_IS_OTHER.getCode();
        switch (createTaskDTO.getType()){
            case "通知公告": taskType = TaskTypeEnum.TYPE_IS_NOTICE.getCode();;break;
            case "外卖快递": taskType = TaskTypeEnum.TYPE_IS_WAIKUAI.getCode();;break;
            case "失物招领": taskType = TaskTypeEnum.TYPE_IS_LOSTF.getCode();;break;
            case "其他": taskType = TaskTypeEnum.TYPE_IS_OTHER.getCode();;break;
        }

        taskEntity.setType(taskType);
        taskEntity.setStatus(TaskStatusEnum.STATUS_IS_FINDING.getCode());


        Integer result = taskDao.insertSelective(taskEntity);

        return B.success(taskEntity.getId());
    }

    /**
     * 获取任务详情
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getTaskDetailDTO(Long taskId) throws Exception{
        // 1.查询任务Entity
        TaskEntity taskEntity = taskDao.selectByPrimaryKey(taskId);
        if(Util.allFieldIsNUll(taskEntity)){
            throw new WxException(ResponseEnum.ERROR_TASK_NO_TASK);
        }

        // 2.构建任务详情DTO
        TaskDetailDTO taskDetailDTO = new TaskDetailDTO();
        taskDetailDTO.setUid(taskEntity.getUid());
        taskDetailDTO.setName(taskEntity.getName());
        taskDetailDTO.setAvatarUrl(taskEntity.getAvatarUrl());
        taskDetailDTO.setTitle(taskEntity.getTitle());
        taskDetailDTO.setContent(taskEntity.getContent());

        // 图片字符串转数组
        String[] pictrues = taskEntity.getPictrues().split(";");
        taskDetailDTO.setPictrues(pictrues);

        // 时间戳转时间
        taskDetailDTO.setCreateTime(TimeUtil.timeStampToString(taskEntity.getCreatstamp()));
        taskDetailDTO.setEndTime(TimeUtil.timeStampToString(taskEntity.getEndstamp()));

        taskDetailDTO.setLocaleName(taskEntity.getLocaleName());
        taskDetailDTO.setTelNumber(taskEntity.getTelNumber());
        taskDetailDTO.setReward(taskEntity.getReward());
        taskDetailDTO.setGivePoint(taskEntity.getGivePoint());
        taskDetailDTO.setDoUid(taskEntity.getDouid());
        taskDetailDTO.setStatus(taskEntity.getStatus());
        taskDetailDTO.setType(taskEntity.getType());


        return B.success(taskDetailDTO);
    }

    /**
     * 更新用户状态
     *
     * @param taskId
     * @param taskStatus
     * @param taskUid
     * @param userUid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateTaskStatus(Long taskId, String taskStatus, Long taskUid, Long userUid) throws Exception{
        // 1.检查taskStatus是否正确
        if(!taskStatus.equals(TaskStatusEnum.STATUS_IS_CANCEL.getCode())
                && !taskStatus.equals(TaskStatusEnum.STATUS_IS_DOING.getCode())
                && !taskStatus.equals(TaskStatusEnum.STATUS_IS_FINISH.getCode())){

            throw new WxException(ResponseEnum.ERROR_TASK_NO_STATUS);
        }

        // 2.只有自己的任务才能取消
        if(taskStatus.equals(TaskStatusEnum.STATUS_IS_CANCEL.getCode()) && !taskUid.equals(userUid)){
            throw new WxException(ResponseEnum.ERROR_TASK_CANNT_CANCEL);
        }

        Integer result = taskDao.updateTaskStatus(taskId, taskStatus);

        return B.success(result);
    }

    public ResponseDTO<?>  updateTaskDoUid(Long taskId, Long doUid) throws Exception{

        Integer result = taskDao.updateTaskDoUid(taskId, doUid);

        return B.success(result);
    }

}

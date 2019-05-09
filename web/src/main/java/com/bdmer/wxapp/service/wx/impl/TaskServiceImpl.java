package com.bdmer.wxapp.service.wx.impl;

import com.bdmer.wxapp.common.enums.TaskStatusEnum;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.WxUserHolder;
import com.bdmer.wxapp.dao.UserBdmerDao;
import com.bdmer.wxapp.dto.request.CreateTaskDTO;
import com.bdmer.wxapp.dto.request.QueryTaskListDTO;
import com.bdmer.wxapp.dto.request.QueryUserTaskListDTO;
import com.bdmer.wxapp.dto.response.DoUserDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.dto.response.TaskCardDTO;
import com.bdmer.wxapp.dto.response.TaskDetailDTO;
import com.bdmer.wxapp.model.RecordEntity;
import com.bdmer.wxapp.model.UserBdmerEntity;
import com.bdmer.wxapp.model.UserWxAppEntity;
import com.bdmer.wxapp.service.core.FileCore;
import com.bdmer.wxapp.service.core.TaskCore;
import com.bdmer.wxapp.service.core.UserBdmerCore;
import com.bdmer.wxapp.service.core.WxAuthCore;
import com.bdmer.wxapp.service.wx.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * 任务操作 - 接口实现类
 *
 * @since 2019-05-06
 * @author gongdl
 */
@Service
public class TaskServiceImpl implements ITaskService {
    private static final Logger LOG = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Value("${bdmer.upload.taskImage}")
    private String uploadDir;
    @Value("${bdmer.download.taskImage}")
    private String downloadUrl;

    @Autowired
    TaskCore taskCore;
    @Autowired
    FileCore fileCore;
    @Autowired
    UserBdmerCore userBdmerCore;
    @Autowired
    WxAuthCore wxAuthCore;

    @Override
    public ResponseDTO<?> uploadTaskPictrue(MultipartFile img) throws Exception{
        // 1.获取用户unionid
        LOG.info("【TaskServiceImpl - uploadTaskPictrue】 - 获取用户unionid");
        String unionid = WxUserHolder.getUnionid();

        // 2.解析并存储上传的任务图片
        LOG.info("【TaskServiceImpl - uploadTaskPictrue】 - 解析并存储上传的任务图片");
        String imageName = (String) fileCore.storageImage(uploadDir, unionid+ UUID.randomUUID().toString(), img).getData();

        return B.success(downloadUrl + imageName);
    }

    @Override
    public ResponseDTO<?> createTask(CreateTaskDTO createTaskDTO) throws Exception{
        // 1.获取用户
        LOG.info("【TaskServiceImpl - uploadTaskPictrue】 - 获取用户");
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();
        UserWxAppEntity userWxAppEntity = (UserWxAppEntity) wxAuthCore.getUserWxAppEntityByOpenid(WxUserHolder.getOpenid()).getData();
        createTaskDTO.setUid(userBdmerEntity.getUid());
        createTaskDTO.setName(userWxAppEntity.getNickName());
        createTaskDTO.setAvatarUrl(userWxAppEntity.getAvatarUrl());

        // 2.储存任务
        LOG.info("【TaskServiceImpl - uploadTaskPictrue】 - 储存任务");
        Long taskId = (Long) taskCore.insertTask(createTaskDTO).getData();

        return B.success(taskId);
    }

    @Override
    public ResponseDTO<?> getTaskDetail(Long taskId) throws Exception{
        // 1.获取任务详情
        TaskDetailDTO taskDetailDTO = (TaskDetailDTO) taskCore.getTaskDetailDTO(taskId).getData();

        return B.success(taskDetailDTO);
    }

    @Override
    public ResponseDTO<?> getDoUser(Long userId) throws Exception{
        // 1.获取用户
        LOG.info("【TaskServiceImpl - getDoUser】 - 获取用户");
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUid(userId).getData();
        UserWxAppEntity userWxAppEntity = (UserWxAppEntity) wxAuthCore.getUserWxAppEntityByOpenid(userBdmerEntity.getOpenidwxapp()).getData();

        // 2.构建执行人DTO
        DoUserDTO doUserDTO = new DoUserDTO();
        doUserDTO.setName(userWxAppEntity.getNickName());
        doUserDTO.setAvatarUrl(userWxAppEntity.getAvatarUrl());
        doUserDTO.setTelNumber(userBdmerEntity.getTelNumber());

        return B.success(doUserDTO);
    }

    @Override
    public ResponseDTO<?> updateTaskStatus(Long taskId, String taskStatus) throws Exception{
        // 1.获取基本数据
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();
        TaskDetailDTO taskDetailDTO = (TaskDetailDTO) taskCore.getTaskDetailDTO(taskId).getData();

        // 2.更新状态（只有用户自己才能取消）
        taskCore.updateTaskStatus(taskId, taskStatus, taskDetailDTO, userBdmerEntity);


        return B.success(true);
    }

    @Override
    public ResponseDTO<?> updateGivePoint(Long taskId, Integer givePoint) throws Exception{
        // 1.获取基本数据
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();
        TaskDetailDTO taskDetailDTO = (TaskDetailDTO) taskCore.getTaskDetailDTO(taskId).getData();

        // 2.更新状态（只有用户自己才能评价）
        taskCore.updateGivePoint(taskId, givePoint, taskDetailDTO.getUid(), taskDetailDTO.getDoUid(),taskDetailDTO.getType(), userBdmerEntity.getUid());

        return B.success(true);
    }

    @Override
    public ResponseDTO<?> updateTaskDoUid(Long taskId) throws Exception{

        // 1.获取基本数据
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();
        TaskDetailDTO taskDetailDTO = (TaskDetailDTO) taskCore.getTaskDetailDTO(taskId).getData();

        // 2.更新状态
        taskCore.updateTaskStatus(taskId,TaskStatusEnum.STATUS_IS_DOING.getCode(), taskDetailDTO, userBdmerEntity);

        // 3.更新执行人
        taskCore.updateTaskDoUid(taskId, userBdmerEntity.getUid());

        return B.success(true);
    }

    @Override
    public ResponseDTO<?> getTaskList(QueryTaskListDTO queryTaskListDTO) throws Exception{
        // 1.获取数据
        List<TaskCardDTO> taskCardDTOList = (List<TaskCardDTO>) taskCore.getTaskList(queryTaskListDTO).getData();

        return B.success(taskCardDTOList);
    }

    @Override
    public ResponseDTO<?> getUserTaskList(QueryUserTaskListDTO queryUserTaskListDTO) throws Exception{
        // 1.获取用户数据
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();

        // 2.获取用户任务数据
        List<TaskCardDTO> taskCardDTOList = (List<TaskCardDTO>) taskCore.getUserTaskList(queryUserTaskListDTO, userBdmerEntity.getUid()).getData();

        return B.success(taskCardDTOList);
    }

    @Override
    public ResponseDTO<?> getRecord() throws Exception{
        // 1.获取用户数据
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();

        // 1.获取用户点数记录
        List<RecordEntity> recordEntityList = (List<RecordEntity>) taskCore.getRecord(userBdmerEntity.getUid()).getData();

        return B.success(recordEntityList);
    }
}

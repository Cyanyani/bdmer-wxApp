package com.bdmer.wxapp.service.core;

import com.bdmer.wxapp.common.enums.*;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.TimeUtil;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.dao.RecordDao;
import com.bdmer.wxapp.dao.TaskDao;
import com.bdmer.wxapp.dao.UserBdmerDao;
import com.bdmer.wxapp.dto.request.CreateTaskDTO;
import com.bdmer.wxapp.dto.request.QueryTaskListDTO;
import com.bdmer.wxapp.dto.request.QueryUserTaskListDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.dto.response.TaskCardDTO;
import com.bdmer.wxapp.dto.response.TaskDetailDTO;
import com.bdmer.wxapp.model.RecordEntity;
import com.bdmer.wxapp.model.TaskEntity;
import com.bdmer.wxapp.model.UserBdmerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private UserBdmerDao userBdmerDao;

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
        if(taskEntity == null){
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
     * @param userBdmerEntity
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateTaskStatus(Long taskId, String taskStatus, Long taskUid, UserBdmerEntity userBdmerEntity) throws Exception{
        // 1.检查taskStatus是否正确
        if(!taskStatus.equals(TaskStatusEnum.STATUS_IS_CANCEL.getCode())
                && !taskStatus.equals(TaskStatusEnum.STATUS_IS_DOING.getCode())
                && !taskStatus.equals(TaskStatusEnum.STATUS_IS_FINISH.getCode())){

            throw new WxException(ResponseEnum.ERROR_TASK_NO_STATUS);
        }

        // 2.只有自己的任务才能取消
        if(taskStatus.equals(TaskStatusEnum.STATUS_IS_CANCEL.getCode()) && !taskUid.equals(userBdmerEntity.getUid())){
            throw new WxException(ResponseEnum.ERROR_TASK_CANNT_CANCEL);
        }

        // 3.只有验证通过的用户才可以领取任务
        if(taskStatus.equals(TaskStatusEnum.STATUS_IS_DOING.getCode()) && userBdmerEntity.getAuthStatus().intValue() == 0){
            throw new WxException(ResponseEnum.ERROR_TASK_NO_PASS_AUTH);
        }

        // 4.只有绑定了手机号码的用户才可以领取任务
        if(taskStatus.equals(TaskStatusEnum.STATUS_IS_DOING.getCode()) && !Util.isNumber(userBdmerEntity.getTelNumber())){
            throw new WxException(ResponseEnum.ERROR_TASK_NO_TEL);
        }

        Integer result = taskDao.updateTaskStatus(taskId, taskStatus);

        return B.success(result);
    }

    /**
     * 评分
     *
     * @param taskId
     * @param givePoint
     * @param taskUid
     * @param userUid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateGivePoint(Long taskId, Integer givePoint, Long taskUid, Long taskDoUid, String taskType, Long userUid) throws Exception{
        // 1.检查givePoint是否正确
        if(givePoint < -10 || givePoint > 10){
            throw new WxException(ResponseEnum.ERROR_TASK_ERROR_POINT);
        }

        // 2.只有自己的任务才能评价
        if(!taskUid.equals(userUid)){
            throw new WxException(ResponseEnum.ERROR_TASK_CANNT_GIVE_POINT);
        }

        Integer result = taskDao.updateGivePoint(taskId, givePoint);

        // 3.更新doUid的数据
        userBdmerDao.updatePointByUid(taskDoUid, givePoint);


        // 4.插入reocrd记录
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setUid(taskDoUid);

        // 选择类型
        String tempTypeMsg = TaskTypeEnum.TYPE_IS_WAIKUAI.getMsg();
        switch (taskType){
            case "WAIKUAI": tempTypeMsg = TaskTypeEnum.TYPE_IS_WAIKUAI.getMsg(); break;
            case "OTHER": tempTypeMsg = TaskTypeEnum.TYPE_IS_OTHER.getMsg(); break;
        }
        recordEntity.setEvent(tempTypeMsg);
        recordEntity.setEventdetail(taskUid.toString());
        recordEntity.setGetpoint(givePoint);
        recordEntity.setCreatetime(TimeUtil.getTimeFormat());
        recordEntity.setCreatestamp(TimeUtil.getTimeStamp());
        recordDao.insertSelective(recordEntity);

        return B.success(result);
    }

    /**
     * 领取任务
     *
     * @param taskId
     * @param doUid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?>  updateTaskDoUid(Long taskId, Long doUid) throws Exception{

        Integer result = taskDao.updateTaskDoUid(taskId, doUid);

        return B.success(result);
    }

    /**
     * 获取任务列表
     *
     * @param queryTaskListDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getTaskList(QueryTaskListDTO queryTaskListDTO) throws Exception{

        // 1.检查参数
        if(queryTaskListDTO.getIndex() < 0 || queryTaskListDTO.getSize() < 0){
            throw new WxException(ResponseEnum.ERROR_TASK_ERROR_QUERY_PARAMS);
        }

        // 查询FINDING的任务
        List<TaskCardDTO> taskCardDTOList = taskDao.getTaskList(queryTaskListDTO);

        return B.success(taskCardDTOList);
    }

    /**
     * 查询用户任务
     *
     * @param queryUserTaskListDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?>  getUserTaskList(QueryUserTaskListDTO queryUserTaskListDTO, Long tempUid) throws Exception{

        // 1.检查参数
        if(queryUserTaskListDTO.getIndex() < 0 || queryUserTaskListDTO.getSize() < 0){
            throw new WxException(ResponseEnum.ERROR_TASK_ERROR_QUERY_PARAMS);
        }

        // 构建查询参数
        String myType = queryUserTaskListDTO.getMyType();
        String myStatus = queryUserTaskListDTO.getMyStatus();

        Double lat = queryUserTaskListDTO.getLat();
        Double lng = queryUserTaskListDTO.getLng();
        Integer index = queryUserTaskListDTO.getIndex();;
        Integer size = queryUserTaskListDTO.getSize();

        Long uid = tempUid;
        Long doUid = tempUid;
        String status = TaskStatusEnum.STATUS_IS_FINDING.getCode();
        List<TaskCardDTO> taskCardDTOList;

        // 查询-CURRENT ---> FINDING + DOING
        if(myStatus.equals(UserTaskStatusEnum.USER_TASK_STATUS_ENUM_CURRENT.getCode())){
            // 查询-PUBLISH ---> uid + FINDING + DOING
            if(myType.equals(UserTaskTypeEnum.USER_TASK_TYPE_ENUM_PUBLISH.getCode())){
                status = TaskStatusEnum.STATUS_IS_FINDING.getCode();
                taskCardDTOList =  taskDao.getTaskListByUid(lat, lng, index, size, uid, status, "DOING");
            }
            // 查询-RECEIVE ---> doUid + DONING
            else{
                status = TaskStatusEnum.STATUS_IS_DOING.getCode();
                taskCardDTOList =  taskDao.getTaskListByDoUid(lat, lng, index, size, doUid, status, null);
            }
        }
        // 查询-HISTORY ---> CANCEL + FINISH
        else{
            // 查询-PUBLISH ---> uid + CANCEL
            if(myType.equals(UserTaskTypeEnum.USER_TASK_TYPE_ENUM_PUBLISH.getCode())){
                status = TaskStatusEnum.STATUS_IS_CANCEL.getCode();
                taskCardDTOList =  taskDao.getTaskListByUid(lat, lng, index, size, uid, status, "FINISH");
            }
            // 查询-RECEIVE ---> doUid + FINISH + CANCEL
            else{
                status = TaskStatusEnum.STATUS_IS_FINISH.getCode();
                taskCardDTOList =  taskDao.getTaskListByDoUid(lat, lng, index, size, doUid, status, "CANCEL");
            }
        }

        return B.success(taskCardDTOList);
    }

    /**
     * 查询用户点数记录
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getRecord(Long uid) throws Exception{

        // 查询FINDING的任务
        List<RecordEntity> recordEntityList = recordDao.selectByUid(uid);

        return B.success(recordEntityList);
    }

}

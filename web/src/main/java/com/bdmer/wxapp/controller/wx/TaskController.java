package com.bdmer.wxapp.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.dto.request.CreateTaskDTO;
import com.bdmer.wxapp.dto.request.QueryTaskListDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.service.wx.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * 任务基本操作接口 - 控制类
 *
 * @since 2019-04-27
 * @author gongdl
 */
@Controller
@RestController
@RequestMapping("/wx/task")
public class TaskController {
    private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    ITaskService taskService;

    @RequestMapping(value = "/uploadTaskPictrue",  method = RequestMethod.POST)
    public ResponseDTO<?> uploadAuthInfo(@RequestParam("file") MultipartFile img) throws Exception {
        // 参数检查
        LOG.info("【TaskController - uploadTaskPictrue】 - 参数检查");
        if(img.isEmpty()){
            LOG.error("【TaskController - uploadTaskPictrue】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【TaskController - uploadTaskPictrue】 - 开始执行");

        return taskService.uploadTaskPictrue(img);
    }

    @RequestMapping(value = "/createTask",  method = RequestMethod.POST)
    public ResponseDTO<?> createTask(String data) throws Exception {
        // 参数转化成DTO
        CreateTaskDTO createTaskDTO = JSONObject.parseObject(data, CreateTaskDTO.class);

        // 参数检查
        LOG.info("【TaskController - createTask】 - 参数检查");
        if(Util.allFieldIsNUll(createTaskDTO)){
            LOG.error("【TaskController - createTask】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        return taskService.createTask(createTaskDTO);
    }

    @RequestMapping(value = "/getTaskDetail",  method = RequestMethod.GET)
    public ResponseDTO<?> getTaskDetail(Long taskId) throws Exception {
        // 参数检查
        LOG.info("【TaskController - getTaskDetail】 - 参数检查");
        if(Util.allFieldIsNUll(taskId)){
            LOG.error("【TaskController - getTaskDetail】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【TaskController - getTaskDetail】 - 开始执行");
        return taskService.getTaskDetail(taskId);
    }

    @RequestMapping(value = "/getDoUser",  method = RequestMethod.GET)
    public ResponseDTO<?> getDoUser(Long userId) throws Exception {
        // 参数检查
        LOG.info("【TaskController - getDoUser】 - 参数检查");
        if(Util.allFieldIsNUll(userId)){
            LOG.error("【TaskController - getDoUser】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【TaskController - getDoUser】 - 开始执行");
        return taskService.getDoUser(userId);
    }

    @RequestMapping(value = "/updateTaskStatus",  method = RequestMethod.GET)
    public ResponseDTO<?> getDoUser(Long taskId, String taskStatus) throws Exception {
        // 参数检查
        LOG.info("【TaskController - updateTaskStatus】 - 参数检查");
        if(Util.allFieldIsNUll(taskId) || !Util.isString(taskStatus)){
            LOG.error("【TaskController - updateTaskStatus】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【TaskController - updateTaskStatus】 - 开始执行");
        return taskService.updateTaskStatus(taskId, taskStatus);
    }

    @RequestMapping(value = "/updateTaskDoUid",  method = RequestMethod.GET)
    public ResponseDTO<?> updateTaskDoUid(Long taskId) throws Exception {
        // 参数检查
        LOG.info("【TaskController - updateTaskDoUid】 - 参数检查");
        if(Util.allFieldIsNUll(taskId)){
            LOG.error("【TaskController - updateTaskDoUid】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【TaskController - updateTaskDoUid】 - 开始执行");
        return taskService.updateTaskDoUid(taskId);
    }

    @RequestMapping(value = "/getTaskList",  method = RequestMethod.GET)
    public ResponseDTO<?> getTaskList(String data) throws Exception {
        // 参数转化成DTO
        QueryTaskListDTO queryTaskListDTO = JSONObject.parseObject(data, QueryTaskListDTO.class);

        // 参数检查
        LOG.info("【TaskController - getTaskList】 - 参数检查");
        if(Util.allFieldIsNUll(queryTaskListDTO)){
            LOG.error("【TaskController - getTaskList】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【TaskController - getTaskList】 - 开始执行");
        //return taskService.getTaskList(queryTaskListDTO);
        return null;
    }

}

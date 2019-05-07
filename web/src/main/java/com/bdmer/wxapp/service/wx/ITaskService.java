package com.bdmer.wxapp.service.wx;

import com.bdmer.wxapp.dto.request.CreateTaskDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 任务户操作 - 接口类
 *
 * @since 2019-05-06
 * @author gongdl
 */
public interface ITaskService {
    /**
     * 上传任务图片
     *
     * @param img
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> uploadTaskPictrue(MultipartFile img) throws Exception;

    /**
     * 发布任务
     *
     * @param createTaskDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> createTask(CreateTaskDTO createTaskDTO) throws Exception;

    /**
     * 根据任务id获取任务详情
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getTaskDetail(Long taskId) throws Exception;

    /**
     * 获取执行人
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getDoUser(Long userId) throws Exception;

    /**
     * 更新任务状态
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateTaskStatus(Long taskId, String taskStatus) throws Exception;

    /**
     * 更新执行人
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateTaskDoUid(Long taskId) throws Exception;
}

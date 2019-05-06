package com.bdmer.wxapp.controller.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}

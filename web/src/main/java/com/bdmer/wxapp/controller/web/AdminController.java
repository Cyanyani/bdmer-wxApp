package com.bdmer.wxapp.controller.web;

import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/web/admin")
public class AdminController {

    @RequestMapping("/index")
    public ResponseDTO<?> login(String code){
        return B.success(code + "Hello World!");
    }
}

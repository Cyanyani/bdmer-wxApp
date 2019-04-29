package com.bdmer.wxapp.controller;

import com.bdmer.wxapp.dto.ResponseDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/user")
public class AuthController {

    @RequestMapping("/login")
    public ResponseDTO<?> login(String code){
        return null;
    }
}

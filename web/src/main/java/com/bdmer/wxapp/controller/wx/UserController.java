package com.bdmer.wxapp.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.dto.request.AESUserTelNumberDTO;
import com.bdmer.wxapp.dto.request.LocaleDTO;
import com.bdmer.wxapp.dto.request.SendWxUserInfoDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.service.wx.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * 用户基本操作接口 - 控制类
 */
@Controller
@RestController
@RequestMapping("/wx/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/login",  method = RequestMethod.POST)
    public ResponseDTO<?> login(String code) throws Exception {
        // 参数检查
        LOG.info("【UserController - login】 - 参数检查");
        if(!Util.isString(code)){
            LOG.error("【UserController - login】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【UserController - login】 - 开始执行");
        return userService.login(code);
    }

    @RequestMapping(value = "/checkToken",  method = RequestMethod.GET)
    public ResponseDTO<?> checkToken(String token) throws Exception {
        // 参数检查
        LOG.info("【UserController - checkToken】 - 参数检查");
        if(!Util.isString(token)){
            LOG.error("【UserController - checkToken】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【UserController -checkToken】 - 开始执行");
        return userService.checkToken(token);
    }

    @RequestMapping(value = "/sendWxUserInfo",  method = RequestMethod.POST)
    public ResponseDTO<?> sendWxUserInfo(String data) throws Exception {
        // 参数转化成DTO
        SendWxUserInfoDTO sendWxUserInfoDTO = JSONObject.parseObject(data, SendWxUserInfoDTO.class);

        // 参数检查
        LOG.info("【UserController - sendWxUserInfo】 - 参数检查");
        if(Util.allFieldIsNUll(sendWxUserInfoDTO)){
            LOG.error("【UserController - sendWxUserInfo】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【UserController - sendWxUserInfo】 - 开始执行");
        return userService.sendWxUserInfo(sendWxUserInfoDTO);
    }

    @RequestMapping(value = "/getUserBdmerInfo",  method = RequestMethod.GET)
    public ResponseDTO<?> getUserBdmerInfo() throws Exception {

        // 开始执行
        LOG.info("【UserController - getUserBdmerInfo】 - 开始执行");
        return userService.getUserBdmerInfo();
    }

    @RequestMapping(value = "/updateUserLocale",  method = RequestMethod.POST)
    public ResponseDTO<?> updateUserLocale(String data) throws Exception {
        // 参数转化成DTO
        LocaleDTO localeDTO = JSONObject.parseObject(data, LocaleDTO.class);

        // 参数检查
        LOG.info("【UserController - updateUserLocale】 - 参数检查");
        if(Util.allFieldIsNUll(localeDTO)){
            LOG.error("【UserController - updateUserLocale】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【UserController - updateUserLocale】 - 开始执行");
        return userService.updateUserLocale(localeDTO);
    }

    @RequestMapping(value = "/updateUserTelNumber",  method = RequestMethod.POST)
    public ResponseDTO<?> updateUserTelNumber(String data) throws Exception {
        // 参数转化成DTO
        AESUserTelNumberDTO aesUserTelNumberDTO = JSONObject.parseObject(data, AESUserTelNumberDTO.class);

        // 参数检查
        LOG.info("【UserController - updateUserTelNumber】 - 参数检查");
        if(Util.allFieldIsNUll(aesUserTelNumberDTO)){
            LOG.error("【UserController - updateUserTelNumber】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【UserController - updateUserTelNumber】 - 开始执行");
        return userService.updateUserTelNumber(aesUserTelNumberDTO);
    }

    @RequestMapping(value = "/uploadAuthInfo",  method = RequestMethod.POST)
    public ResponseDTO<?> uploadAuthInfo(@RequestParam("file") MultipartFile img) throws Exception {
        // 参数检查
        LOG.info("【UserController - uploadAuthInfo】 - 参数检查");
        if(img.isEmpty()){
            LOG.error("【UserController - uploadAuthInfo】 - 参数为空");
            return B.error(ResponseEnum.ERROR_REQ_NO_PARAM, null);
        }

        // 开始执行
        LOG.info("【UserController - uploadAuthInfo】 - 开始执行");
        return userService.uploadAuthInfo(img);
    }
}

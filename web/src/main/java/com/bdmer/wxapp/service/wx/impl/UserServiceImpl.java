package com.bdmer.wxapp.service.wx.impl;


import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.common.tool.WxUserHolder;
import com.bdmer.wxapp.dto.other.UserTokenDTO;
import com.bdmer.wxapp.dto.request.SendWxUserInfoDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.model.UserWxAppEntity;
import com.bdmer.wxapp.service.core.WxAuthCore;
import com.bdmer.wxapp.service.wx.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

/**
 * 用户操作 - 接口实现类
 *
 * @since 2019-05-03
 * @author gongdl
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    WxAuthCore wxAuthCore;

    @Override
    public ResponseDTO<?> login(String code) throws Exception{

        // 1.通过code拿到session_key + openid + unionid
        LOG.info("【Service - login】 - 1.获取userTokenDTO");
        UserTokenDTO userTokenDTO = (UserTokenDTO) wxAuthCore.getUserTokenDTO(code).getData();

        // 2.生成唯一标识token
        String token = (String) wxAuthCore.createToken().getData();
        LOG.info("【Service - login】 - 2.获取唯一token：{}", token);

        // 3.以token为键将userTokenDTO存入redis
        LOG.info("【Service - login】 - 3.开始存储userTokenDTO入redis");
        wxAuthCore.storageUserTokenDTO(token, userTokenDTO);

        // 4.通过openid去mysql查找用户
        LOG.info("【Service - login】 - 4.通过openid去mysql查找用户");
        UserWxAppEntity userWxAppEntity = (UserWxAppEntity) wxAuthCore.getUserWxAppEntityByOpenid(userTokenDTO.getOpenid()).getData();
        if(Util.isNullOrEmpty(userWxAppEntity)){
            return B.error(ResponseEnum.ERROR_WX_USER_TOKEN_NEED_INFO, token);
        }

       return B.success(token);
    }

    @Override
    public ResponseDTO<?> checkToken(String token) throws  Exception{
        // 1.通过openid去mysql查找用户
        String openid = WxUserHolder.getOpenid();
        UserWxAppEntity userWxAppEntity = (UserWxAppEntity) wxAuthCore.getUserWxAppEntityByOpenid(openid).getData();
        if(Util.isNullOrEmpty(userWxAppEntity)){
            return B.error(ResponseEnum.ERROR_WX_USER_TOKEN_NEED_INFO, token);
        }

        return B.success(token);
    }

    @Override
    public ResponseDTO<?> sendWxUserInfo(SendWxUserInfoDTO sendWxUserInfoDTO) throws Exception{
        // 1.解密，获取用户信息
        UserWxAppEntity userWxAppEntity = (UserWxAppEntity) wxAuthCore.decryptUserInfo(sendWxUserInfoDTO).getData();

        // 2.通过openid去mysql查找用户
        String openid = WxUserHolder.getOpenid();
        UserWxAppEntity olduserWxAppEntity = (UserWxAppEntity) wxAuthCore.getUserWxAppEntityByOpenid(openid).getData();
        if(Util.allFieldIsNUll(olduserWxAppEntity)){
            // --> 选择插入
            wxAuthCore.insertUserWxAppEntity(userWxAppEntity);
        }else{
            // --> 选择更新
            wxAuthCore.updateUserWxAppEntityById(userWxAppEntity);
        }

        return B.success(sendWxUserInfoDTO);
    }
}

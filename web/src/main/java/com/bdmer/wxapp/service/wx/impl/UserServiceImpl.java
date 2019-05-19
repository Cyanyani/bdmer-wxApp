package com.bdmer.wxapp.service.wx.impl;


import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.common.tool.WxUserHolder;
import com.bdmer.wxapp.dto.other.AuthInfoDTO;
import com.bdmer.wxapp.dto.other.UserTokenDTO;
import com.bdmer.wxapp.dto.request.AESUserTelNumberDTO;
import com.bdmer.wxapp.dto.request.LocaleDTO;
import com.bdmer.wxapp.dto.request.SendWxUserInfoDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.model.UserBdmerEntity;
import com.bdmer.wxapp.model.UserWxAppEntity;
import com.bdmer.wxapp.service.core.FileCore;
import com.bdmer.wxapp.service.core.UserBdmerCore;
import com.bdmer.wxapp.service.core.WxAuthCore;
import com.bdmer.wxapp.service.wx.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户操作 - 接口实现类
 *
 * @since 2019-05-03
 * @author gongdl
 */
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${bdmer.upload.authImage}")
    private String uploadDir;
    @Value("${bdmer.download.authImage}")
    private String downloadUrl;

    @Autowired
    WxAuthCore wxAuthCore;
    @Autowired
    UserBdmerCore userBdmerCore;
    @Autowired
    FileCore fileCore;

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
        UserWxAppEntity olduserWxAppEntity = (UserWxAppEntity) wxAuthCore.getUserWxAppEntityByOpenid(WxUserHolder.getOpenid()).getData();
        if(olduserWxAppEntity == null){
            // --> 选择插入
            wxAuthCore.insertUserWxAppEntity(userWxAppEntity);
        }else{
            // --> 选择更新
            wxAuthCore.updateUserWxAppEntityById(userWxAppEntity);
        }

        // 3.通过unionid或openid查询bdmer用户信息
        LOG.info("【Service - login】 - 5.通过unionid或openid查询bdmer用户信息");
        UserBdmerEntity olduserBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(userWxAppEntity.getUnionid(), userWxAppEntity.getOpenid()).getData();
        if(olduserBdmerEntity == null){
            // --> 选择插入
            userBdmerCore.insertBdmerEntity(userWxAppEntity.getUnionid(), userWxAppEntity.getOpenid());
        }
        // openidWxApp 是空
        else if(!Util.isString(olduserBdmerEntity.getOpenidwxapp())){
            userBdmerCore.updateOpenidWxApp(userWxAppEntity.getUnionid(), userWxAppEntity.getOpenid());
        }

        return B.success(sendWxUserInfoDTO);
    }

    @Override
    public ResponseDTO<?> getUserBdmerInfo() throws Exception{
        // 2.获取bdmer用户信息
        UserBdmerEntity userBdmerEntity = (UserBdmerEntity) userBdmerCore.getUserBdmerEntityByUnionid(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();
        if(userBdmerEntity == null){
            userBdmerEntity = (UserBdmerEntity) userBdmerCore.insertBdmerEntity(WxUserHolder.getUnionid(), WxUserHolder.getOpenid()).getData();
        }

        return B.success(userBdmerEntity);
    }

    @Override
    public ResponseDTO<?> updateUserLocale(LocaleDTO localeDTO) throws Exception{
        // 1.获取unionid
        String unionid = WxUserHolder.getUnionid();

        // 2.更新用户位置信息
        Integer result = (Integer) userBdmerCore.updateUserLocaleByUnionid(unionid, localeDTO).getData();

        return B.success(result);
    }

    @Override
    public ResponseDTO<?> updateUserTelNumber(AESUserTelNumberDTO aesUserTelNumberDTO) throws Exception{
        // 1.解密，获取用户电话信息
        String telNumber = (String) userBdmerCore.decryptUserTelNumber(aesUserTelNumberDTO).getData();

        // 2.获取unionid
        String unionid = WxUserHolder.getUnionid();

        // 3.更新用户电话信息
        Integer result = (Integer) userBdmerCore.updateUserTelNumberByUnionid(unionid, telNumber).getData();

        return B.success(telNumber);
    }

    @Override
    public ResponseDTO<?> uploadAuthInfo(MultipartFile img) throws Exception{
        // 1.获取用户unionid
        String unionid = WxUserHolder.getUnionid();

        // 2.解析并存储上传的MultipartFile
        String imageName = (String) fileCore.storageImage(uploadDir, unionid, img).getData();

        // 3.更新authInfo
        AuthInfoDTO authInfoDTO  = (AuthInfoDTO) userBdmerCore.updateAuthInfo(downloadUrl + imageName, 0).getData();
        authInfoDTO.setUnionid("");

        return B.success(authInfoDTO);
    }

}

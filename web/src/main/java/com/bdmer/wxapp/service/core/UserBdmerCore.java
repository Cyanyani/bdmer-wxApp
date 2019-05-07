package com.bdmer.wxapp.service.core;


import com.alibaba.fastjson.JSONObject;
import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.*;
import com.bdmer.wxapp.dao.UserBdmerDao;
import com.bdmer.wxapp.dto.other.AuthInfoDTO;
import com.bdmer.wxapp.dto.request.AESUserTelNumberDTO;
import com.bdmer.wxapp.dto.request.LocaleDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.model.UserBdmerEntity;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

/**
 * bdmer用户管理 - 核心类
 *
 * @since 2019-05-04
 * @author gongdl
 */
@Component
@Transactional
public class UserBdmerCore {
    private static final Logger LOG = LoggerFactory.getLogger(UserBdmerCore.class);

    @Value("${bdmer.upload.authImage}")
    private String uploadDir;
    @Value("${bdmer.download.authImage}")
    private String downloadUrl;

    @Autowired
    private UserBdmerDao userBdmerDao;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据unionid查询bdmer用户信息
     *
     * @param unionid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getUserBdmerEntityByUnionid(String unionid) throws Exception{
        UserBdmerEntity userBdmerEntity = userBdmerDao.selectByUnionid(unionid);

        if(Util.allFieldIsNUll(userBdmerEntity)){
            throw new WxException(ResponseEnum.ERROR_BDMER_NO_USER);
        }

        return B.success(userBdmerEntity);
    }

    /**
     * 插入bdmer用户信息
     *
     * @param
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> insertBdmerEntity() throws Exception{

        UserBdmerEntity userBdmerEntity = new UserBdmerEntity();
        userBdmerEntity.setUnionid(WxUserHolder.getUnionid());
        userBdmerEntity.setOpenidwxapp(WxUserHolder.getOpenid());
        userBdmerEntity.setPoint(15L);
        userBdmerEntity.setUsedpoint(0L);
        userBdmerEntity.setRechargepoint(0L);
        userBdmerEntity.setInvitationcount(0);
        userBdmerEntity.setTempinvitationsuccess(0);
        userBdmerEntity.setTempinvitationfail(0);
        userBdmerEntity.setTempinvitationsuccess(0);
        userBdmerEntity.setFromuid(0L);
        userBdmerEntity.setSubscribe(false);
        userBdmerEntity.setIsvip(false);
        userBdmerEntity.setCreatetime(TimeUtil.getTimeFormat());
        userBdmerEntity.setCreatestamp(TimeUtil.getTimeStamp());

        userBdmerDao.insertSelective(userBdmerEntity);

        return B.success(userBdmerEntity);
    }

    /**
     * 更新用户位置信息
     *
     * @param unionid
     * @param localeDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateUserLocaleByUnionid(String unionid, LocaleDTO localeDTO) throws Exception{

        String locale = localeDTO.getLatitude() + localeDTO.getLongitude();
        String locale_name = localeDTO.getName() + ";" +localeDTO.getAddress();

        Integer result = userBdmerDao.updateLocaleByUnionid(unionid, locale, locale_name );

        return B.success(result);
    }

    /**
     * 解密AES加密的用户信息
     *
     * @param aesUserTelNumberDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> decryptUserTelNumber(AESUserTelNumberDTO aesUserTelNumberDTO) throws Exception{
        // 解密
        byte[] resultByte = AESUtil.decrypt(Base64.decodeBase64(aesUserTelNumberDTO.getEncryptedData()), Base64.decodeBase64(WxUserHolder.getSessionKey()), Base64.decodeBase64(aesUserTelNumberDTO.getIv()));
        if(resultByte == null || resultByte.length <= 0){
            // 获取是sessionkey有问题，将token清除
            redisUtil.del(WxUserHolder.getToken());
            throw new WxException(ResponseEnum.ERROR_BDMER_USER_TEL);
        }

        // 获取用户信息
        JSONObject userTelNuumber = JSONObject.parseObject(new String(resultByte, "UTF-8"));
        if(Util.allFieldIsNUll(userTelNuumber)){
            throw new WxException(ResponseEnum.ERROR_BDMER_USER_TEL);
        }

        String telNumber = userTelNuumber.getString("phoneNumber");
        if(!Util.isString(telNumber) || !Util.isNumber(telNumber)){
            throw new WxException(ResponseEnum.ERROR_BDMER_USER_TEL);
        }

        return B.success(telNumber);
    }

    /**
     * 更新用户手机
     *
     * @param unionid
     * @param telNumber
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateUserTelNumberByUnionid(String unionid, String telNumber) throws Exception{

        Integer result = userBdmerDao.updateTelNumberByUnionid(unionid, telNumber);

        return B.success(result);
    }

    /**
     * 更新用户认证信息
     *
     * @param authImage
     * @param authStatus
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateAuthInfo(String authImage, Integer authStatus) throws Exception{

        AuthInfoDTO authInfoDTO = new AuthInfoDTO();
        authInfoDTO.setUnionid(WxUserHolder.getUnionid());
        authInfoDTO.setAuthImage(authImage);
        authInfoDTO.setAuthStatus(authStatus);

        Integer result = userBdmerDao.updateAuthInfo(authInfoDTO);

        return B.success(authInfoDTO);
    }

}

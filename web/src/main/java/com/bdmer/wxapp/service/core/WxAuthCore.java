package com.bdmer.wxapp.service.core;

import com.alibaba.fastjson.JSONObject;
import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.*;
import com.bdmer.wxapp.dao.UserWxAppDao;
import com.bdmer.wxapp.dto.other.UserTokenDTO;
import com.bdmer.wxapp.dto.request.SendWxUserInfoDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.model.UserWxAppEntity;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * 微信授权 - 核心类
 *
 * @since 2019-05-03
 * @author gongdl
 */
@Component
@Transactional
public class WxAuthCore {
    private static final Logger LOG = LoggerFactory.getLogger(WxAuthCore.class);

    @Value("${wx.app.appid}")
    private  String APPID;
    @Value("${wx.app.secret}")
    private  String SECRET;
    @Value("${wx.app.request.url}")
    private  String requestUrl;

    @Value("${redis.token.ttl}")
    private Long tokenTTL;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserWxAppDao userWxAppDao;

    /**
     * 获取微信微信授权
     *
     * @param code
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getUserTokenDTO(String code) throws Exception{
        // 请求url
        String url = requestUrl.replace("APPID", APPID).replace("SECRET", SECRET).replace("JSCODE", code);

        // 获取sessionKey + openid + unionid
        String result = HttpClientUtil.doGet(url);
        if(!Util.isString(result)){
            // ---> 应该是网络问题，所以没有返回信息。
            throw new WxException(ResponseEnum.ERROR_REQ_NO_NET);
        }

        // 将返回的json字符串转换成UserTokenDTO对象
        UserTokenDTO userTokenDTO = JSONObject.parseObject(result, UserTokenDTO.class);
        if(Util.allFieldIsNUll(userTokenDTO)){
            // ---> 应该code问题，所以信息有误，不能转换。
            throw new WxException(ResponseEnum.ERROR_WX_USER_CODE);
        }

        return B.success(userTokenDTO);
    }

    /**
     * 生成唯一token
     *
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> createToken() throws Exception{
        String token = UUID.randomUUID().toString();

        return B.success(token);
    }

    /**
     * 存储已验证信息
     *
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> storageUserTokenDTO(String token, UserTokenDTO userTokenDTO) throws Exception{
        redisUtil.set(token, userTokenDTO, tokenTTL.longValue());

        return B.success(true);
    }

    /**
     * 根据openid查找用户
     *
     * @param openid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getUserWxAppEntityByOpenid(String openid) throws Exception{
        UserWxAppEntity userWxAppEntity = userWxAppDao.selectByOpenid(openid);

        return B.success(userWxAppEntity);
    }

    /**
     * 解密AES加密的用户信息
     *
     * @param sendWxUserInfoDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> decryptUserInfo(SendWxUserInfoDTO sendWxUserInfoDTO) throws Exception{
        // 解密
        byte[] resultByte = AESUtil.decrypt(Base64.decodeBase64(sendWxUserInfoDTO.getEncryptedData()), Base64.decodeBase64(WxUserHolder.getSessionKey()), Base64.decodeBase64(sendWxUserInfoDTO.getIv()));
        if(resultByte == null || resultByte.length <= 0){
            // 获取是sessionkey有问题，将token清除
            redisUtil.del(WxUserHolder.getToken());
            throw new WxException(ResponseEnum.ERROR_WX_USER_INFO);
        }

        // 获取用户信息
        UserWxAppEntity userWxAppEntity = JSONObject.parseObject(new String(resultByte, "UTF-8"), UserWxAppEntity.class);
        if(Util.allFieldIsNUll(userWxAppEntity)){
            throw new WxException(ResponseEnum.ERROR_WX_USER_INFO);
        }
        userWxAppEntity.setCreateStamp(TimeUtil.getTimeStamp());
        userWxAppEntity.setCerateTime(TimeUtil.getTimeFormat());

        return B.success(userWxAppEntity);
    }

    /**
     * 插入用户信息
     *
     * @param userWxAppEntity
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> insertUserWxAppEntity(UserWxAppEntity userWxAppEntity) throws Exception{
        Integer result = userWxAppDao.insertSelective(userWxAppEntity);

        return B.success(result);
    }

    /**
     * 更新用户信息
     *
     * @param userWxAppEntity
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateUserWxAppEntityById(UserWxAppEntity userWxAppEntity) throws Exception{
        Integer result = userWxAppDao.updateByPrimaryKeySelective(userWxAppEntity);

        return B.success(result);
    }
}

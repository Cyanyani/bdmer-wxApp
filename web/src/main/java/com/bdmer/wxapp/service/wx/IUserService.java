package com.bdmer.wxapp.service.wx;

import com.bdmer.wxapp.dto.request.LocaleDTO;
import com.bdmer.wxapp.dto.request.SendWxUserInfoDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;

/**
 * 用户操作 - 接口类
 *
 * @since 2019-05-03
 * @author gongdl
 */
public interface IUserService {
    /**
     * 微信小程序code授权登陆
     *
     * @param code
     * @return
     */
    public ResponseDTO<?> login(String code) throws Exception;

    /**
     * 微信小程序token检查
     *
     * @param token
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> checkToken(String token) throws Exception;

    /**
     * 存储微信小程序前端发来的加密用户信息
     *
     * @param sendWxUserInfoDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> sendWxUserInfo(SendWxUserInfoDTO sendWxUserInfoDTO) throws Exception;

    /**
     * 获取bdmer用户信息
     *
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getUserBdmerInfo() throws Exception;

    /**
     * 更新用户位置信息
     *
     * @param localeDTO
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> updateUserLocale(LocaleDTO localeDTO) throws Exception;
}

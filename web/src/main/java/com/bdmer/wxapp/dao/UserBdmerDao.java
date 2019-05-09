package com.bdmer.wxapp.dao;

import com.bdmer.wxapp.dto.other.AuthInfoDTO;
import com.bdmer.wxapp.model.UserBdmerEntity;
import org.apache.ibatis.annotations.Param;

public interface UserBdmerDao {
    int deleteByPrimaryKey(Long uid);

    int insert(UserBdmerEntity record);

    int insertSelective(UserBdmerEntity record);

    UserBdmerEntity selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(UserBdmerEntity record);

    int updateByPrimaryKey(UserBdmerEntity record);

    /**
     * 根据unionid查找用户
     *
     * @param unionid
     * @return
     */
    UserBdmerEntity selectByUnionid(@Param("unionid") String unionid);

    /**
     * 根据openid查找用户
     *
     * @param openid
     * @return
     */
    UserBdmerEntity selectByOpenid(@Param("openid") String openid);

    /**
     * 根据unionid更新位置信息
     * @param unionid
     * @param locale
     * @param localeName
     * @return
     */
    int updateLocaleByUnionid(@Param("unionid") String unionid, @Param("locale") String locale, @Param("localeName") String localeName);

    /**
     * 根据unionid更新openidWxApp
     *
     * @param unionid
     * @param openidWxApp
     * @return
     */
    int updateOpenidWxApp(@Param("unionid") String unionid, @Param("openidWxApp") String openidWxApp);
    /**
     * 根据unionid更新手机号码
     *
     * @param unionid
     * @param telNumber
     * @return
     */
    int updateTelNumberByUnionid(@Param("unionid") String unionid, @Param("telNumber") String telNumber);

    /**
     * 根据unionid更新认证信息
     *
     * @param authInfoDTO
     * @return
     */
    int updateAuthInfo(AuthInfoDTO authInfoDTO);

    /**
     * 根据uid更新用户分数
     *
     * @param taskDoUid
     * @param givePoint
     * @return
     */
    int updatePointByUid(@Param("taskDoUid") Long taskDoUid , @Param("givePoint") Integer givePoint);
}
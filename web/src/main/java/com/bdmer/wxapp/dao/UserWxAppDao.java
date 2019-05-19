package com.bdmer.wxapp.dao;

import com.bdmer.wxapp.model.UserWxAppEntity;
import org.apache.ibatis.annotations.Param;

public interface UserWxAppDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserWxAppEntity record);

    int insertSelective(UserWxAppEntity record);

    UserWxAppEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserWxAppEntity record);

    int updateByPrimaryKey(UserWxAppEntity record);

    /**
     * 根据openid查找用户
     *
     * @param openid
     * @return
     */
    UserWxAppEntity selectByOpenid(@Param("openid") String openid);
}
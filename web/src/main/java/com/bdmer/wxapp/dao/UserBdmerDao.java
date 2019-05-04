package com.bdmer.wxapp.dao;

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

    int updateLocaleByUnionid(@Param("unionid") String unionid, @Param("locale") String locale, @Param("localeName") String localeName);
}
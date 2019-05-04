package com.bdmer.wxapp.service.core;


import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.TimeUtil;
import com.bdmer.wxapp.common.tool.WxUserHolder;
import com.bdmer.wxapp.dao.UserBdmerDao;
import com.bdmer.wxapp.dto.request.LocaleDTO;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import com.bdmer.wxapp.model.UserBdmerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserBdmerDao userBdmerDao;

    /**
     * 根据unionid查询bdmer用户信息
     *
     * @param unionid
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> getUserBdmerEntityByUnionid(String unionid) throws Exception{
        UserBdmerEntity userBdmerEntity = userBdmerDao.selectByUnionid(unionid);

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

    public ResponseDTO<?> updateUserLocale(String unionid, LocaleDTO localeDTO) throws Exception{

        String locale = localeDTO.getLatitude() + localeDTO.getLongitude();
        String locale_name = localeDTO.getName() + ";" +localeDTO.getAddress();

        Integer result = userBdmerDao.updateLocaleByUnionid(unionid, locale, locale_name );

        return B.success(result);
    }
}

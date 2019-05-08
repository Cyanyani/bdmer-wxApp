package com.bdmer.wxapp.dao;

import com.bdmer.wxapp.model.RecordEntity;

import java.util.List;

public interface RecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(RecordEntity record);

    int insertSelective(RecordEntity record);

    RecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordEntity record);

    int updateByPrimaryKey(RecordEntity record);

    List<RecordEntity> selectByUid(Long uid);
}
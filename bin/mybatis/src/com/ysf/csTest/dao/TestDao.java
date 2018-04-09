package com.ysf.csTest.dao;

import com.ysf.csTest.entity.Test;

public interface TestDao {
    int deleteByPrimaryKey(Integer testId);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer testId);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKeyWithBLOBs(Test record);

    int updateByPrimaryKey(Test record);
}
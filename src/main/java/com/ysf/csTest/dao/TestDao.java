package com.ysf.csTest.dao;

import com.ysf.csTest.dao.common.BaseQuery;
import com.ysf.csTest.entity.Test;

import java.util.List;

public interface TestDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKeyWithBLOBs(Test record);

    int updateByPrimaryKey(Test record);

    int deleteByIds(String ids);

    int deleteByIdsList(List<Integer> idsList);

    List<Test> selectByCondition(Test record, BaseQuery query);

    int selectByConditionGetCount(Test record, BaseQuery query);

    List<Test> selectByIds(String ids);

    List<Test> selectByIdsList(List<Integer> idsList);

    int insertBatch(List<Test> recordList);
}

package com.ysf.csTest.dao.impl;

import com.ysf.csTest.dao.UserInfoDao;
import com.ysf.csTest.dao.common.BaseQuery;
import com.ysf.csTest.dao.common.CommonMapper;
import com.ysf.csTest.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class UserInfoDaoImpl extends CommonMapper<UserInfo, BaseQuery> implements UserInfoDao {
    @Override
    public String getPackage() {
        return "com.ysf.csTest.dao.UserInfoDao.";
    }

    @Override
    public void insertInit(UserInfo record, Date date) {
        record.setId(null);
        record.setCreateTime(date);
        record.setModifyTime(date);
    }

    @Override
    public void updateInit(UserInfo record, Date date) {
        record.setModifyTime(date);
    }
}

package com.ysf.csTest.dao.impl;

import com.ysf.csTest.dao.TestDao;
import com.ysf.csTest.dao.common.BaseQuery;
import com.ysf.csTest.dao.common.CommonMapper;
import com.ysf.csTest.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class TestDaoImpl extends CommonMapper<Test, BaseQuery> implements TestDao {
    @Override
    public String getPackage() {
        return "com.ysf.csTest.dao.TestDao.";
    }

    @Override
    public void insertInit(Test record, Date date) {
        record.setTestId(null);
        record.setCreateTime(date);
        record.setModifyTime(date);
    }

    @Override
    public void updateInit(Test record, Date date) {
        record.setModifyTime(date);
    }
}

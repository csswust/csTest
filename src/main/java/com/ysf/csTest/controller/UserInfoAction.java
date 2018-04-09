package com.ysf.csTest.controller;

import com.ysf.csTest.controller.common.BaseAction;
import com.ysf.csTest.dao.UserInfoDao;
import com.ysf.csTest.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 972536780 on 2018/4/1.
 */
@RestController
@RequestMapping("/user")
public class UserInfoAction extends BaseAction {
    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(value = "/selectById")
    public UserInfo selectById(Integer id) {
        return userInfoDao.selectByPrimaryKey(id);
    }
}

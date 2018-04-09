package com.ysf.csTest.controller;

import com.ysf.csTest.controller.common.BaseAction;
import com.ysf.csTest.dao.UserInfoDao;
import com.ysf.csTest.dao.common.BaseQuery;
import com.ysf.csTest.entity.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 972536780 on 2018/4/1.
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoAction extends BaseAction {
    @Autowired
    private UserInfoDao userInfoDao;

    @RequestMapping(value = "/selectByCondition", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> selectByCondition(
            UserInfo userInfo,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows) {
        if (userInfo == null) return null;
        Map<String, Object> res = new HashMap<>();
        List<UserInfo> userInfoList = userInfoDao.selectByCondition(userInfo,
                new BaseQuery(page, rows));
        Integer total = userInfoDao.selectByConditionGetCount(userInfo, new BaseQuery());
        res.put("total", total);
        res.put("list", userInfoList);
        return res;
    }

    @RequestMapping(value = "/selectById", method = {RequestMethod.GET, RequestMethod.POST})
    public UserInfo selectById(@RequestParam Integer id) {
        return userInfoDao.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/insertOne", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> insertOne(UserInfo userInfo) {
        Map<String, Object> res = new HashMap<>();
        int result = userInfoDao.insertSelective(userInfo);
        res.put("status", result);
        return res;
    }

    @RequestMapping(value = "/updateById", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> updateById(UserInfo userInfo) {
        Map<String, Object> res = new HashMap<>();
        int result = userInfoDao.updateByPrimaryKeySelective(userInfo);
        res.put("status", result);
        return res;
    }

    @RequestMapping(value = "/deleteByIds", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> deleteByIds(@RequestParam String ids) {
        if (ids == null || StringUtils.isBlank(ids)) return null;
        Map<String, Object> res = new HashMap<>();
        int result = userInfoDao.deleteByIds(ids);
        res.put("status", result);
        return res;
    }

    @RequestMapping(value = "/insertBatch", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> insertBatch(@RequestParam UserInfo[] userInfoList) {
        if (userInfoList == null) return null;
        Map<String, Object> res = new HashMap<>();
        int status = userInfoDao.insertBatch(Arrays.asList(userInfoList));
        if (status == userInfoList.length) res.put("status", status);
        else res.put("status", 0);
        return res;
    }
}

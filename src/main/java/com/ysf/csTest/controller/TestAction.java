package com.ysf.csTest.controller;

import com.ysf.csTest.controller.common.BaseAction;
import com.ysf.csTest.dao.TestDao;
import com.ysf.csTest.dao.common.BaseQuery;
import com.ysf.csTest.entity.Test;
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
@RequestMapping("/test")
public class TestAction extends BaseAction {
    @Autowired
    private TestDao testDao;

    @RequestMapping(value = "/selectByCondition", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> selectByCondition(
            Test test,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows) {
        if (test == null) return null;
        Map<String, Object> res = new HashMap<>();
        List<Test> testList = testDao.selectByCondition(test,
                new BaseQuery(page, rows));
        Integer total = testDao.selectByConditionGetCount(test, new BaseQuery());
        res.put("total", total);
        res.put("list", testList);
        return res;
    }

    @RequestMapping(value = "/selectById", method = {RequestMethod.GET, RequestMethod.POST})
    public Test selectById(@RequestParam Integer id) {
        return testDao.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "/insertOne", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> insertOne(Test test) {
        Map<String, Object> res = new HashMap<>();
        int result = testDao.insertSelective(test);
        res.put("status", result);
        return res;
    }

    @RequestMapping(value = "/updateById", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> updateById(Test test) {
        Map<String, Object> res = new HashMap<>();
        int result = testDao.updateByPrimaryKeySelective(test);
        res.put("status", result);
        return res;
    }

    @RequestMapping(value = "/deleteByIds", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> deleteByIds(@RequestParam String ids) {
        if (ids == null || StringUtils.isBlank(ids)) return null;
        Map<String, Object> res = new HashMap<>();
        int result = testDao.deleteByIds(ids);
        res.put("status", result);
        return res;
    }

    @RequestMapping(value = "/insertBatch", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> insertBatch(@RequestParam Test[] testList) {
        if (testList == null) return null;
        Map<String, Object> res = new HashMap<>();
        int status = testDao.insertBatch(Arrays.asList(testList));
        if (status == testList.length) res.put("status", status);
        else res.put("status", 0);
        return res;
    }
}

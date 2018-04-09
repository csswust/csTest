package com.ysf.csTest.test.utils;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;

/**
 * Created by 972536780 on 2018/4/9.
 */
public class Main {
    private static String modelPath = "E:\\javawork\\csTest\\src\\main\\resources\\mybatis_mappers";

    public static void main(String[] args) throws IOException {
        File oldPathFile = new File(modelPath);
        File[] files = oldPathFile.listFiles();
        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            Model model = Generator.getModel(file);
            System.out.println(JSON.toJSONString(model));
            DaoImplGenerator.generator(model);
        }
    }
}

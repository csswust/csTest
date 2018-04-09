package com.ysf.csTest.test.utils;

import com.alibaba.fastjson.JSON;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MapperImplGenerator {
    private static String oldPath = "E:\\javawork\\csTest\\src\\main\\resources\\mybatis_mappers";
    private static String newPath = oldPath + "/impl";
    private static String customPath = oldPath + "/custom";

    public static void main(String[] args) throws IOException {
        File oldPathFile = new File(oldPath);
        File[] files = oldPathFile.listFiles();
        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            if (!judge(newPath + "/" + file.getName())) {
                continue;
            }
            Model model = Generator.getModel(newPath + "/" + file.getName());
            System.out.println(JSON.toJSONString(model));
            generator(model, file.getName());

            if (!judge(customPath + "/" + file.getName())) {
                continue;
            }
            generatorCustom(model, file.getName());
            if (count++ >= 100) {
                break;
            }
        }
    }

    private static void generatorCustom(Model model, String fileName) throws IOException {
        File newFile = new File(customPath + "/" + fileName);
        String resultMap = model.isBlob() ? "ResultMapWithBLOBs" : "BaseResultMap";
        PrintWriter printWriter = new PrintWriter(newFile);
        printWriter.printf("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n" +
                "<mapper namespace=\"%s\">\n" +
                "    <sql id=\"custom_condition\">\n" +
                "        ORDER BY %s DESC\n" +
                "    </sql>\n" +
                "</mapper>", model.getNamespace(), model.getIdColumn());
        printWriter.flush();
        printWriter.close();
    }

    private static boolean judge(String path) {
        File newFile = new File(path);
        if (newFile.exists()) {
            return false;
        } else {
            try {
                newFile.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static void generator(Model model, String fileName) throws IOException {
        File newFile = new File(newPath + "/" + fileName);
        String resultMap = model.isBlob() ? "ResultMapWithBLOBs" : "BaseResultMap";
        PrintWriter printWriter = new PrintWriter(newFile);
        printWriter.printf("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
        printWriter.printf("<mapper namespace=\"%s\">\n", model.getNamespace());
        printWriter.printf("    <select id=\"selectByCondition\" resultMap=\"%s\" parameterType=\"java.util.Map\">\n" +
                "        SELECT\n", resultMap);
        printWriter.printf("        <include refid=\"Base_Column_List\"/>\n");
        if (model.isBlob()) {
            printWriter.printf("        ,\n" +
                    "        <include refid=\"Blob_Column_List\"/>\n");
        }
        printWriter.printf("        FROM %s\n" +
                "        WHERE 1 = 1\n", model.getTableName());
        printfField(printWriter, model.getIdColumn(), model.getIdProperty());
        for (Field field : model.getFieldList()) {
            printfField(printWriter, field.getColumn(), field.getProperty());
        }
        for (Field field : model.getBlobFieldList()) {
            printfField(printWriter, field.getColumn(), field.getProperty());
        }
        printWriter.printf("        <include refid=\"custom_condition\"/>\n");
        printWriter.printf("        <if test=\"start != null and rows != null\">\n" +
                "            LIMIT #{start}, #{rows}\n" +
                "        </if>\n" +
                "    </select>\n");
        printWriter.printf("    <select id=\"selectByConditionGetCount\" resultType=\"java.lang.Integer\" parameterType=\"java.util.Map\">\n" +
                "        SELECT\n" +
                "        count(*)\n");
        printWriter.printf("        FROM %s\n" +
                "        WHERE 1 = 1\n", model.getTableName());
        printfField(printWriter, model.getIdColumn(), model.getIdProperty());
        for (Field field : model.getFieldList()) {
            printfField(printWriter, field.getColumn(), field.getProperty());
        }
        for (Field field : model.getBlobFieldList()) {
            printfField(printWriter, field.getColumn(), field.getProperty());
        }
        printWriter.printf("        <include refid=\"custom_condition\"/>\n");
        printWriter.printf("        <if test=\"start != null and rows != null\">\n" +
                "            LIMIT #{start}, #{rows}\n" +
                "        </if>\n" +
                "    </select>\n");
        printWriter.printf("    <delete id=\"deleteByIdsList\" parameterType=\"java.util.Map\">\n" +
                "        delete from %s\n" +
                "        where %s in\n" +
                "        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\">\n" +
                "            #{item}\n" +
                "        </foreach>\n" +
                "    </delete>\n", model.getTableName(), model.getIdColumn());
        printWriter.printf("    <select id=\"selectByIdsList\" resultMap=\"%s\" parameterType=\"java.util.Map\">\n" +
                "        select\n" +
                "        <include refid=\"Base_Column_List\" />\n", resultMap);
        if (model.isBlob()) {
            printWriter.printf("        ,\n" +
                    "        <include refid=\"Blob_Column_List\"/>\n");
        }
        printWriter.printf("        from %s\n" +
                "        where %s in\n" +
                "        <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\">\n" +
                "            #{item}\n" +
                "        </foreach>\n" +
                "    </select>\n", model.getTableName(), model.getIdColumn());
        printWriter.printf("    <select id=\"insertBatch\" parameterType=\"java.util.Map\">\n" +
                "        insert into %s\n" +
                "        (%s) values\n" +
                "        <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">\n" +
                "            (%s)\n" +
                "        </foreach>\n" +
                "    </select>\n",model.getTableName(),getFileStr(model),getValueStr(model));
        printWriter.printf("</mapper>");
        printWriter.flush();
        printWriter.close();
    }

    public static String getFileStr(Model model) {
        String str = "";
        List<Field> fieldList = model.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            if (i != 0) str += ", ";
            str += fieldList.get(i).getColumn();
        }
        return str;
    }

    public static String getValueStr(Model model) {
        String str = "";
        List<Field> fieldList = model.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            if (i != 0) str += ", ";
            str += "#{item." + fieldList.get(i).getProperty() + "}";
        }
        return str;
    }

    public static void printfField(PrintWriter printWriter, String cc, String pp) {
        printWriter.printf("        <if test=\"record.%s != null\">\n" +
                "            AND\n" +
                "            %s = #{record.%s}\n" +
                "        </if>\n", pp, cc, pp);
    }
}

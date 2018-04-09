package com.ysf.csTest.test.utils;

import com.alibaba.fastjson.JSON;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 972536780 on 2018/4/9.
 */
public class Generator {
    public static Model getModel(String xmlPath) {
        File xmlFile = new File(xmlPath);
        return getModel(xmlFile);
    }

    public static Model getModel(File xmlFile) {
        if (xmlFile.isDirectory()) return null;
        Element rootElement = readXml(xmlFile.getPath());
        Model model = getModel(rootElement);
        return model;
    }

    public static Model getModel(Element rootElement) {
        Model model = new Model();
        model.setNamespace(rootElement.attributeValue("namespace"));
        List<Element> resultMapList = rootElement.elements("resultMap");
        Element firltMap = resultMapList.get(0);
        // 设置type
        model.setType(firltMap.attributeValue("type"));
        // 获取对应class
        try {
            Class<?> cla = Class.forName(model.getType());
            model.setTypeClass(cla);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 设置主键
        Element id = firltMap.element("id");
        model.setIdColumn(id.attributeValue("column"));
        model.setIdProperty(id.attributeValue("property"));
        model.setIdJdbcType(id.attributeValue("jdbcType"));
        // 设置字段
        model.setFieldList(getField(firltMap));
        if (resultMapList.size() != 1) {
            model.setBlob(true);
            model.setBlobFieldList(getField(resultMapList.get(1)));
        }
        Element delete = rootElement.element("delete");
        String text = delete.getText().replaceAll("\n", "");
        String result = text.split("\\s+")[3];
        model.setTableName(result);
        return model;
    }

    public static List<Field> getField(Element rootElement) {
        List<Field> fieldList = new ArrayList<>();
        List<Element> elementList = rootElement.elements("result");
        for (Element element : elementList) {
            Field field = new Field();
            field.setColumn(element.attributeValue("column"));
            field.setProperty(element.attributeValue("property"));
            field.setJdbcType(element.attributeValue("jdbcType"));
            fieldList.add(field);
        }
        return fieldList;
    }

    public static Element readXml(String filePath) {
        InputStream in = null;
        Element rootElement = null;
        try {
            SAXReader reader = new SAXReader();
            in = new FileInputStream(new File(filePath));
            Document doc = reader.read(in);
            rootElement = doc.getRootElement();
            System.out.println("XMLUtil.readXml root name:" + rootElement.getName());
        } catch (Exception e) {
            System.err.println("XMLUtil.readXml error: " + e);
            return null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rootElement;
    }
}

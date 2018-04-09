package com.ysf.csTest.test.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MapperImplGenerator {
    public static void generator(Model model) throws IOException {
        Map<String, Object> map = Generator.getTemplateMap(model);
        map.put("conditionStr", getConditionStr(model));
        map.put("FieldStr", getFieldStr(model));
        map.put("valueStr", getValueStr(model));

        String typeName = (String) map.get("typeName");
        Generator.writeTemplate(Generator.xmlImplPath + "/" + typeName + "Mapper.xml",
                "mapper-template/XMLImplMapper.ftl", map);
        Generator.writeTemplate(Generator.xmlCustomPath + "/" + typeName + "Mapper.xml",
                "mapper-template/XMLCustomMapper.ftl", map);
    }

    private static String getFieldStr(Model model) {
        String str = "";
        List<Field> fieldList = model.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            if (i != 0) str += ", ";
            str += fieldList.get(i).getColumn();
        }
        return str;
    }

    private static String getValueStr(Model model) {
        String str = "";
        List<Field> fieldList = model.getFieldList();
        for (int i = 0; i < fieldList.size(); i++) {
            if (i != 0) str += ", ";
            str += "#{item." + fieldList.get(i).getProperty() + "}";
        }
        return str;
    }

    private static String getConditionStr(Model model) {
        StringBuilder builder = new StringBuilder();
        builder.append(printfField(model.getIdColumn(), model.getIdProperty()));
        for (Field field : model.getFieldList()) {
            builder.append(printfField(field.getColumn(), field.getProperty()));
        }
        for (Field field : model.getBlobFieldList()) {
            builder.append(printfField(field.getColumn(), field.getProperty()));
        }
        return builder.toString();
    }

    private static String printfField(String cc, String pp) {
        return String.format("        <if test=\"record.%s != null\">\n" +
                "            AND\n" +
                "            %s = #{record.%s}\n" +
                "        </if>\n", pp, cc, pp);
    }
}

package com.ysf.csTest.test.utils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 972536780 on 2018/4/9.
 */
public class ActionGenerator {
    public static void generator(Model model) throws IOException {
        Map<String, Object> map = Generator.getTemplateMap(model);
        String typeName = (String) map.get("typeName");
        String lTypeName = toLowerCaseFirstOne(typeName);
        map.put("lTypeName", lTypeName);
        Generator.writeTemplate(Generator.actionPath + "/" + typeName + "Action.java",
                "mapper-template/Action.ftl", map);
    }

    private static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}

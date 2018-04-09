package com.ysf.csTest.test.utils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by 972536780 on 2017/11/20.
 */
public class DaoImplGenerator {
    public static void generator(Model model) throws IOException {
        Map<String, Object> map = Generator.getTemplateMap(model);
        String typeName = (String) map.get("typeName");
        Generator.writeTemplate(Generator.daoImplPath + "/" + typeName + "DaoImpl.java",
                "mapper-template/DaoImplMapper.ftl", map);
        Generator.writeTemplate(Generator.daoPath + "/" + typeName + "Dao.java",
                "mapper-template/DaoMapper.ftl", map);
    }
}

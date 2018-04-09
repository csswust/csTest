package com.ysf.csTest.test.utils;

import com.ysf.csTest.utils.FileUtil;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 972536780 on 2017/11/20.
 */
public class DaoImplGenerator {
    private static String oldPath = "E:\\javawork\\csTest\\src\\main\\java\\com\\ysf\\csTest\\dao";
    private static String newPath = oldPath + "/impl";
    private static String basePackage = "com.ysf.csTest";

    public static void generator(Model model) throws IOException {
        String typeName = model.getTypeClass().getSimpleName();
        File newFile = new File(newPath + "/" + typeName + "DaoImpl.java");
        if (newFile.exists()) return;
        else newFile.createNewFile();
        Map<String, Object> map = new HashMap<>();
        map.put("basePackage", basePackage);
        map.put("typeName", typeName);
        map.put("model", model);
        String template = FileUtil.readResource("mapper-template/DaoImplMapper.ftl");
        String TemplateContent = FreeMarkerUtil.proccessTemplate(model.getNamespace(),
                template, map);
        FileUtils.write(newFile, TemplateContent, "UTF-8");
    }

    public static void main(String[] args) throws IOException {
        File oldPathFile = new File(oldPath);
        File[] files = oldPathFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            Scanner in = new Scanner(new FileInputStream(file));
            String packageLine = in.nextLine();
            packageLine = packageLine.substring(8, packageLine.length() - 1);
            in.nextLine();
            String importLine = in.nextLine();
            in.nextLine();
            in.next();
            in.next();
            String calssName = in.next();
            calssName = calssName.substring(0, calssName.length() - 3);
            in.next();
            in.next();
            in.next();
            String primaryName = in.next();
            primaryName = primaryName.substring(0, primaryName.length() - 2);
            String daPrimaryName = primaryName.substring(0, 1).toUpperCase() + primaryName.substring(1);
            generator(packageLine, importLine, calssName, primaryName, daPrimaryName, file.getName());
            in.close();
        }
    }

    private static void generator(String packageLine, String importLine, String calssName,
                                  String primaryName, String daPrimaryName, String newFileName) throws IOException {
        File newFile = new File(newPath + "/" + calssName + "DaoImpl.java");
        if (newFile.exists()) {
            return;
        } else {
            newFile.createNewFile();
        }
        PrintWriter printWriter = new PrintWriter(newFile);
        printWriter.printf("package %s.impl;\n" +
                        "\n" +
                        "import com.ysf.csTest.dao.common.BaseQuery;\n" +
                        "import com.ysf.csTest.dao.common.CommonMapper;\n" +
                        "import %s.%sDao;\n" +
                        "import com.ysf.csTest.entity.%s;\n" +
                        "import org.springframework.stereotype.Repository;\n\n" +
                        "import java.util.Date;\n\n" +
                        "@Repository\n",
                packageLine, packageLine, calssName, calssName);
        printWriter.printf("public class %sDaoImpl extends CommonMapper<%s, BaseQuery> implements %sDao {\n" +
                "    @Override\n" +
                "    public String getPackage() {\n" +
                "        return \"com.ysf.csTest.dao.%sDao.\";\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void insertInit(%s record, Date date) {\n" +
                "        record.set%s(null);\n" +
                "        record.setCreateTime(date);\n" +
                "        record.setModifyTime(date);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public void updateInit(%s record, Date date) {\n" +
                "        record.setModifyTime(date);\n" +
                "    }\n" +
                "}\n", calssName, calssName, calssName, calssName, calssName, daPrimaryName, calssName);
        printWriter.flush();
        printWriter.close();
    }
}

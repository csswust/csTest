package com.ysf.csTest.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 972536780 on 2017/11/20.
 */
public class Config {
    private static Logger log = LoggerFactory.getLogger(Config.class);
    private static volatile Map<String, String> configFile = new ConcurrentHashMap<>();

    // 刷新配置文件的配置
    public static void refreshConfig() {
        Properties prop = new Properties();
        try {
            String path = Config.class.getClassLoader().getResource("config.properties").getPath();
            InputStream in = new BufferedInputStream(new FileInputStream(path));
            prop.load(in);
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                configFile.put(key, prop.getProperty(key));
            }
            in.close();
        } catch (Exception e) {
            log.error("读取config.properties出错 error: {}", e);
        }
    }

    public static String get(String key, String defaultValue) {
        String result = configFile.get(key);
        return result == null ? defaultValue : result;
    }

    public static int getToInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(configFile.get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long getToLong(String key, long defaultValue) {
        try {
            return Long.parseLong(configFile.get(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
}

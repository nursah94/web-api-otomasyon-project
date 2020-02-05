package config;

import java.io.FileInputStream;
import java.util.Properties;

public class TestConfig {

    private static Properties properties = null;

    private static Properties getProperties() {
        if (properties == null) {
            try {
                properties = new Properties();
                FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/config/config.properties");
                properties.load(ip);
                return properties;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }
}

package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("test-config.properties")) {
            if (input == null) {
                throw new RuntimeException("Configuration file not found.");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Configuration file not loaded.", ex);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

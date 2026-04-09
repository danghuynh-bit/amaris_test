package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configutaion Manager to handle application properties
 */
public class ConfigManager {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at " + CONFIG_FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }   

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static String getAppUrl() {
        return getProperty("app.url");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public static boolean shouldMaximizeWindow() {
        return Boolean.parseBoolean(getProperty("maximize.window", "true"));
    }

    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout", "30"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }

    public static String getReportPath() {
        return getProperty("report.path", "test-output/extent-reports");
    }

    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "test-output/screenshots");
    }

    public static String getEnvironment() {
        return getProperty("environment", "qa");
    }
}
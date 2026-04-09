package com.automation.utils;

/**
 * Constants used across the test automation framework
 */
public class Constants {

    // Timeouts
    public static final int DEFAULT_WAIT_TIME = 30;
    public static final int SHORT_WAIT_TIME = 10;
    public static final int LONG_WAIT_TIME = 60;

    // File paths
    public static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    public static final String TEST_DATA_PATH = "src/test/resources/testdata/";
    public static final String SCREENSHOT_PATH = "test-output/screenshots/";
    public static final String REPORT_PATH = "test-output/extent-reports/";

    // Browser names
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";

    // Test environments
    public static final String QA = "qa";
    public static final String STAGING = "staging";
    public static final String CI = "ci";
    public static final String UAT = "uat";
    public static final String PROD = "prod";

    // Error messages
    public static final String ELEMENT_NOT_FOUND = "Element not found: ";
    public static final String TIMEOUT_OCCURRED = "Timeout occurred after waiting for: ";

    private Constants() {
        // Private constructor to prevent instantiation
    }

}
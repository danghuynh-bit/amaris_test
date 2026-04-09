package com.automation.utils;

import com.automation.config.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots
 */
public class ScreenshotUtils {
    
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            
            File destinationFile = new File(ConfigManager.getScreenshotPath() + "/" + fileName);
            destinationFile.getParentFile().mkdirs(); // Ensure directory exists

            FileUtils.copyFile(source, destinationFile);

            return destinationFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to capture screenshot: " + e.getMessage(), e);
        }
    }

    public static String takeScreenshot(WebDriver driver) {
        return takeScreenshot(driver, "screenshot");
    }
}
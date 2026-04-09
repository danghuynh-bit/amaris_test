package com.automation.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Common utility methods for test automation
 */
public class CommonUtils {

    private static final Random random = new Random();

    public static String generateRadomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static String generateRandomEmail() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "user" + timestamp + "@example.com";
    }

    public static String generateRandomPhoneNumber() {
        StringBuilder sb = new StringBuilder("07");
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getFormattedDateTime(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted during sleep", e);
        }
    }

    /**
     * Calculate a single date from a string.
     * Assumes dateString is in "yyyy-MM-dd" format.
     * Returns a LocalDate object.
     */
    public static LocalDate calculateDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Calculate and format a single date from a string.
     * Assumes dateString is in "yyyy-MM-dd" format.
     * Returns the formatted date string.
     */
    public static String calculateDate(String dateString, String format) {
        LocalDate date = calculateDate(dateString);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(format);
        return date.format(outputFormatter);
    }

    /**
     * Format a LocalDate to the specified format.
     */
    public static String formatDate(LocalDate date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
}
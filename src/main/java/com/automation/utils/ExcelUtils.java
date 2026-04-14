package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Utility class for reading test data from Excel files
 */
public class ExcelUtils {

    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet " + sheetName + " not found in " + filePath);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet " + sheetName);
            }

            int numRows = sheet.getPhysicalNumberOfRows();
            int numCols = headerRow.getLastCellNum();

            for (int i = 1; i < numRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> dataMap = new HashMap<>();
                for (int j = 0; j < numCols; j++) {
                    Cell headerCell = headerRow.getCell(j);
                    Cell dataCell = row.getCell(j);

                    String headerValue = getCellValueAsString(headerCell);
                    String dataValue = getCellValueAsString(dataCell);

                    dataMap.put(headerValue, dataValue);
                }
                dataList.add(dataMap);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
        }

        return dataList;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * Helper method to convert the String values from the Map back to Integers safely.
     * Put this here so all logic stays in ExcelUtils.
     */
    public static int getNumberValue(Map<String, String> data, String key) {
        String value = data.get(key);
        if (value == null) {
            throw new RuntimeException("The key '" + key + "' was not found in the Excel data. Check for typos/case-sensitivity.");
        }
        // Even with formatter, if a user typed a decimal in Excel, we handle it
        return (int) Double.parseDouble(value);
    }
}
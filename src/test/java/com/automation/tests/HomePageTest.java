package com.automation.tests;

import com.automation.driver.DriverFactory;
import com.automation.pages.*;
import com.automation.reporting.ExtendReportManager;
import com.automation.utils.ExcelUtils;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class HomePageTest {

    private WebDriver driver;
    private HomePage homePage;
    private CartPage cartPage;
    private ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        ExtendReportManager.initializeReport();
    }

    // DataProvider to read Excel data
    @DataProvider(name = "hotelData")
    public Object[][] getHotelData() {
        String filePath = "src/test/resources/data/HotelTestData.xlsx";
        String sheetName = "SearchTests";
        List<Map<String, String>> excelData = ExcelUtils.readExcelData(filePath, sheetName);

        Object[][] data = new Object[excelData.size()][1];
        for (int i = 0; i < excelData.size(); i++) {
            data[i][0] = excelData.get(i);
        }
        return data;
    }

    @BeforeMethod
    public void setUp() {
        ExtendReportManager.createTest("HomePage Search Test", "Test for searching hotel name on HomePage");
        test = ExtendReportManager.getTest();
        test.log(Status.INFO, "Initializing WebDriver and navigating to Agoda");

        DriverFactory.initializeDriver();
        driver = DriverFactory.getDriver();
        driver.get("https://www.agoda.com/");
        homePage = new HomePage(driver);
        cartPage = new CartPage(driver);   

        test.log(Status.INFO, "HomePage loaded successfully");
    }

    @Test(dataProvider = "hotelData")
    public void testSearchHotelName(Map<String, String> data) {
        // Extract data from the Map
        String hotelName = data.get("HotelName");
        int startOffset = ExcelUtils.getNumberValue(data, "StartOffSet"); 
        int endOffset = ExcelUtils.getNumberValue(data, "EndOffSet");
        int rooms = ExcelUtils.getNumberValue(data, "Rooms");
        int adults = ExcelUtils.getNumberValue(data, "Adults");
        int children = ExcelUtils.getNumberValue(data, "Children");

        // Initialize dynamic report for each data row
        ExtendReportManager.createTest("Search Test: " + hotelName, "Testing search for " + hotelName);
        test = ExtendReportManager.getTest();

        test.log(Status.INFO, "Starting search for: " + hotelName);
        
        homePage.searchAndChooseHotel(hotelName);
        homePage.chooseDate(startOffset, endOffset, "yyyy-MM-dd");
        homePage.chooseNumberOfRoomsAndPeople(rooms, adults, children);
        homePage.searchHotels();
        cartPage.chooseFirstHotelCardAndVerifyPrice();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test skipped: " + result.getThrowable());
        }

        // Capture screenshot at the end of the test
        try {
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getMethod().getMethodName());
            test.addScreenCaptureFromPath(screenshotPath);
            test.log(Status.INFO, "Screenshot captured: " + screenshotPath);
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
        }

        ExtendReportManager.removeTest();
        DriverFactory.quitDriver();
        test.log(Status.INFO, "Browser closed");
    }

    @AfterSuite
    public void flushReport() {
        ExtendReportManager.flushReport();
    }
}
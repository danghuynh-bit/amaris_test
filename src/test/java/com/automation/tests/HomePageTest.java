package com.automation.tests;

import com.automation.driver.DriverFactory;
import com.automation.pages.*;
import com.automation.reporting.ExtendReportManager;
import com.automation.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.Set;

public class HomePageTest {

    private WebDriver driver;
    private HomePage homePage;
    private CartPage cartPage;
    private ExtentTest test;

    @BeforeSuite
    public void setUpReport() {
        ExtendReportManager.initializeReport();
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

    @Test
    public void testSearchHotelName() {
        String hotelName = "Muong Thanh Saigon Centre Hotel";
        test.log(Status.INFO, "Starting hotel search and selection");

        homePage.searchAndChooseHotel(hotelName);
        homePage.chooseDate(2, 3, "yyyy-MM-dd");
        homePage.chooseNumberOfRoomsAndPeople(1, 4, 2);
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
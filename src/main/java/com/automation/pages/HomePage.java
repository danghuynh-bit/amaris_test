package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.automation.reporting.ExtendReportManager;
import com.automation.utils.ScreenshotUtils;
import com.automation.utils.CommonUtils;
import com.aventstack.extentreports.Status;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class HomePage extends BasePage {

    @FindBy(id = "textInput")
    private WebElement inputSearch;

    @FindBy(xpath = "(//li[@data-testid = 'topDestinationListItem'])[1]")
    private WebElement firstSearchOption;

    @FindBy(xpath = "//i[contains(@class, 'check-in')]")
    private WebElement checkInIcon;

    @FindBy(xpath = "//div[contains(@data-selenium, 'checkInText')]")
    private WebElement checkInText;

    @FindBy(xpath = "//div[contains(@data-selenium, 'checkOutText')]")
    private WebElement checkOutText;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyBox')]")
    private WebElement roomDropdown;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyRooms')]//div[@data-component='desktop-occ-room-value']")
    private WebElement roomCountText;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyRooms')]//button[@aria-label='Cộng Phòng']")
    private WebElement btnIncreaseRoom;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyAdults')]//div[@data-component='desktop-occ-adult-value']")
    private WebElement adultCountText;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyAdults')]//button[@aria-label='Cộng Người lớn'] | //div[contains(@data-selenium, 'occupancyAdults')]//button[@aria-label='Add Adults']")
    private WebElement btnIncreaseAdult;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyChildren')]//div[@data-component='desktop-occ-children-value']")
    private WebElement childCountText;

    @FindBy(xpath = "//div[contains(@data-selenium, 'occupancyChildren')]//button[@aria-label='Add Children'] | //div[contains(@data-selenium, 'occupancyChildren')]//button[@aria-label='Add Child']")
    private WebElement btnIncreaseChild;

    @FindBy(xpath = "//button[contains(@data-element-name, 'occ-child-age-dropdown')]")
    private List<WebElement> childAgeDropdowns;

    @FindBy(xpath = "//span[contains(., '<1 year old')]")
    private WebElement under1AgeOption;

    @FindBy(xpath = "//button[contains(., 'TÌM')] | //button[contains(., 'SEARCH')]")
    private WebElement searchButton;

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Page actions
    public void searchAndChooseHotel(String hotelName) {
        super.waitForElementVisible(inputSearch).sendKeys(hotelName);
        logger.info("Entered hotel name: " + hotelName);
        if (ExtendReportManager.getTest() != null) {
            ExtendReportManager.getTest().log(Status.INFO, "Entered hotel name in search field: " + hotelName);
        }
        super.waitForElementVisible(firstSearchOption);
        super.click(firstSearchOption);
        if (ExtendReportManager.getTest() != null) {
            ExtendReportManager.getTest().log(Status.INFO, "Selected the first search option");
        }
        try {
            Thread.sleep(5000); // Wait for 5 seconds
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.INFO, "Waited 5 seconds after selection");
            }
            // Capture screenshot
            String screenshotPath = ScreenshotUtils.takeScreenshot(driver, "after_selection");
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().addScreenCaptureFromPath(screenshotPath);
                ExtendReportManager.getTest().log(Status.INFO, "Screenshot captured after selection: " + screenshotPath);
            }
        } catch (InterruptedException e) {
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.WARNING, "Wait interrupted: " + e.getMessage());
            }
        }
    }

    public String getInputSearchValue() {
        return inputSearch.getAttribute("value");
    }

    // Choose date method
    public void chooseDate(int startFrom, int endTo, String format) {
        // Calculate dates based on day offsets from today
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusDays(startFrom);
        LocalDate endDate = today.plusDays(endTo);

        // Format dates for logging
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
        String formattedStart = startDate.format(dateFormatter);
        String formattedEnd = endDate.format(dateFormatter);
        String formattedToday = CommonUtils.formatDate(today, format);

        // Open calendar if not visible
        WebElement todayEle = driver.findElement(By.xpath("//span[contains(@data-selenium-date, '" + formattedToday + "')]"));
        if (!super.isElementDisplayed(todayEle)) {
            super.click(checkInIcon);
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.INFO, "Clicked on check-in icon to open calendar");
            }
        } else {
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.INFO, "Calendar is already open");
            }
        }

        // Wait and click start date (find fresh each time)
        try {
            By startDateLocator = By.xpath("//span[contains(@data-selenium-date, '" + formattedStart + "')]");
            WebElement startDateEle = super.waitForElementClickable(driver.findElement(startDateLocator));
            super.click(startDateEle);
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.INFO, "Clicked on start date: " + formattedStart);
            }
        } catch (Exception e) {
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.FAIL, "Failed to click start date " + formattedStart + ": " + e.getMessage());
            }
            throw e;
        }

        // Add delay before clicking end date to let calendar re-render
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait and click end date (find fresh each time)
        try {
            By endDateLocator = By.xpath("//span[contains(@data-selenium-date, '" + formattedEnd + "')]");
            WebElement endDateEle = super.waitForElementClickable(driver.findElement(endDateLocator));
            super.click(endDateEle);
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.INFO, "Clicked on end date: " + formattedEnd);
            }
        } catch (Exception e) {
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.FAIL, "Failed to click end date " + formattedEnd + ": " + e.getMessage());
            }
            throw e;
        }

        // Wait for the dates to update
        try {
            Thread.sleep(3000); // Wait 3 seconds for UI update
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.INFO, "Waited 3 seconds for date selection to update");
            }
        } catch (InterruptedException e) {
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.WARNING, "Wait interrupted: " + e.getMessage());
            }
        }

        // Verify check-in and check-out texts
        String checkInTextValue = super.getText(checkInText);
        String checkOutTextValue = super.getText(checkOutText);

        if (ExtendReportManager.getTest() != null) {
            ExtendReportManager.getTest().log(Status.INFO, "Check-in text: " + checkInTextValue);
            ExtendReportManager.getTest().log(Status.INFO, "Check-out text: " + checkOutTextValue);
        }
    }

    public void chooseNumberOfRoomsAndPeople(int room, int adult, int child) {
        // Set rooms
        int currentRooms = Integer.parseInt(roomCountText.getText());
        while (currentRooms < room) {
            super.click(btnIncreaseRoom);
            currentRooms++;
        }

        // Set adults
        int currentAdults = Integer.parseInt(adultCountText.getText());
        while (currentAdults < adult) {
            super.click(btnIncreaseAdult);
            currentAdults++;
        }

        // Set children
        int currentChildren = Integer.parseInt(childCountText.getText());
        while (currentChildren < child) {
            super.click(btnIncreaseChild);
            currentChildren++;
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (WebElement ele : childAgeDropdowns) {
            super.click(ele);
            super.waitForElementVisible(under1AgeOption);
            super.click(under1AgeOption);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void searchHotels() {
        super.click(searchButton);
    }

    @Override
    public boolean isPageLoaded() {
        return super.waitForElementVisible(inputSearch) != null;
    }
}
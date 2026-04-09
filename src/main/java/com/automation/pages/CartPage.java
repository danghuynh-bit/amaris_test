package com.automation.pages;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import com.automation.reporting.ExtendReportManager;
import com.aventstack.extentreports.Status;

public class CartPage extends BasePage {

    @FindBy(id = "textInput")
    private WebElement inputSearch;

    @FindBy(xpath = "(//div[contains(@class, 'PropertyCard__Container') and not(contains(., 'Sold out')) and contains(., '₫')])[1]")
    private WebElement firstHotelCard;

    @FindBy(xpath = "(//span[contains(@class, 'StickyNavPrice__priceDetail--lowerText-small')])[2]//following-sibling::span[2]")
    private WebElement firstHotelPrice;

    // Constructor
    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Page actions
    public void chooseFirstHotelCard() {
        scrollToFirstHotelCard();
        super.waitForElementVisible(firstHotelCard);
        super.click(firstHotelCard);
        if (ExtendReportManager.getTest() != null) {
            ExtendReportManager.getTest().log(Status.INFO, "Clicked on the first hotel card");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void scrollToFirstHotelCard() {
        new Actions(driver).scrollToElement(firstHotelCard).perform();
        // super.scrollToElement(firstHotelCard);
        if (ExtendReportManager.getTest() != null) {
            ExtendReportManager.getTest().log(Status.INFO, "Scrolled to the first hotel card");
        }
    }

    public boolean chooseFirstHotelCardAndVerifyPrice() {
        chooseFirstHotelCard();

        // Switch to the new window that opens after search
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                if (ExtendReportManager.getTest() != null) {
                    ExtendReportManager.getTest().log(Status.INFO, "Switched to new window: " + window);
                }
                break;
            }
        }

        try {
            WebElement priceElement = super.waitForElementVisible(firstHotelPrice);
            boolean displayed = priceElement.isDisplayed();
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.PASS, "First hotel price displayed: " + displayed);
            }
            return displayed;
        } catch (Exception e) {
            if (ExtendReportManager.getTest() != null) {
                ExtendReportManager.getTest().log(Status.FAIL, "First hotel price was not displayed after selecting hotel card: " + e.getMessage());
            }
            return false;
        }
    }

    @Override
    public boolean isPageLoaded() {
        return super.waitForElementVisible(inputSearch) != null;
    }
}
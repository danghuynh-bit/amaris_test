package com.automation.pages;

import com.automation.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;
import java.util.List;

/**
 * Base Page class containing common methods for all page objects
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;
    protected Logger logger;
    protected Hotel hotel;
    protected ExtentTest test;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
        this.jsExecutor = (JavascriptExecutor) driver;
        this.logger = LogManager.getLogger(this.getClass());
        PageFactory.initElements(driver, this);

        // Initialize hotel model for use in page objects
        this.hotel = Hotel.getInstance();

        PageFactory.initElements(driver, this);
    }

    // Wait methods
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected boolean waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)) != null;
    }

    protected boolean waitForElementToDisappear(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // Click methods
    protected void click(WebElement element) {
        waitForElementClickable(element);
        element.click();   
        logger.info("Clicked on element: " + element.toString());
    }

    protected void clickWithJS(WebElement element) {
        jsExecutor.executeScript("arguments[0].click();", element);
        logger.info("Clicked on element with JS: " + element.toString());
    }

    protected void doubleClick(WebElement element) {
        waitForElementClickable(element);
        actions.doubleClick(element).perform();
        logger.info("Double clicked on element: " + element.toString());
    }

    protected void rightClick(WebElement element) {
        waitForElementClickable(element);
        actions.contextClick(element).perform();
        logger.info("Right clicked on element: " + element.toString());
    }

    // Input methods
    protected void selectByValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
        logger.info("Selected value '" + value + "' from dropdown: " + element.toString());
    }

    protected void selectByText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
        logger.info("Selected visible text '" + text + "' from dropdown: " + element.toString());
    }

    protected void selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
        logger.info("Selected index '" + index + "' from dropdown: " + element.toString());
    }

    // Get methods
    protected String getText(WebElement element) {
        waitForElementVisible(element);
        String text = element.getText();
        logger.info("Retrieved text '" + text + "' from element: " + element.toString());
        return text;
    }

    protected String getAttribute(WebElement element, String attribute) {
        waitForElementVisible(element);
        String attrValue = element.getAttribute(attribute);
        logger.info("Retrieved attribute '" + attribute + "' with value '" + attrValue + "' from element: " + element.toString());
        return attrValue;
    }

    // Validation methods
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    // Scroll methods
    protected void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        logger.info("Scrolled to element: " + element.toString());
    }

    protected void scrollToTop() {
        jsExecutor.executeScript("window.scrollTo(0, 0);");
        logger.info("Scrolled to top of the page");
    }

    protected void scrollToBottom() {
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        logger.info("Scrolled to bottom of the page");
    }

    // Page methods
    protected void refreshPage() {
        driver.navigate().refresh();
        logger.info("Page refreshed");
    }

    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current URL: " + url);
        return url;
    }

    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Retrieved page title: " + title);
        return title;
    }

    // Abstract method to be implemented by each page
    public abstract boolean isPageLoaded();
}
package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Wrappers {
    private WebDriver driver;
    private static WebDriverWait wait;
    private static JavascriptExecutor js;

    // Constructor to initialize WebDriver and JavascriptExecutor
    public Wrappers(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }

    // Method to send keys to a WebElement
   public void sendKeys(WebElement inputBox, String keysToSend) {
    try {
        inputBox.sendKeys(keysToSend);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(inputBox)); // Wait until inputBox is clickable
        System.out.println("Entered Value Successfully");
        js.executeScript("window.scrollBy(0,400);");
    } catch (Exception e) {
        System.out.println("Exception occurred while sending Keys: " + e.getMessage());
    }
}

    // Method to open a URL in the browser
    public void openURL(String url) {
        try {
            // Check if current URL is different from the specified URL
            if (!(driver.getCurrentUrl().equals(url))) {
                driver.get(url);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.urlToBe(url));            }
        } catch (Exception e) {
            System.out.println("Exception occurred while Navigating: " + e.getMessage());
        }
    }

    // Method to wait for an element to be clickable
    public void waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Method to click on a WebElement
    public void click(WebElement element) {
        try {
            if (element != null && element.isDisplayed()) {
                waitForElementToBeClickable(element);
                element.click();
                //Thread.sleep(1000);
            } else {
                System.out.println("Element is not displayed or null");
            }
        } catch (Exception e) {
            System.out.println("Exception occurred while clicking: " + e.getMessage());
        }
    }
}

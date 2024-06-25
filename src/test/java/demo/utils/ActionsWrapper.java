package demo.utils;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsWrapper {
    /**
    Waits for the element to be visible before sending keys.
     *
     * @param driver The WebDriver instance to use for locating the element and sending keys.
     * @param locator The locator strategy used to find the web element.
     * @param text The text to be sent to the web element.
     */
    public static void sendKeysAW(WebDriver driver, By locator, String text) {
        try {
            YouTubeUtils.logStatus("Sending Text inside fields");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(text);
            // Sending ENTER key press after sending the text
            element.sendKeys(Keys.ENTER);
        } catch (Exception e) {
            YouTubeUtils.logStatus("Exception while sending keys" + e.getMessage());
        }
    }

    /**
     *
     * @param driver The WebDriver instance to use for locating the element and clicking.
     * @param locator The locator strategy used to find the web element.
     */
    public static void clickAW(WebDriver driver, By locator) {
        try {
            YouTubeUtils.logStatus("Click on the locator");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            WebElement element = driver.findElement(locator);
            // Clicking the element
            element.click();
        } catch (Exception e) {
            YouTubeUtils.logStatus(e.getMessage());
        }
    }
}
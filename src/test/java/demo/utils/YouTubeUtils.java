package demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YouTubeUtils {
   /**
     * Log status
     *
     * @param message prints text inside it.
     */
    public static void logStatus(String message) {
        System.out.println(message);
    }


    /**
     * Scrolls the viewport
     *
     * @param driver The WebDriver instance on which the scrolling operation is performed.
     * @param locator The By locator used to identify the element to scroll to.
     */
    public static void scrollToViewport(WebDriver driver, By locator) {
        try {
            logStatus("***scrollToViewport***");

            // Set up a WebDriverWait to wait for the element to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", element);
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
    }

    /**
     * Retrieves and prints the displayed message of an element identified by the specified locator.
     *
     * @param driver The WebDriver instance where the element is located.
     * @param locator The By locator used to identify the element containing the displayed message.
     */
    public static void getDisplayedMessage(WebDriver driver, By locator) {
        try {
            logStatus("getDisplayedMessage");

            // Find the element containing the displayed message
            WebElement msg = driver.findElement(locator);

            // Print the displayed message
            logStatus(msg.getText());
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
    }
      /**
    * Retrieves the text content of a child element located within a parent element identified by the provided locators.
    *
    * @param driver The WebDriver instance where the elements are located.
    * @param loc The By locator used to identify the parent element.
    * @param cloc The By locator used to identify the child element within the parent element.
    * @return The text content of the child element if found, otherwise an empty string.
    */
    public static String getElementText(WebDriver driver, By loc, By cloc) {
        String movie = "";
        try {
            logStatus("Getting element text");

            // Retrieve the parent element
            WebElement element = getElement(driver, loc);

            // Retrieve the child element and get its text content
            movie = element.findElement(cloc).getText();
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
        return movie;
    }


    /**
     * Scrolls to the extreme end of the page by continuously clicking on an element identified by the specified locator.
     *
     * @param driver The WebDriver instance to perform the scrolling operation.
     * @param locator The By locator used to identify the element to click for scrolling.
     */
    public static void scrollToExtreme(WebDriver driver, By locator) {
        try {
            logStatus("Scrolling to extreme");

            // Set up a WebDriverWait to wait for the element to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            // Scroll to the extreme end by continuously clicking on the element
            while (button.isDisplayed()) {
                button.click();
            }
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
    }

  

  
    /**
     * Retrieves a list of WebElements identified by the specified locator within the WebDriver's current context.
     *
     * @param driver The WebDriver instance where the elements are located.
     * @param news The By locator used to identify the list of WebElements to retrieve.
     * @return A list of WebElements identified by the locator if found, otherwise an empty list.
     */
    public static List<WebElement> getElements(WebDriver driver, By news) {
        List<WebElement> elements = new ArrayList<>();
        try {
            logStatus("Getting elements");

            // Find all elements matching the locator
            elements = driver.findElements(news);
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
        return elements;
    }

    /**
     * Retrieves the body content and view count of news posts identified by the specified locator within the WebDriver's current context.
     *
     * @param driver The WebDriver instance where the news posts are located.
     * @param news The By locator used to identify the list of news posts.
     */
    public static void getBodyAndViewCount(WebDriver driver, By news) {
        int sum = 0;
        try {
            logStatus("Getting body and view count");

            // Wait for the first news post to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(news));

            // Retrieve all news posts matching the provided locator
            List<WebElement> newsPosts = YouTubeUtils.getElements(driver, news);

            // Iterate through each news post
            for (WebElement elementBody : newsPosts) {
                int likes = 0;
                try {
                    // Retrieve the number of likes for the current news post
                    WebElement tot = elementBody.findElement(By.xpath(".//span[contains(@id, 'vote-count-middle')]"));
                    likes = Integer.parseInt(tot.getText());
                } catch (Exception e) {
                    // If likes count is not available or cannot be parsed, set likes to 0
                    likes = 0;
                }

                // Retrieve the body content of the current news post
                WebElement newsBody = elementBody.findElement(By.xpath(".//div[contains(@id, 'body')]"));
                sum += likes;

                logStatus(String.format("%s Likes, Body: %s", likes, newsBody.getText()));
            }
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
        logStatus("Total likes: " + sum);
    }
      /**
     * Retrieves a WebElement identified by the specified locator within the WebDriver's current context.
     *
     * @param driver The WebDriver instance where the element is located.
     * @param loc The By locator used to identify the WebElement to retrieve.
     * @return The WebElement identified by the locator if found and visible within the specified timeout, otherwise returns null.
     */
    public static WebElement getElement(WebDriver driver, By loc) {
        WebElement mv = null;
        try {
            logStatus("Getting element");

            // Set up a WebDriverWait to wait for the element to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            mv = wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
        } catch (Exception e) {
            logStatus(e.getMessage());
        }
        return mv;
    }
     /**
     * Parses the views count from the given text representation of views.
     *
     * @param viewsText The text representation of views to parse the count from.
     * @return The parsed views count as a long integer.
     */
    private static long parseviewSum(String viewsText) {
        viewsText = viewsText.replace(" views", "");
        long viewSum;
        if (viewsText.endsWith("K")) {
            viewSum = (long) (Double.parseDouble(viewsText.substring(0, viewsText.length() - 1)) * 1_000);
        } else if (viewsText.endsWith("M")) {
            viewSum = (long) (Double.parseDouble(viewsText.substring(0, viewsText.length() - 1)) * 10_00_000);
        } else {
            viewSum = Long.parseLong(viewsText);
        }
        return viewSum;
    }

    /**
     * Scrolls through video views until the total count reaches the specified target count.
     *
     * @param driver The WebDriver instance where the videos are located.
     * @param totalCount The target total count of video views to reach.
     */
    public static void scrollTillVideoCountReaches(WebDriver driver, long totalCount) {
        try {
            logStatus("Scrolling till view count reaches " + totalCount);

            // Initialize the index for locating video views
            int index = 1;

            // Continue scrolling until the total count reaches 0 or below
            while (totalCount >= 0) {
                // Wait for the video view element to become visible
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                WebElement video = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//ytd-video-renderer//span[contains(text(), 'views')])["+ index +"]")));

                // Scroll the viewport to the video view element
                YouTubeUtils.scrollToViewport(driver, By.xpath("(//ytd-video-renderer//span[contains(text(), 'views')])["+ index +"]"));

                // Subtract the views count of the current video from the total count
                totalCount -= parseviewSum(video.getText());

                // Increment the index for the next video
                index++;
                logStatus(String.format("(%s) %s views, Remaining: %s", video.getText(), parseviewSum(video.getText()), (totalCount <= 0) ? "Count reached" : totalCount));
            }
        } catch (Exception e) {
            logStatus( e.getMessage());
        }
    }

   
}
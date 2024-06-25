package demo;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import demo.utils.ActionsWrapper;
import demo.utils.ExcelDataProvider;

import demo.utils.YouTubeUtils;
import demo.wrappers.Wrappers;

public class TestCases {

    ChromeDriver driver;
    SoftAssert sa = new SoftAssert();// Initializing softassert
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void testCase01() throws InterruptedException {
        Wrappers w = new Wrappers(driver);
        System.out.println("Start Test case: Testcase01");
        w.openURL("https://www.youtube.com/");
        System.out.println("Opened Youtube");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Assertion: Verify correct URL
        assert driver.getCurrentUrl().toLowerCase().contains("youtube.com") : "Not on Correct Website please check!";
        Thread.sleep(3000);
        // Create a JavaScriptExecutor instance
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Scroll down to bring the "About" link into view
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        // Wait for a few seconds   
        try {
            Thread.sleep(3000);  
        } catch (InterruptedException e) {
        System.out.println("Cant be scrolled");
        }
        // Find and click on "About" link at the bottom of the sidebar
        WebElement aboutLink = driver.findElement(By.xpath("//a[@slot='guide-links-primary'][text()='About']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", aboutLink);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink));
        aboutLink.click();
        Thread.sleep(2000);
        // Wait for the "About" page to load
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Print the message on the screen
        System.out.println("Message on About page: " + driver.getTitle());
        WebElement about = driver.findElement(By.xpath("//h1[@class='lb-font-display-1 lb-font-weight-700 lb-font-color-text-primary lb-font--no-crop']"));
        System.out.println(about.getText());
        List<WebElement> texts = driver.findElements(By.xpath("//p[@class='lb-font-display-3 lb-font-color-text-primary']"));
        for (WebElement t : texts) {
            System.out.println(t.getText());
        }
        System.out.println("End Test case: Testcase01");
    }
public class YouTubeMoviesTest {
    
    @Test(priority = 3, dataProvider = "movieData", enabled = true, description = "Test Case 2 Movies Task Verification")
    public void testCase02(String section) throws InterruptedException {
        YouTubeUtils.logStatus("Verify movie is marked 'A' for Mature and movie is either 'Comedy' or 'Animation'");
        Wrappers w = new Wrappers(driver);
        w.openURL("https://www.youtube.com/");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        // Assertion: Verify correct URL
        assert driver.getCurrentUrl().toLowerCase().contains("youtube.com") : "Not on Correct Website please check!";
        Thread.sleep(3000);

        // Navigate to the "Movies" tab
        By movie = By.xpath("//a[contains(@title, 'Films') or contains(@title, 'Movies')]");
        YouTubeUtils.scrollToViewport(driver, movie);
        ActionsWrapper.clickAW(driver, movie);

        // Scroll to the extreme right within the specified section
       YouTubeUtils.scrollToExtreme(driver, By.xpath("//span[contains(text(), '"+ section +"')]/ancestor::div[contains(@class, 'item-section')]//button[contains(@aria-label, 'Next')]"));

       // final one in movie
        By fin = By.xpath("(//span[contains(text(), '"+ section +"')]/ancestor::div[contains(@class, 'item-section')]//ytd-grid-movie-renderer)[last()]");

        // Soft Assert on whether the movie is marked "A" for Mature or not
        By mature = By.xpath(".//p[not(contains(text(), 'Buy') or contains(text(), 'Rent'))]");
        String matureText = YouTubeUtils.getElementText(driver, fin, mature);
        sa.assertEquals(matureText, "A", "The movie is not marked as 'A'");

        // Soft assert on whether the movie is either "Comedy" or "Animation"
        By genre = By.xpath(".//span[contains(@class, 'metadata')]");
        String genreText = YouTubeUtils.getElementText(driver, fin, genre);
        sa.assertTrue(genreText.contains("Comedy") || genreText.contains("Animation"), "The movie genre is neither 'Comedy' nor 'Animation'");
        sa.assertAll();
        YouTubeUtils.logStatus("Verify movie is marked 'A' for Mature and movie is either 'Comedy' or 'Animation'");
    }

    @DataProvider(name = "movieData")
    public Object[][] movieData() {
        return new Object[][] {
            {"Top selling"},
            {"Top selling"},
        };
    }
}

    @Test(priority = 2, enabled = true, description = "Verify number of tracks")
    public void testCase03() throws InterruptedException{
        YouTubeUtils.logStatus("Music Related Stuff");
        Wrappers w = new Wrappers(driver);
        w.openURL("https://www.youtube.com/");
        System.out.println("Opened Youtube");
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        // Assertion: Verify correct URL
        assert driver.getCurrentUrl().toLowerCase().contains("youtube.com") : "Not on Correct Website please check!";
        Thread.sleep(3000);
        // Navigate to the "Music" tab of the application
        By musicTab = By.xpath("//a[contains(@title, 'Music')]");
        YouTubeUtils.scrollToViewport(driver, musicTab);
        ActionsWrapper.clickAW(driver, musicTab);

        // Locate the first section of playlists
        By firstSection = By.xpath("(//ytd-item-section-renderer)[1]");
        YouTubeUtils.scrollToViewport(driver, firstSection);

        // Scroll to the extreme right within the first section
        YouTubeUtils.scrollToExtreme(driver, By.xpath("(//ytd-item-section-renderer)[1]//button[contains(@aria-label, 'Next')]"));

        // Last playlist
        By lastPlaylist = By.xpath("((//ytd-item-section-renderer)[1]//ytd-compact-station-renderer)[last()]");

        // Print the name of the playlist
        By playlistNameLocator = By.xpath(".//h3");
        String playlistName = YouTubeUtils.getElementText(driver, lastPlaylist, playlistNameLocator);
        YouTubeUtils.logStatus("Playlist name: " + playlistName);

        // Count the number of tracks listed in the playlist
        By numberOfTrackLocator = By.xpath(".//p[contains(@id, 'video-count')]");
        int numberOfTracks = Integer.parseInt(YouTubeUtils.getElementText(driver, lastPlaylist, numberOfTrackLocator).replaceAll("[\\D]", ""));
        YouTubeUtils.logStatus("Track Count: " + numberOfTracks);

        // Soft assert whether the number of tracks listed is less than or equal to 50
        sa.assertTrue((numberOfTracks <= 50), "number of tracks is more than 50");

        sa.assertAll();
        YouTubeUtils.logStatus("Verifying number of tracks");
    }

    @Test(priority = 1, enabled = true, description = "Verify news information and no of likes")
    public void testCase04() {
        YouTubeUtils.logStatus("Verify news body and likes");
        // Navigate to the "News" tab of the application
        By newsTab = By.xpath("//a[contains(@title, 'News')]");
        YouTubeUtils.scrollToViewport(driver, newsTab);
        ActionsWrapper.clickAW(driver, newsTab);
        By latestNewsPosts = By.xpath("//span[contains(text(), 'Latest news post')]");
        YouTubeUtils.scrollToViewport(driver, latestNewsPosts);
        int numberOfNewsPosts = 3;
        By firstNNewsLocator = By.xpath("(//span[contains(text(), 'Latest news post')]/ancestor::ytd-rich-section-renderer//ytd-post-renderer)[position() <= "+ numberOfNewsPosts +"]");
        YouTubeUtils.getBodyAndViewCount(driver, firstNNewsLocator);
        YouTubeUtils.logStatus("Verify news information");
    }
    @Test(priority = 4, enabled = true, description = "Verify video views count", dataProvider = "text", dataProviderClass = ExcelDataProvider.class)
    public void testCase05(String text) {
        YouTubeUtils.logStatus("Verify video views count: " + text);
        // Search for the item
        By searchBox = By.xpath("//input[contains(@id, 'search')]");
        ActionsWrapper.sendKeysAW(driver, searchBox, text);
        long t = 10_00_00_000;
        YouTubeUtils.scrollTillVideoCountReaches(driver, t);
        YouTubeUtils.logStatus("Verify video views count: " + text);
    }
    @AfterTest
    public void endTest()
    {
        YouTubeUtils.logStatus("Quitting driver");
        if (driver != null) {
            driver.quit();
        }
        YouTubeUtils.logStatus("Done driver quit");
    }
}

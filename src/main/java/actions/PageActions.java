// https://github.com/SeleniumHQ/selenium/wiki/PageFactory
// https://www.toptal.com/selenium/test-automation-in-selenium-using-page-object-model-and-page-factory

package actions;

import co.boorse.seleniumtable.SeleniumTable;
import com.typesafe.config.Config;
import config.EnvFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.jsoup.helper.Validate.fail;

@Slf4j
public class PageActions {
    private WebDriver driver;

    private static WebDriverWait shortWait;
    private static WebDriverWait longWait;
    private static WebDriverWait extraLongWait;

    private static Config config = EnvFactory.getInstance().getConfig();
    private final static String PATH_RESULTS_DIR = config.getString("resultsDir");

    public PageActions(WebDriver driver) {
        this.driver = driver;
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        extraLongWait = new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    /**
     * In our application, when we say "addApplicant" then application refocuses.
     * Chrome driver cannot handle this: https://chromedriver.chromium.org/help/clicking-issues
     * Also the javascript scrollIntoView method does not always work, so we have another method
     * that is not as efficient but that works to scroll page into center.
     * Thus when things are not clickable, because page did not scroll or did not scroll enough
     * hide behind a ribbon, than we try again to recenter and click element. This seems to have
     * fixed the flakiness issue I encountered early on after migration to Json Schema.
     */
    public void scrollIntoViewAndClick(WebElement webElement) {
        try {
            waitForElementToBeClickable(webElement);
            scrollIntoView(webElement);
            webElement.click();
        } catch (Exception exception) {
            log.debug("scrollIntoView failed. Now trying with scrollIntoViewAndCenter");
            scrollIntoViewAndCenter(webElement);
            webElement.click();
        }
    }

    public void getPage(String page) {
        driver.get(page);
        longWait.until(ExpectedConditions.urlToBe(page));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentPageURL() {
        return driver.getCurrentUrl();
    }

    public void scrollIntoView(WebElement webElement) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", webElement);
        } else {
            throw new RuntimeException("The driver used doesn't implement JavascriptExecutor");
        }
    }

    public SeleniumTable getHtmlTable(WebElement tableElement) {
        return SeleniumTable.getInstance(tableElement);
    }

    public void scrollIntoViewAndCenter(WebElement webElement) {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, webElement);
            // Selenium cannot click on moving elements. So you have to let the page settle down before clicking.
            waitForPageToRecenter(2);
        } else {
            throw new RuntimeException("The driver used doesn't implement JavascriptExecutor");
        }
    }

    /**
     * The selenium click method does not work for elements hidden behind ribbon. Thus this method can be used in such cases.
     */
    public void scrollIntoViewAndClickViaJavaScript(WebElement webElement) {
        scrollIntoViewAndCenter(webElement);
        clickViaJavaScript(webElement);
    }

    public void clickViaJavaScript(WebElement webElement) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", webElement);
        } else {
            throw new RuntimeException("The driver used doesn't implement JavascriptExecutor");
        }
    }

    public void setCheckbox(WebElement webElement) {
        scrollIntoView(webElement);
        if (! webElement.isSelected()) {
            webElement.click();
        }
    }

    public void setTextField(WebElement webElement, String value) {
        waitUntilElementIsVisible(webElement);
        scrollIntoViewAndClick(webElement);
        webElement.clear();
        webElement.sendKeys(value);
    }

    public void setListItem(List<WebElement> webElementList, String attributeName, String attributeValue) {
        // Below step is required for list items. Else the test gets flaky.
        waitUntilAllElementsAreDisplayed(webElementList);
        WebElement webElement = webElementList
                .stream()
                .filter(element -> element.getAttribute(attributeName).equals(attributeValue))
                .findFirst().get();
        clickViaJavaScript(webElement);
    }

    // Below step is required for list items. Else the test gets flaky.
    public void waitUntilAllElementsAreDisplayed(List<WebElement> webElements) {
        shortWait.until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

    public Boolean waitUntilElementDisappears(By locator) {
        return shortWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitUntilElementIsClickable(WebElement webElement) {
        return shortWait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public WebElement waitUntilElementIsVisible(WebElement webElement) {
        return shortWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public void getPageWithRetry(String pageUrl, By expectedElementLocator) {
        driver.get(pageUrl);

        /* sometimes there can be a blank page displayed - probably some selenium bug.
        If the page is not loaded try to open it once again */
        boolean validUrl = driver.getCurrentUrl().equals(pageUrl);
        boolean pageLoaded = isElementAvailableOnThePage(expectedElementLocator);
        if (! validUrl || ! pageLoaded) {
            log.info("Blank page has been displayed, retrying...");
            sleepForSeconds(2);
            driver.get(pageUrl);
            longWait.until(ExpectedConditions.urlToBe(pageUrl));
        }else{
            log.info("Navigated to pageUrl: {}", pageUrl);
        }
    }

    protected boolean isElementAvailableOnThePage(By locator) {
        List<WebElement> addButton = findElements(locator);
        return addButton.size() > 0;
    }

    public void waitUntilElementIsDisplayed(WebElement webElement) {
        waitUntilElementIsVisible(webElement).isDisplayed();
    }

    public boolean isElementDisplayed(WebElement webElement) {
        return waitUntilElementIsVisible(webElement).isDisplayed();
    }

    public boolean isTextPresentInWebElement(WebElement webElement, String text) {
        return shortWait.until(ExpectedConditions.textToBePresentInElement(webElement, text));
    }

    public boolean doesElementHaveAttributeWithValue(WebElement webElement, String attribute, String text) {
        return shortWait.until(ExpectedConditions.attributeToBe(webElement, attribute, text));
    }

    public String getTextFromElement(WebElement webElement) {
        return waitUntilElementIsVisible(webElement).getText();
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public static void waitBeforeCheckingDatabase(Integer seconds) {
        sleepForSeconds(seconds);
    }

    public static void waitForCardToBeVisibleAfterCarouselClicks(Integer seconds) {
        sleepForSeconds(seconds);
    }

    public static void waitForProgressToUpdate(Integer seconds) {
        sleepForSeconds(seconds);
    }

    public static void waitForPageToLoad(Integer seconds) {
        sleepForSeconds(seconds);
    }

    public static void waitForPageToRecenter(Integer seconds) {
        sleepForSeconds(seconds);
    }

    public static void waitToSeeIfThereAreAnyTechnicalErrors(Integer seconds) {
        sleepForSeconds(seconds);
    }

    public static void sleepForSeconds(Integer seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // We want to extend these methods but not to show them in intellisense as domain methods.
    public void findElementAndSetTextField(String cssSelector, String textToSet) {
        WebElement element = findElementByCssSelector(cssSelector);
        setTextField(element, textToSet);
    }

    public void findElementsAndSetAListItem(String cssSelector, String listItem) {
        List<WebElement> elements = findElementsByCssSelector(cssSelector);
        setListItem(elements, "data-value", listItem);
    }

    public List<WebElement> findElementsByCssSelector(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }

    protected void findElementAndClick(String cssSelector) {
        WebElement webElement = findElementByCssSelector(cssSelector);
        scrollIntoViewAndClick(webElement);
    }

    public void findElementAndClick(By selector) {
        WebElement webElement = driver.findElement(selector);
        scrollIntoViewAndClick(webElement);
    }

    protected WebElement findElementByCssSelector(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    public WebElement findElement(By selector) {
        return driver.findElement(selector);
    }

    public List<WebElement> findElements(By selector) {
        return driver.findElements(selector);
    }

    protected String findElementAndGetText(String cssSelector) {
        WebElement element = findElementByCssSelector(cssSelector);
        return element.getText();
    }

    public List<String> findElementsAndReturnAllListItemsDataValues(String cssSelector) {
        List<WebElement> elements = findElementsByCssSelector(cssSelector);

        List<String> dataValues = new ArrayList<>();
        for (WebElement element : elements) {
            dataValues.add(element.getAttribute("data-value"));
        }

        return dataValues;
    }

    public static LogEntries getBrowserLogs(WebDriver driver) {
        return driver.manage().logs().get(LogType.BROWSER);
    }

    public static void assertBrowserLogs(LogEntries logs) {
        Boolean errors = false;
        if (logs.getAll().size() > 0) {
            for (LogEntry entry : logs) {
                if (entry.getLevel().toString().equalsIgnoreCase("SEVERE")) {
                    errors = true;
                    log.info(entry.getLevel() + " " + entry.getMessage());
                }
            }
        }

        if (errors == true) {
            fail("Errors logs found. \n");
        }
    }

    public static void clearConsole(WebDriver driver) {
        String script = "console.clear();";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }

    public void printElementAttributes(WebElement webElement) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Object debug = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) " +
                "{ items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;", webElement);
        log.info(debug.toString());
    }

    public static void initializeResultsDir(String className) {
        try {
            Path path = Paths.get(String.format("./%s/%s", PATH_RESULTS_DIR, className));
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File dir = new File(String.format("./%s/%s", PATH_RESULTS_DIR, className));
        File[] listFiles = dir.listFiles();
        for (File file : listFiles) {
            System.out.println("Deleting " + file.getName());
            file.delete();
        }
    }

    public void getScreenShot(String directoryName, String fileName) {
        try {
            Path path = Paths.get(String.format("./%s/%s", PATH_RESULTS_DIR, directoryName));
            Files.createDirectories(path);

            // https://dzone.com/articles/using-selenium-webdriver-for-full-page-screenshots#:~:text=AShot()%20is%20a%20web,Beautify%20screenshot.
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "PNG", new File(String.format("%s/%s.png", path, fileName)));

            // To take screenshot for visible area under test. 
            // File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // //The below method will save the screen shot in destination directory with name of fileName
            // FileHandler.copy(scrFile, new File(String.format("%s/%s.png", path, fileName)));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void mouseHover(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void setTextFieldAndPressEnter(WebElement webElement, String text) {
        webElement.sendKeys(text);
        webElement.sendKeys(Keys.ENTER);
    }

    public void waitForUrlContaining(String url) {
        longWait.until(ExpectedConditions.urlContains(url));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return shortWait.until(ExpectedConditions.elementToBeClickable(element));
    }
//    Type this in console of chrome browser and hover to any point to get the page coordinates
//    https://stackoverflow.com/questions/12888584/is-there-a-way-to-tell-chrome-web-debugger-to-show-the-current-mouse-position-in
//    document.onmousemove = function(e) {
//        var x = e.pageX;
//        var y = e.pageY;
//        e.target.title = "X is " + x + " and Y is " + y;
//    };

    public void waitForPageToLoad() {
        longWait.until(webDriver1 -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void clickCoordinates(WebElement element, int dx, int dy) {
        new Actions(driver).moveToElement(element).moveByOffset(dx, dy).click().perform();
    }
}

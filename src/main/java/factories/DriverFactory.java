// Details on how to run test scripts with different browsers from maven command line (and thus from CI)
// https://seleniumjava.com/2017/05/21/how-to-run-scripts-in-a-specific-browser-with-maven/amp/
// Driver should work with whatever browser passed to them (either via CI, CMD line or in rare cases - from tests(not recommended way)
// Thus browser is abstracted outside driver class.
// Driver constructor makes sure that it can work with both cases (both with default browser or when browser is passed to it)

package factories;

import choices.Browser;
import choices.Host;
import com.typesafe.config.Config;
import config.EnvFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;
import java.util.logging.Level;

@Slf4j
public class DriverFactory {
    private static Config config = EnvFactory.getInstance().getConfig();
    private static final Host HOST = Host.parse(config.getString("HOST"));
    private static final Browser BROWSER = Browser.parse(config.getString("BROWSER"));

    private DriverFactory() {
        throw new IllegalStateException("Static factory class");
    }

    public static WebDriver getDriver() {
        log.info("Getting driver for host: {}", HOST);
        switch (HOST) {
            case LOCALHOST:
                return getLocalWebDriver();
            case DOCKER_CONTAINER:
                // fall through - same options apply.
            case DOCKER_SELENIUM_GRID:
                return getRemoteWebDriver();
            default:
                throw new IllegalStateException(String.format("%s is not a valid HOST choice. Pick your HOST from %s.", HOST, java.util.Arrays.asList(Host.values())));
        }
    }

    private static WebDriver getLocalWebDriver() {
        setSeleniumLogs();
        log.info("Getting driver for browser: {}", BROWSER);
        switch (BROWSER) {
            case CHROME:
                setChromeDriverLogs();
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(CapabilitiesFactory.getChromeOptions());
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(CapabilitiesFactory.getFirefoxOptions());
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case OPERA:
                WebDriverManager.operadriver().setup();
                return new OperaDriver();
            default:
                throw new IllegalStateException(String.format("%s is not a valid browser choice. Pick your browser from %s.", BROWSER, java.util.Arrays.asList(BROWSER.values())));
        }
    }

    private static WebDriver getRemoteWebDriver() {
        switch (BROWSER) {
            case CHROME:
                // fall - through. Same method for all browsers.
            case FIREFOX:
                // fall - through. Same method for all browsers.
            case OPERA:
                return new RemoteWebDriver(URLFactory.getHostURL(HOST), CapabilitiesFactory.getCapabilities(BROWSER));
            default:
                throw new IllegalStateException(String.format("%s is not a valid browser choice. Pick your browser from %s.", BROWSER, java.util.Arrays.asList(BROWSER.values())));
        }
    }

    // To get rid of selenium logs (or not)
    private static void setSeleniumLogs() {
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.parse(config.getString("SELENIUM_LOG_LEVEL")));
    }

    /**
     * To get rid of chrome driver logs.
     */
    private static void setChromeDriverLogs() {
        System.setProperty("webdriver.chrome.silentOutput", config.getString("SILENT_DRIVER_LOGS"));
    }

    public static void setDriverTimeouts(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(180));
    }
}

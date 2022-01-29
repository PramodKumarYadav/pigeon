/**
 * Some references for debugging.
 * chromeOptions.addArguments("--verbose");
 * chromeOptions.addArguments("--whitelisted-ips=''");
 * chromeOptions.addArguments("--no-sandbox");
 * chromeOptions.addArguments("--disable-dev-shm-usage");
 * System.setProperty("webdriver.chrome.whitelistedIps", "");
 * //add this line for verbose logging
 * System.setProperty("webdriver.chrome.verboseLogging", "true");
 * chromeOptions.addArguments("--proxy-server='direct://'");
 * chromeOptions.addArguments("--proxy-bypass-list=*");
 * chromeOptions.setProxy(null);
 * https://www.selenium.dev/documentation/en/webdriver/page_loading_strategy/
 * <p>
 * these lines enable debugging
 * System.setProperty("webdriver.chrome.logfile", "/usr/src/chromedrivergrid.log");
 * System.setProperty("webdriver.chrome.verboseLogging", "true");
 * WebDriverManager.globalConfig().setProxy("https://proxy.internal.organisation-name.org:8080");
 */

package factories;

import choices.Browser;
import com.typesafe.config.Config;
import config.EnvFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

import static java.lang.Boolean.parseBoolean;

public class CapabilitiesFactory {
    private static Config config = EnvFactory.getInstance().getConfig();

    private static final boolean HEADLESS = parseBoolean(config.getString("HEADLESS"));
    private static final boolean acceptInsecureCertsFlag = parseBoolean(config.getString("acceptInsecureCertificates"));
    private static final String seleniumLogLevel = config.getString("SELENIUM_LOG_LEVEL");
    private static final String DOWNLOADS_DIR = config.getString("DOWNLOADS_DIR");

    public static Capabilities getCapabilities(Browser browser) {
        switch (browser) {
            case CHROME:
                return getChromeOptions();
            case FIREFOX:
                return getFirefoxOptions();
            case OPERA:
                return getOperaOptions();
            default:
                throw new IllegalStateException(String.format("%s is not a valid browser choice. Pick your browser from %s.", browser, java.util.Arrays.asList(browser.values())));
        }
    }

    public static ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setAcceptInsecureCerts(acceptInsecureCertsFlag);
        chromeOptions.setHeadless(HEADLESS);
//        chromeOptions.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        chromeOptions.addArguments("--no-sandbox"); // overcome limited resource problems and a must-have step to run tests in docker pipeline
        chromeOptions.addArguments("--window-size=1920,1080");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.addArguments("--enable-javascript");

        Map<String, Object> prefs = new Hashtable<String, Object>();
        prefs.put("plugins.always_open_pdf_externally", true);
        prefs.put("download.default_directory", String.format("%s\\%s", System.getProperty("user.dir"), DOWNLOADS_DIR));
        chromeOptions.setExperimentalOption("prefs", prefs);

        // To get error console logs
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.parse(seleniumLogLevel));
        chromeOptions.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        return chromeOptions;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setAcceptInsecureCerts(acceptInsecureCertsFlag);
        firefoxOptions.setHeadless(HEADLESS);
        return firefoxOptions;
    }

    public static OperaOptions getOperaOptions() {
        return new OperaOptions();
    }
}

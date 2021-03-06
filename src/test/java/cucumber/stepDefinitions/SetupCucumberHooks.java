package cucumber.stepDefinitions;

import factories.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import testextensions.PublishTestResults;

@Slf4j
public class SetupCucumberHooks {
    private static WebDriver driver;

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver();
        DriverFactory.setDriverTimeouts(driver);
    }

    @After
    public void postProcessing() {
        driver.close();
        driver.quit();
        log.info("tear down complete");

        PublishTestResults.toElastic();
        log.info("Published results to Elastic");
    }

    public static WebDriver getDriver() {
        return driver;
    }
}

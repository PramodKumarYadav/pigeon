package cucumber.stepDefinitions.commonSections;

import com.typesafe.config.Config;
import config.EnvFactory;
import factories.DriverFactory;
import fedex.commonSections.FedExAlertSection;
import fedex.pages.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import testextensions.PublishTestResults;
import testextensions.TestExecutionLifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Tag("regression")
@ExtendWith(TestExecutionLifecycle.class)
class FedExAlertSectionSteps {
    WebDriver driver;

    private HomePage homePage;
    private FedExAlertSection fedExAlertSection;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String FEDEX_ALERT_TEXT = config.getString("FEDEX_ALERT_TEXT");
    private static final String HOME_PAGE_TITLE = config.getString("HOME_PAGE_TITLE");

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver();
        DriverFactory.setDriverTimeouts(driver);

        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

    }

    @After
    public void postProcessing() {
        driver.close();
        driver.quit();
        log.info("tear down complete");

        PublishTestResults.toElastic();
        log.info("Published results to Elastic");
    }

    @Given("User is on Home Page")
    public void user_is_on_home_page() {
        homePage.navigateToHomePageURL();
        assertEquals(HOME_PAGE_TITLE, homePage.getHomePageTitle());
    }

    @When("User looks at the top section of Home Page")
    public void user_looks_at_the_top_section_of_home_page() {
        fedExAlertSection = new FedExAlertSection(driver);
    }

    @Then("User sees a FedEx alert")
    public void user_sees_a_fed_ex_alert() {
        assertEquals(FEDEX_ALERT_TEXT, fedExAlertSection.getFedExAlertText());
    }
}

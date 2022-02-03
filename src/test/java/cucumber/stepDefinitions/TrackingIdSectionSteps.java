package cucumber.stepDefinitions;

import factories.DriverFactory;
import fedex.commonSections.TrackingIdSection;
import fedex.pages.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import testextensions.PublishTestResults;
import testextensions.TestExecutionLifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(TestExecutionLifecycle.class)
public class TrackingIdSectionSteps {
    WebDriver driver;

    private HomePage homePage;
    private TrackingIdSection trackingIdSection;

    @Before
    public void setUp() {
        this.driver = DriverFactory.getDriver();
        DriverFactory.setDriverTimeouts(driver);

        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        trackingIdSection = new TrackingIdSection(driver);
    }

    @After
    public void postProcessing() {
        driver.close();
        driver.quit();
        log.info("tear down complete");

        PublishTestResults.toElastic();
        log.info("Published results to Elastic");
    }

    @Given("User is looking at tracking id section")
    public void user_is_looking_at_tracking_id_section() {
        homePage.navigateToHomePageURL();
    }

    @Given("User does not set any tracking id in the input field")
    public void user_does_not_set_any_tracking_id_in_the_input_field() {
        trackingIdSection.setTrackingIdInputField("");
    }

    @When("User clicks on the TRACK button")
    public void user_clicks_on_the_track_button() {
        trackingIdSection.clickTrackButton();
    }

    @Then("User gets a warning to - at least enter one tracking number")
    public void user_gets_a_warning_to_at_least_enter_one_tracking_number() {
        assertEquals("Please enter at least one tracking number.", trackingIdSection.getTrackingIdValidationMessage());
    }
}

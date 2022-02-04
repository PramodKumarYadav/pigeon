package cucumber.hooks;

import fedex.commonSections.TrackingIdSection;
import fedex.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import testextensions.TestExecutionLifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(TestExecutionLifecycle.class)
public class TrackingIdSectionSteps {
    WebDriver driver;

    private HomePage homePage;
    private TrackingIdSection trackingIdSection;

    @Given("User is looking at tracking id section")
    public void user_is_looking_at_tracking_id_section() {
        driver = SetupCucumber.getDriver();

        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        trackingIdSection = new TrackingIdSection(driver);
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

package cucumber.stepDefinitions;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import fedex.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import testextensions.TestExecutionLifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(TestExecutionLifecycle.class)
public class HomePageSteps {
    WebDriver driver;

    private HomePage homePage;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String CAMPAIGN_PAGE_URL = config.getString("CAMPAIGN_PAGE_URL");
    private static final String HOME_PAGE_TITLE = config.getString("HOME_PAGE_TITLE");
    private static final String FEDEX_ALERT_TEXT = config.getString("FEDEX_ALERT_TEXT");

    @Given("User is on Home Page")
    public void user_is_on_home_page() {
        driver = SetupCucumberHooks.getDriver();

        homePage = new HomePage(driver);
        pageActions = new PageActions(driver);

        homePage.navigateToHomePageURL();
    }

    @Then("verify that the title is correct")
    public void verify_that_the_title_is_correct() {
        assertEquals(HOME_PAGE_TITLE, homePage.getHomePageTitle());
    }

    @When("User Clicks on Find out more button on Get more delivery choices")
    public void user_clicks_on_find_out_more_button_on_get_more_delivery_choices() {
        homePage.clickDeliveryChoicesFindOutMoreButton();
    }

    @Then("User is redirected to CAMPAIGN_PAGE_URL")
    public void user_is_redirected_to_campaign_page_url() {
        assertEquals(CAMPAIGN_PAGE_URL , pageActions.getCurrentPageURL());
    }
}

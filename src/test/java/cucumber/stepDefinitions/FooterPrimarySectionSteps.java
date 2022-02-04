package cucumber.stepDefinitions;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import fedex.commonSections.FooterPrimarySection;
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
public class FooterPrimarySectionSteps {
    WebDriver driver;

    private HomePage homePage;
    private FooterPrimarySection footerPrimarySection;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String FEDEX_ABOUT_US_URL = config.getString("FEDEX_ABOUT_US_URL");

    @Given("User is looking at Primary Footer section")
    public void user_is_looking_at_primary_footer_section() {
        driver = SetupCucumberHooks.getDriver();

        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        footerPrimarySection = new FooterPrimarySection(driver);
        pageActions = new PageActions(driver);
    }

    @When("User clicks on about fedex link")
    public void user_clicks_on_about_fedex_link() {
        footerPrimarySection.clickAboutFedExLink();
    }
    @Then("User is redirected to FEDEX_ABOUT_US_URL")
    public void user_is_redirected_to_fedex_about_us_url() {
        assertEquals(FEDEX_ABOUT_US_URL, pageActions.getCurrentPageURL());
    }
}

package cucumber.hooks;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import fedex.commonSections.FooterSocialSection;
import fedex.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import testextensions.TestExecutionLifecycle;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TestExecutionLifecycle.class)
public class FooterSocialSectionSteps {
    WebDriver driver;

    private HomePage homePage;
    private FooterSocialSection footerSocialSection;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String LINKEDIN_URL = config.getString("LINKEDIN_URL");

    @Given("User is looking at Social Footer section")
    public void user_is_looking_at_social_footer_section() {
        driver = SetupCucumber.getDriver();

        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        footerSocialSection = new FooterSocialSection(driver);
        pageActions = new PageActions(driver);

        homePage.navigateToHomePageURL();
    }

    @When("User clicks on LinkedIn icon button")
    public void user_clicks_on_linked_in_icon_button() {
        footerSocialSection.clickLinkedInLink();
    }

    @Then("User is redirected to LINKEDIN_URL")
    public void user_is_redirected_to_linkedin_url() {
        assertTrue(pageActions.getCurrentPageURL().contains(LINKEDIN_URL));
    }
}

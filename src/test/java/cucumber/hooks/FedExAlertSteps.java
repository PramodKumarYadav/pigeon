package cucumber.hooks;

import com.typesafe.config.Config;
import config.EnvFactory;
import fedex.commonSections.FedExAlertSection;
import fedex.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FedExAlertSteps {
    WebDriver driver;

    private HomePage homePage;
    private FedExAlertSection fedExAlertSection;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String FEDEX_ALERT_TEXT = config.getString("FEDEX_ALERT_TEXT");
    private static final String HOME_PAGE_TITLE = config.getString("HOME_PAGE_TITLE");

    @Given("User is on Home Page again")
    public void user_is_on_home_page_again() {
        driver = SetupCucumberHooks.getDriver();

        homePage = new HomePage(driver);

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

package cucumber.hooks;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import fedex.commonSections.NavigationSection;
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
public class NavigationSectionSteps {
    WebDriver driver;

    private HomePage homePage;
    private NavigationSection navigationSection;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String HOME_PAGE_TITLE = config.getString("HOME_PAGE_TITLE");
    private static final String LOGIN_PAGE_TITLE = config.getString("LOGIN_PAGE_TITLE");

    @Given("User is looking at navigation menu section")
    public void user_is_looking_at_navigation_menu_section() {
        driver = SetupCucumber.getDriver();

        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        navigationSection = new NavigationSection(driver);
        pageActions = new PageActions(driver);
    }

    @When("User clicks on the FedEx home page button")
    public void user_clicks_on_the_fed_ex_home_page_button() {
        navigationSection.clickFedExHomePageButton();
    }

    @Then("User is redirected to HOME_PAGE_TITLE")
    public void user_is_redirected_to_home_page_title() {
        assertEquals(HOME_PAGE_TITLE, homePage.getHomePageTitle());
    }

    @When("User clicks on the FedEx sign up login button")
    public void user_clicks_on_the_fed_ex_sign_up_login_button() {
        navigationSection.clickFedExSignUpLoginButton();
    }

    @When("User clicks on sub menu login button")
    public void user_clicks_on_sub_menu_login_button() {
        navigationSection.clickLoginButton();
    }

    @Then("User is redirected to LOGIN_PAGE_TITLE")
    public void user_is_redirected_to_login_page_title() {
        assertEquals(LOGIN_PAGE_TITLE, pageActions.getPageTitle());
    }

    @Then("User can verify four menu items Shipping_Tracking_Support_and_Account")
    public void user_can_verify_four_menu_items_shipping_tracking_support_and_account() {
        Integer TOTAL_MENUS = 4;
        assertEquals(TOTAL_MENUS, navigationSection.getMainMenuItems().size());
    }

    @When("User clicks on the menu at index {int}")
    public void user_clicks_on_the_menu_at_index(Integer menuIndex) {
        navigationSection.clickMenuItem(menuIndex);
    }

    @Then("User can see the {word} open")
    public void user_can_see_the_shipping_open(String menuName) {
        log.info("MenuName is: {}", menuName);
    }

    @Then("User can see total {int} sub items at index {int}")
    public void user_can_see_total_sub_items_at_index(Integer expTotalSubItems, Integer menuIndex) {
        assertEquals(expTotalSubItems, navigationSection.getSubMenuItems(menuIndex).size());
    }

    @When("User clicks on the the Shipping menu {int}")
    public void user_clicks_on_the_the_shipping_menu(Integer menuIndex) {
        navigationSection.clickMenuItem(menuIndex);
    }

    @When("For menu at {int} User clicks sub item {int}")
    public void for_menu_at_user_clicks_sub_item(Integer menuIndex, Integer subMenuIndex) {
        navigationSection.clickSubMenuItem(menuIndex, subMenuIndex);
    }
}

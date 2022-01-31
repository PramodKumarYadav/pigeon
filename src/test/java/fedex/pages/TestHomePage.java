package fedex.pages;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import testextensions.TestExecutionLifecycle;
import testextensions.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regression")
@ExtendWith(TestExecutionLifecycle.class)
class TestHomePage extends TestSetup {
    private HomePage homePage;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String CAMPAIGN_PAGE_URL = config.getString("CAMPAIGN_PAGE_URL");
    private static final String HOME_PAGE_TITLE = config.getString("HOME_PAGE_TITLE");

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver).
                navigateToHomePageURL().
                and().
                acceptAllCookies();

        pageActions = new PageActions(driver);
    }

    @Tag("smokeTest")
    @Test
    void assertThatHomePageTitleIsCorrect() {
        assertEquals(HOME_PAGE_TITLE, homePage.getHomePageTitle());
    }

    @Test
    @DisplayName("Testing a button from main section on Home Page")
    void assertThatClickingOnDeliveryChoicesFindOutMoreButtonRedirectsToTheCorrectPage() {
        homePage.clickDeliveryChoicesFindOutMoreButton();
        assertEquals(CAMPAIGN_PAGE_URL, pageActions.getCurrentPageURL());
    }
}

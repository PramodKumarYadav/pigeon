package fedex.commonSections;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import fedex.pages.HomePage;
import testextensions.TestExecutionLifecycle;
import testextensions.TestSetup;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regression")
@ExtendWith(TestExecutionLifecycle.class)
class TestFooterSocialSection extends TestSetup {
    private HomePage homePage;
    private FooterSocialSection footerSocialSection;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String LINKEDIN_URL = config.getString("LINKEDIN_URL");

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver).
                navigateToHomePageURL().
                and().
                acceptAllCookies();

        footerSocialSection = new FooterSocialSection(driver);
        pageActions = new PageActions(driver);
    }

    @Test
    void assertThatTryingToTrackWithEmptyTrackingIdResultsInAWarningToTheUser() {
        footerSocialSection.clickLinkedInLink();
        assertTrue(pageActions.getCurrentPageURL().contains(LINKEDIN_URL));
    }
}

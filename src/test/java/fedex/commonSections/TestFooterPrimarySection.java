package fedex.commonSections;

import com.typesafe.config.Config;
import config.EnvFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import fedex.pages.HomePage;
import testextensions.TestExecutionLifecycle;
import testextensions.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regression")
@ExtendWith(TestExecutionLifecycle.class)
class TestFooterPrimarySection extends TestSetup {
    private FooterPrimarySection footerPrimarySection;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String FEDEX_ABOUT_US_URL = config.getString("FEDEX_ABOUT_US_URL");

    @BeforeEach
    void initialize() {
        HomePage homePage = new HomePage(driver).
                navigateToHomePageURL().
                acceptAllCookies();

        footerPrimarySection = new FooterPrimarySection(driver);
    }

    @Test
    void assertThatTryingToTrackWithEmptyTrackingIdResultsInAWarningToTheUser() {
        footerPrimarySection.clickAboutFedExLink();
        assertEquals(FEDEX_ABOUT_US_URL, footerPrimarySection.getCurrentURL());
    }
}

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
class TestFedExAlertSection extends TestSetup {
    private HomePage homePage;
    private FedExAlertSection fedExAlertSection;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String FEDEX_ALERT_TEXT = config.getString("FEDEX_ALERT_TEXT");

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver).
                navigateToHomePageURL().
                and().
                acceptAllCookies();

        fedExAlertSection = new FedExAlertSection(driver);
    }

    @Tag("smokeTest")
    @Test
    void assertThatFedExCovidAlertTextIsCorrect() {
        assertEquals(FEDEX_ALERT_TEXT, fedExAlertSection.getFedExAlertText());
    }
}

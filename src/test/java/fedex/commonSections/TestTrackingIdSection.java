package fedex.commonSections;

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
public class TestTrackingIdSection extends TestSetup {
    private HomePage homePage;
    private TrackingIdSection trackingIdSection;

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        trackingIdSection = new TrackingIdSection(driver);
    }

    @Test
    void assertThatTryingToTrackWithEmptyTrackingIdResultsInAWarningToTheUser() {
        trackingIdSection
                .setTrackingIdInputField("")
                .clickTrackButton();
        assertEquals("Please enter at least one tracking number.", trackingIdSection.getTrackingIdValidationMessage());
    }
}

package pages.home;

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
public class TestHomePage extends TestSetup {
    private HomePage homePage;

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver);
        homePage.navigateToHomePageURL();
    }

    @Tag("smokeTest")
    @Test
    void assertThatHomePageTitleIsCorrect() {
        String homePageTitle = "Express Delivery, Courier & Shipping Services | FedEx United Kingdom";
        assertEquals(homePageTitle, homePage.getHomePageTitle());
    }

    @Tag("flaky")
    @DisplayName("A test to demonstrate that tags can be used to filter flaky, slow or smokeTests or regression tests.")
    @Test
    void aTestToDemonstrateHowToMarkFlakyOrSlowTests() {
        String homePageTitle = "Express Delivery, Courier & Shipping Services | FedEx United Kingdom";
        assertEquals(homePageTitle, homePage.getHomePageTitle());
    }
}

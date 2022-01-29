package pages.home;

import actions.PageActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import factories.DriverFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHomePage {
    private WebDriver driver = DriverFactory.getDriver();

    private HomePage homePage = new HomePage(driver);

    @BeforeEach
    void setUp() {
        DriverFactory.setDriverTimeouts(driver);
        homePage.navigateToHomePageURL();
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        PageActions.closeDriver(driver);
    }

    @Test
    void assertThatHomePageTitleIsCorrect() {
        String homePageTitle = "Express Delivery, Courier & Shipping Services | FedEx United Kingdom";
        assertEquals(homePageTitle, homePage.getHomePageTitle());
    }
}

package pages.home;

import actions.PageActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import factories.DriverFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFedExAlert {
    private WebDriver driver = DriverFactory.getDriver();

    private HomePage homePage = new HomePage(driver);
    private FedExAlert fedExAlert = new FedExAlert(driver);

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
    void assertThatFedExCovidAlertTextIsCorrect() {
        String alertText = "High volume of shipments and COVID 19 restrictions may cause delivery delays. " +
                "Before finalising your shipment, please see our latest service alert updates.";
        assertEquals(alertText, fedExAlert.getFedExAlertText());
    }
}

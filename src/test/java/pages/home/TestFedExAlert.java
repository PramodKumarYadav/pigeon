package pages.home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import testextensions.TestExecutionLifecycle;
import testextensions.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regression")
@ExtendWith(TestExecutionLifecycle.class)
public class TestFedExAlert extends TestSetup {
    private HomePage homePage;
    private FedExAlert fedExAlert;

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver);
        fedExAlert = new FedExAlert(driver);

        homePage.navigateToHomePageURL();
    }

    @Tag("smokeTest")
    @Test
    void assertThatFedExCovidAlertTextIsCorrect() {
        String alertText = "High volume of shipments and COVID 19 restrictions may cause delivery delays. " +
                "Before finalising your shipment, please see our latest service alert updates.";
        assertEquals(alertText, fedExAlert.getFedExAlertText());
    }
}

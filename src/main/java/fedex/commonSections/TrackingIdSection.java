package fedex.commonSections;

import actions.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrackingIdSection {
    private PageActions pageActions;

    public TrackingIdSection(WebDriver driver) {
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(driver);
    }

    @FindBy(css = "input[name='trackingnumber']")
    private WebElement trackingIdInputField;

    @FindBy(css = "button[id='btnSingleTrack']")
    private WebElement trackButton;

    @FindBy(css = "span[id*='fxg-validation-']")
    private WebElement trackingIdValidationMessage;

    public TrackingIdSection setTrackingIdInputField(String value) {
        pageActions.setTextField(trackingIdInputField, value);
        return this;
    }

    public TrackingIdSection clickTrackButton() {
        pageActions.clickViaJavaScript(trackButton);
        return this;
    }

    public String getTrackingIdValidationMessage() {
        return pageActions.getTextFromElement(trackingIdValidationMessage);
    }
}

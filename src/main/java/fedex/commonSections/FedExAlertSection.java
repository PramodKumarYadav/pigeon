package fedex.commonSections;

import actions.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FedExAlertSection {
    private PageActions pageActions;

    public FedExAlertSection(WebDriver driver) {
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(driver);
    }

    @FindBy(css = "div[class='fxg-alert__text fxg-alert__text-spacer'] > p")
    private WebElement fedExAlert;


    public String getFedExAlertText(){
        return pageActions.getTextFromElement(fedExAlert);
    }
}

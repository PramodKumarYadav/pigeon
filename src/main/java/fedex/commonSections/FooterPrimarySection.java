package fedex.commonSections;

import actions.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FooterPrimarySection {
    private PageActions pageActions;

    public FooterPrimarySection(WebDriver driver) {
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(driver);
    }

    @FindBy(css = "a[aria-label='About FedEx']")
    private WebElement aboutFedExLink;

    public FooterPrimarySection clickAboutFedExLink() {
        pageActions.clickViaJavaScript(aboutFedExLink);
        return this;
    }
}

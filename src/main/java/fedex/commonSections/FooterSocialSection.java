package fedex.commonSections;

import actions.PageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FooterSocialSection {
    private PageActions pageActions;

    public FooterSocialSection(WebDriver driver) {
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(driver);
    }

    @FindBy(css = "img[class='fxg-icon fxg-icon--linkedin   ']")
    private WebElement linkedInLink;

    public FooterSocialSection clickLinkedInLink() {
        pageActions.clickViaJavaScript(linkedInLink);
        return this;
    }
}

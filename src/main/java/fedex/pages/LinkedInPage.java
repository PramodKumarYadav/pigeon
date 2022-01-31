package fedex.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedInPage {
    public LinkedInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "h1[class='authwall-join-form__title']")
    private WebElement joinLinkedInHeader;

    public WebElement getLinkedInSearchInputField() {
        return joinLinkedInHeader;
    }
}

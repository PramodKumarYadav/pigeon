package fedex.pages;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String HOME_PAGE_URL = config.getString("HOME_PAGE_URL");

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(driver);
    }

    @FindBy(css = "button[class='fxg-cookie-consent__accept fxg-button fxg-button--orange js-fxg-cookie-save is-save-all']")
    private WebElement acceptAllCookies;

    @FindBy(css = "a[data-analytics=\"foc|Find Out More\"]")
    private WebElement deliveryChoicesFindOutMoreButton;

    public HomePage navigateToHomePageURL() {
        pageActions.waitForPageToLoad();
        pageActions.getPageWithRetry(HOME_PAGE_URL, By.cssSelector("button[id='btnSingleTrack']"));
        return this;
    }

    public String getHomePageTitle() {
        return pageActions.getPageTitle();
    }

    public HomePage acceptAllCookies() {
        pageActions.scrollIntoViewAndCenter(acceptAllCookies);
        pageActions.scrollIntoViewAndClick(acceptAllCookies);
        return this;
    }

    /** sugar syntax */
    public HomePage and() {
        return this;
    }

    public HomePage clickDeliveryChoicesFindOutMoreButton() {
        pageActions.clickViaJavaScript(deliveryChoicesFindOutMoreButton);
        return this;
    }
}

package pages.home;

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

    @FindBy(css = "div[class='fxg-alert__text fxg-alert__text-spacer'] > p")
    private WebElement fedExAlert;


    public HomePage navigateToHomePageURL() {
        pageActions.getPageWithRetry(HOME_PAGE_URL, By.cssSelector("button[id='btnSingleTrack']"));
        return this;
    }

    public String getHomePageTitle() {
        return pageActions.getPageTitle();
    }
}

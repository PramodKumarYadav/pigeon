package fedex.commonSections;

import actions.PageActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class NavigationSection {
    private PageActions pageActions;

    public NavigationSection(WebDriver driver) {
        PageFactory.initElements(driver, this);
        pageActions = new PageActions(driver);
    }

    @FindBy(css = "a[class='fxg-header__logo_wrapper fxg-keyboard']")
    private WebElement fedExHomePageButton;

    @FindBy(css = "a[class=\"fxg-link fxg-dropdown-js fxg-keyboard\"]")
    private WebElement fedExSignUpLoginButton;

    @FindBy(css = "a[aria-label='LOG IN'][title='LOG IN']")
    private WebElement loginSubMenuButton;
    
    public NavigationSection clickFedExHomePageButton() {
        pageActions.clickViaJavaScript(fedExHomePageButton);
        return this;
    }

    public NavigationSection clickFedExSignUpLoginButton() {
        pageActions.clickViaJavaScript(fedExSignUpLoginButton);
        return this;
    }

    public NavigationSection clickLoginButton() {
        pageActions.scrollIntoViewAndClick(loginSubMenuButton);
        return this;
    }

    public NavigationSection clickMenuItem(Integer menuItemNr) {
        WebElement menuItem = getMenuItem(menuItemNr);
        pageActions.scrollIntoViewAndClick(menuItem);
        return this;
    }

    public List<WebElement> getMainMenuItems() {
        return pageActions.findElementsByCssSelector("ul[class='fxg-dropdown fxg-global-nav'] > div[class='dropdown section']");
    }

    private WebElement getMenuItem(Integer menuItemNr) {
        return getMainMenuItems().get(menuItemNr);
    }

    /** Sugar syntax */
    public NavigationSection and() {
        return this;
    }

    /** Sugar syntax */
    public NavigationSection andThen() {
        return this;
    }

    public NavigationSection clickSubMenuItem(Integer menuItemNr, Integer subMenuItemNr) {
        WebElement subMenuItem = getSubMenuItems(menuItemNr).get(subMenuItemNr);
        pageActions.scrollIntoViewAndClick(subMenuItem);
        return this;
    }

    public List<WebElement> getSubMenuItems(Integer menuItemNr) {
        return getMenuItem(menuItemNr).findElements(By.cssSelector(" ul[class='fxg-dropdown fxg-global-nav'] > div[class='dropdown section'] > li > div > div > div"));
    }
}

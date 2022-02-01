package fedex.commonSections;

import actions.PageActions;
import com.typesafe.config.Config;
import config.EnvFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import fedex.pages.HomePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import testextensions.TestExecutionLifecycle;
import testextensions.TestSetup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regression")
@ExtendWith(TestExecutionLifecycle.class)
@DisplayName("Demonstrate that user can access Navigation Menus and click on sub menus to redirect to expected pages")
public class TestNavigationSection extends TestSetup {
    private HomePage homePage;
    private NavigationSection navigationSection;
    private PageActions pageActions;

    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String HOME_PAGE_TITLE = config.getString("HOME_PAGE_TITLE");
    private static final String LOGIN_PAGE_TITLE = config.getString("LOGIN_PAGE_TITLE");

    @BeforeEach
    void initialize() {
        homePage = new HomePage(driver).
                navigateToHomePageURL()
                .and()
                .acceptAllCookies();

        navigationSection = new NavigationSection(driver);
        pageActions = new PageActions(driver);
    }

    @Nested
    class FedExHomeMenu {
        @Test
        void assertThatFedExHomePageNavigationMenuButtonReturnsTheHomePage() {
            navigationSection.clickFedExHomePageButton();
            assertEquals(HOME_PAGE_TITLE, homePage.getHomePageTitle());
        }
    }

    @Nested
    class SignUpLoginMenu {
        @Test
        void assertThatSignUpLoginNavigationMenuButtonOpensSubItemsAndYouCanClickASubItem() {
            navigationSection.clickFedExSignUpLoginButton().
                    andThen().
                    clickLoginButton();
            assertEquals(LOGIN_PAGE_TITLE, pageActions.getPageTitle());
        }
    }

    @Nested
    class ShippingTrackingSupportAccountMenu {
        @Test
        void assertThatThereAreFourNavigationMenus() {
            Integer TOTAL_MENUS = 4;
            assertEquals(TOTAL_MENUS, navigationSection.getMainMenuItems().size());
        }

        @DisplayName("Ideally we would have separate nested classes for each of them as shown in the next " +
                "nested sub class ShippingMenu - and with asserts. This test is just for demo to show" +
                "that these areas are important to cover in their own sub sections.")
        @ParameterizedTest(name = "Navigation Menu Item - {1}")
        @CsvSource({"0, SHIPPING"
                , "1, TRACKING"
                , "2, SUPPORT"
                , "3, ACCOUNT"
        })
        void assertThatAllMenusAreAccessible(Integer position, String menuName) {
            navigationSection.clickMenuItem(position);
        }
    }

    @Nested
    class ShippingMenu {
        @Test
        void assertThatShippingMenuDropDownButtonWorks() {
            Integer positionShipping = 0;
            Integer TOTAL_SUB_ITEMS = 9;
            navigationSection.clickMenuItem(positionShipping);
            assertEquals(TOTAL_SUB_ITEMS, navigationSection.getSubMenuItems(positionShipping).size());
        }

        @Test
        void assertThatClickingSubMenuItemShipWithAccountInShippingMenuTakesUserToLoginPage() {
            Integer positionShipping = 0;
            Integer positionShipWithAccount = 0;
            navigationSection.clickMenuItem(positionShipping).
                    andThen().
                    clickSubMenuItem(positionShipping, positionShipWithAccount);
            assertEquals(LOGIN_PAGE_TITLE, pageActions.getPageTitle());
        }
    }
}

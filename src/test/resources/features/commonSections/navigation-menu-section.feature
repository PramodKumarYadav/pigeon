Feature: Navigation menu section of FedEx application

    Background:
        Given User is looking at navigation menu section

    Scenario: Validate that FedEx home page button takes user to home page
        When User clicks on the FedEx home page button
        Then User is redirected to HOME_PAGE_TITLE

    Scenario: Validate that clicking on login menu under Signup login navigation menu takes user to login page
        When User clicks on the FedEx sign up login button
        And User clicks on sub menu login button
        Then User is redirected to LOGIN_PAGE_TITLE

    Scenario: Validate that there are four navigation menus Shipping_Tracking_Support_and_Account
        Then User can verify four menu items Shipping_Tracking_Support_and_Account

    Scenario Outline: Validate that user can navigate the four navigation menus Shipping_Tracking_Support_and_Account
        When User clicks on the menu at index <MenuIndex>
        Then User can see the <MenuName> open
        Examples: Menu items available on navigation Menu
            | MenuIndex | MenuName |
            | 0         | SHIPPING |
            | 1         | TRACKING |
            | 2         | SUPPORT  |
            | 3         | ACCOUNT  |

    Scenario Outline: Validate that Shipping menu button works and there are total 9 sub items
        When User clicks on the menu at index <MenuIndex>
        Then User can see total <TotalSubItems> sub items at index <MenuIndex>
        Examples: Menu items available on navigation Menu
            | MenuIndex | TotalSubItems |
            | 0         | 9 |

    Scenario Outline: Validate that clicking on Sub menu ship with account takes user to login page
        When User clicks on the the Shipping menu <MenuIndex>
        And For menu at <MenuIndex> User clicks sub item <SubMenuIndex>
        Then User is redirected to LOGIN_PAGE_TITLE
        Examples: Menu items available on navigation Menu
            | MenuIndex | SubMenuIndex |
            | 0         | 0 |

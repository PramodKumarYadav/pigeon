Feature: Home Page validation

Scenario: Get more delivery choices: Find out more
    Given User is on Home Page
    When User Clicks on Find out more button on Get more delivery choices
    Then User is redirected to CAMPAIGN_PAGE_URL

Scenario: Get more delivery choices: Find out more: again
    Given User is on Home Page
    When User Clicks on Find out more button on Get more delivery choices
    Then User is redirected to CAMPAIGN_PAGE_URL

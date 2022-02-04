Feature: Home Page of FedEx application

    Background:
        Given User is on Home Page

    Scenario: Verify the page title
        Then verify that the title is correct

    Scenario: Get more delivery choices: Find out more
        When User Clicks on Find out more button on Get more delivery choices
        Then User is redirected to CAMPAIGN_PAGE_URL

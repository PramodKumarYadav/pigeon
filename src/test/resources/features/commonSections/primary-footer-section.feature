Feature: Common primary footer section of FedEx application

    Scenario: Validate Primary footer section
        Given User is looking at Primary Footer section
        When User clicks on about fedex link
        Then User is redirected to FEDEX_ABOUT_US_URL

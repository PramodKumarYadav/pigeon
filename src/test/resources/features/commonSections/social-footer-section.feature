Feature: Common social footer section of FedEx application

    Scenario: Validate Social footer section
        Given User is looking at Social Footer section
        When User clicks on LinkedIn icon button
        Then User is redirected to LINKEDIN_URL

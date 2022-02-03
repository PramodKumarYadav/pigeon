Feature: Tracking id section of FedEx application

    Scenario: Validate tracking id section
        Given User is looking at tracking id section
        And User does not set any tracking id in the input field
        When User clicks on the TRACK button
        Then User gets a warning to - at least enter one tracking number

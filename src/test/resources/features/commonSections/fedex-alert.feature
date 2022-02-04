Feature: FedEx alert section

    Scenario: Validate FedEx alert section
        Given User is looking at the top alert section
        When User checks for an alert message
        Then User sees a FedEx alert

Feature: FedEx alert section

    Scenario: Validate FedEx alert section
        Given User is on Home Page again
        When User looks at the top section of Home Page
        Then User sees a FedEx alert

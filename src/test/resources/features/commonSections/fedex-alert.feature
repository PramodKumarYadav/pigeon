Feature: FedEx alert section validation

    Background:
        Given User is on Home Page again

    Scenario: Validate FedEx alert section
        When User looks at the top section of Home Page
        Then User sees a FedEx alert

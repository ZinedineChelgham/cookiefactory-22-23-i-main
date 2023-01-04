Feature: Get order history of a client

  Background:
    Given a existing client with email "client@mail.com"
    And a store "MyStore"


    Scenario:
      Given previous 3 orders ordered by user with email "client@mail.com"
      When the client "client@mail.com" get is history
      Then the client get his 3 previous orders

  Scenario:
    When the client "not_a_client@mail.com" get is history
    Then the client have an error

  Scenario:
    Given no order ordered by user with email "client@mail.com"
    When the client "client@mail.com" get is history
    Then the client get his 0 previous orders
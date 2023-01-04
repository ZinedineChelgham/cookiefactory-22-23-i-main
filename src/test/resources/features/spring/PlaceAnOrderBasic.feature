Feature: Place an order

  Background:
    Given a store named "MyStore" exists
    And a cookie recipe named "Chocolala" exists
    And an available cook "Thomas" in the store

  Scenario: Create an order
    Given a list of stores
    When the client select the store "MyStore" to make his order with id 0
    Then the order is create in the repository 0
    And the order has the store "MyStore"
    And the order has the initial status "basket"


  Scenario: Add a cookie
    Given an empty order
    When the client add a cookie "Chocolala" in the order
    Then the order contains a cookie of the recipe "Chocolala"

  Scenario: Link an order with a new User
    Given an empty order
    When the client link his order with the mail "user@mail.com" and phone "0123456789"
    Then the order has a user with mail "user@mail.com" and phone "0123456789"

  Scenario: Validate order
    Given an order with the status "basket"
    And a chosen time to pickup
    And a user with mail "user@mail.com"
    When the client validate the order
    Then the order has the status "payed"



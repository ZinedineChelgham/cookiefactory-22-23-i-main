Feature: Manage Order

  Background:
    Given a cook "mike" with an order

  Scenario: start prepare order
    Given A order is payed
    When A cook starts the preparation
    Then The order has the Status "PREPARATION"

  Scenario: mark order as ready
    Given A order is in preparation
    When A cook marks the order as ready
    Then The order has the Status "READY"
    And The cook is available for a new order
    And The ingredients are removed from stock
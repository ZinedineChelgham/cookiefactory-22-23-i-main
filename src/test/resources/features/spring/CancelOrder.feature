Feature: Order cancellation

  Background:
    Given a recipe "MyRecipe" is made with ingredients "Floor" and "Egg", with flavour "Chocolate flavour" and with dough "Chocolate dough"
    Given a store "MyStore" with the 1 of these ingredients locked and 0 available
    Given a cook with an empty agenda

  Scenario: cancel an order before a cook start working on it
    Given A order with "PAYED" status
    And 1 cookie of the recipe "MyRecipe"
    And in the cook agenda
    When The client cancel the order
    Then the order status change to "CANCELLED"
    And the cook agenda is empty
    And there is 0 ingredient locked and 1 available of the recipe "MyRecipe" in the stock

  Scenario: cancel an order before a cook start working on it
    Given A order with "PREPARATION" status
    And 1 cookie of the recipe "MyRecipe"
    And in the cook agenda
    When The client cancel the order
    Then the order status change to "PREPARATION"
    And the cook agenda still contains the order
    And there is 1 ingredient locked and 0 available of the recipe "MyRecipe" in the stock









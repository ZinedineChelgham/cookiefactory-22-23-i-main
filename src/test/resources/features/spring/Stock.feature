Feature: Manage stock of store

  Background:
    Given an ingredient named "Egg" exists

  Scenario Outline: add ingredient to store
    Given an empty store named "Central"
    When the manager adds <amount_add> ingredient of the name "Egg" to store
    Then the store has <amount_final> ingredient of the name "Egg" in stock

    Examples:
      |amount_add|amount_final|
      |3         |3           |
      |12        |12          |
Feature: lock stock ingredients

  Background:
    Given a recipe "MyRecipe" is made with ingredients "Floor" and "Egg", with dough "Chocolate dough" and with flavour "Chocolate flavour"
    Given a store "MyStore" with the 10 of these ingredients

  Scenario Outline: Lock an order line
    Given an order A
    When the client add <quantity> cookies of the recipe "MyRecipe" in the order
    Then the store have <quantityInStock> of each ingredient of the recipe "MyRecipe" in stock
    And the store have <quantityLocked> of each ingredient of the recipe "MyRecipe" locked in stock
    Examples:
      | quantity | quantityInStock | quantityLocked |
      | 1        | 9               | 1              |
      | 10       | 0               | 10             |

  Scenario Outline: Lock an order line with not enough ingredients
    Given an order A
    When the client add <quantity> cookies of the recipe "MyRecipe" in the order
    Then the order line isn't add in the order
    And the store have <quantityInStock> of each ingredient of the recipe "MyRecipe" in stock
    And the store have <quantityLocked> of each ingredient of the recipe "MyRecipe" locked in stock

    Examples:
      | quantity | quantityInStock | quantityLocked |
      | 21       | 10              | 0              |
      | 11       | 10              | 0              |
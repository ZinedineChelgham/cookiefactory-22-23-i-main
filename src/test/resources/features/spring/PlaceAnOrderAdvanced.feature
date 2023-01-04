Feature: Place an order

  Background:
    Given an advanced store named "MyStore" exists
    And a chocolate cookie recipe named "Chocolala" exists
    And a vanilla cookie recipe named "Vanilala" exists


  Scenario Outline: Add multiple cookies
    Given an empty order without cookies
    When the client add <cookie_quantity> cookies <recipe_name> in the order
    Then the order contains <order_line_quantity> of the recipe <order_line_recipe_name>
    Examples:
      | cookie_quantity | recipe_name | order_line_quantity | order_line_recipe_name |
      | 1               | "Chocolala" | 1                   | "Chocolala"            |
      | 3               | "Chocolala" | 3                   | "Chocolala"            |


  Scenario Outline: Add multiple order lines
    Given an empty order without order lines
    When the client add <cookie_quantity_1> cookies <recipe_name_1> in the order
    And the client add <cookie_quantity_2> cookies <recipe_name_2> in the order
    Then the order has an order line that contain <order_line_quantity_1> of the recipe <order_line_recipe_name_1>
    And the order has an order line that contain <order_line_quantity_2> of the recipe <order_line_recipe_name_2>
    Examples:
      | cookie_quantity_1 | recipe_name_1 | cookie_quantity_2 | recipe_name_2 | order_line_quantity_1 | order_line_recipe_name_1 | order_line_quantity_2 | order_line_recipe_name_2 |
      | 1                 | "Chocolala"   | 1                 | "Vanilala"    | 1                     | "Chocolala"              | 1                     | "Vanilala"               |
      | 9                 | "Chocolala"   | 2                 | "Vanilala"    | 9                     | "Chocolala"              | 2                     | "Vanilala"               |


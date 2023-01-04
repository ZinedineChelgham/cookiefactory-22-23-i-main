Feature: manage recipes

  Scenario Outline: add recipes
    Given a list of recipes
    When someone add a new <recipe>
    Then the recipe list size is increment and the recipe is in the list

    Examples:
      | recipe        |
      | "choco blanc" |
      | "milk"        |

  Scenario Outline: remove recipes
    Given a list of recipes
    When someone remove a existing <recipe>
    Then the recipe list size is decrement and the recipe is not in the list

    Examples:
      | recipe        |
      | "choco blanc" |
      | "milk"        |
      | "potato"      |



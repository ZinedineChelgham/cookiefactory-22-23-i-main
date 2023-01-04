Feature: Select time to pickup order

  Background:
    Given a store "MyStore" open from 8 to 20
    Given a recipe "MyRecipe" which take 10 minutes to be prepared

  Scenario Outline: Select a correct time to pickup
    Given an order with <numberOfCookies> cookies of the recipe "MyRecipe"
    And a cook who work from <startWorkHour> to <endWorkHour>
    And with an empty CookScheduler
    When a client choose the time to pickup the <day>/<month>/<year> at <hour>:<minutes> for his order
    Then the order have the time to pickup corresponding to the <day>/<month>/<year> at <hour>:<minutes>
    And the cook scheduler of a the cook will have a new timeslot corresponding to the given order
    And the timeslot start the <day>/<month>/<year> at <startTimeHour>:<startTimeMinute>
    And the timeslot end the <day>/<month>/<year> at <hour>:<minutes>
    Examples:
      | numberOfCookies | startWorkHour | endWorkHour | day | month | year | hour | minutes | startTimeHour | startTimeMinute |
      | 1               | 8             | 18          | 2   | 1     | 2023 | 10   | 00      | 9             | 45              |
      | 10              | 8             | 18          | 2   | 1     | 2023 | 10   | 00      | 8             | 15              |
      | 3               | 8             | 18          | 2   | 1     | 2023 | 8    | 30      | 8             | 00              |

  Scenario Outline: Select an incorrect time to pickup
    Given an order with <numberOfCookies> cookies of the recipe "MyRecipe"
    And a cook who work from <startWorkHour> to <endWorkHour>
    And with an empty CookScheduler
    When a client choose the time to pickup the <day>/<month>/<year> at <hour>:<minutes> for his order
    Then the order have the time set to null
    And the cook scheduler of a the cook will not have a new timeslot corresponding to the given order

    Examples:
      | numberOfCookies | startWorkHour | endWorkHour | day | month | year | hour | minutes |
      | 1               | 8             | 18          | 2   | 1     | 2023 | 8    | 00      |
      | 1               | 8             | 18          | 2   | 1     | 2023 | 19   | 00      |
      | 1               | 6             | 18          | 2   | 1     | 2023 | 7    | 00      |
      | 1               | 8             | 22          | 2   | 1     | 2023 | 21   | 00      |

  Scenario Outline: Select a correct time to pickup with other order in the scheduler
    Given an order with <numberOfCookies> cookies of the recipe "MyRecipe"
    And a cook who work from <startWorkHour> to <endWorkHour>
    And with an CookScheduler with an order with <numberOfCookies> cookies of the recipe "MyRecipe" the <day>/<month>/<year> at <otherOrderHour>:<otherOrderMinutes>
    When a client choose the time to pickup the <day>/<month>/<year> at <hour>:<minutes> for his order
    Then the order have the time to pickup corresponding to the <day>/<month>/<year> at <hour>:<minutes>
    And the cook scheduler of a the cook will have a new timeslot corresponding to the given order
    And the timeslot start the <day>/<month>/<year> at <startTimeHour>:<startTimeMinute>
    And the timeslot end the <day>/<month>/<year> at <hour>:<minutes>
    Examples:
      | numberOfCookies | startWorkHour | endWorkHour | day | month | year | otherOrderHour | otherOrderMinutes | hour | minutes | startTimeHour | startTimeMinute |
      | 3               | 8             | 18          | 2   | 1     | 2023 | 9              | 30                |10    | 00      | 9             | 30              |
      | 3               | 8             | 18          | 2   | 1     | 2023 | 10             | 30                |10    | 00      | 9             | 30              |

  Scenario Outline: Select an incorrect time to pickup with other order in the scheduler
    Given an order with <numberOfCookies> cookies of the recipe "MyRecipe"
    And a cook who work from <startWorkHour> to <endWorkHour>
    And with an CookScheduler with an order with <numberOfCookies> cookies of the recipe "MyRecipe" the <day>/<month>/<year> at <otherOrderHour>:<otherOrderMinutes>
    When a client choose the time to pickup the <day>/<month>/<year> at <hour>:<minutes> for his order
    Then the order have the time set to null
    And the cook scheduler of a the cook will not have a new timeslot corresponding to the given order

    Examples:
      | numberOfCookies | startWorkHour | endWorkHour | day | month | year | otherOrderHour | otherOrderMinutes | hour | minutes |
      | 3               | 8             | 18          | 2   | 1     | 2023 | 10             | 00                |10    | 00      |
      | 3               | 8             | 18          | 2   | 1     | 2023 | 10             | 15                |10    | 00      |
      | 3               | 8             | 18          | 2   | 1     | 2023 | 9              | 45                |10    | 00      |
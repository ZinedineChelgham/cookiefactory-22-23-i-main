Feature: manage stores schedule

  Background:
    Given a store that want to change his schedule

  Scenario Outline: change opening hours of the store
    Given a store with a list of <nbOrder> orders <OpeningHour>,<OpeningMinute>,<ClosingHour>,<ClosingMinute>,<Order1Hour>,<Order2Hour>,<Order3Hour>
    When someone change the opening hour of the store <NewOpeningHour>, <NewOpeningMinute>
    Then the orders outside the schedule are delete <nbOrderLeft>

    Examples:
      | nbOrder        | OpeningHour  | OpeningMinute| ClosingHour  | ClosingMinute     | Order1Hour | Order2Hour | Order3Hour | NewOpeningHour | NewOpeningMinute | nbOrderLeft |
      | 3              | 5            | 20           | 19           | 20                | 9          | 12         | 18         | 10             | 10               | 2           |
      | 3              | 6            | 20           | 19           | 20                | 9          | 10         | 18         | 11             | 10               | 1           |

  Scenario Outline: change closing hours of the store
    Given a store with a list of <nbOrder> orders <OpeningHour>,<OpeningMinute>,<ClosingHour>,<ClosingMinute>,<Order1Hour>,<Order2Hour>,<Order3Hour>
    When someone change the closing hour of the store <NewClosingHour>, <NewClosingMinute>
    Then the orders outside the schedule are delete <nbOrderLeft>

    Examples:
      | nbOrder        | OpeningHour  | OpeningMinute| ClosingHour  | ClosingMinute   | Order1Hour | Order2Hour | Order3Hour | NewClosingHour | NewClosingMinute | nbOrderLeft |
      | 3              | 5            | 20           | 20           | 20              | 10         | 11         | 19         | 15             | 10               | 2           |
      | 3              | 6            | 20           | 20           | 20              | 10         | 17         | 18         | 12             | 10               | 1           |
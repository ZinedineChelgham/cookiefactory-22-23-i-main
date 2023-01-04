Feature: send sms past 5min or 1 hour if the client didnt come
  Every client have to get a remind after 5min order after pickup time
  Or after 1 hours after pickup time

  Background:
    Given an order id : 12800; and a pickup time for 21 june 2022 at 15:30 pm for a client

  Scenario Outline:
    Given this order finished and not delivered yet
    When its pass <time> minutes after the pickup time
    Then client recieve message

    Examples:
      | time |
      | 5   |

  Scenario Outline:
    Given this order finished and not delivered yet
    When its pass <time> minutes after the pickup time
    Then client dont recieve message


    Examples:
      | time |
      | 4   |
      | 7   |

  Scenario Outline:
    Given this order finished and not delivered yet
    When its pass <time> hours after the pickup time
    Then client recieve message


    Examples:
      | time |
      | 1   |

  Scenario Outline:
    Given this order finished and not delivered yet
    When its pass <time> hours after the pickup time
    Then client dont recieve message


    Examples:
      | time |
      | 2   |

  Scenario Outline:
    Given this order finished and HAS been delivered
    When its pass <time> minutes after the pickup time
    Then No Message : Order is already delivered


    Examples:
      | time |
      | 5   |
  Scenario Outline:
    Given this order finished and not delivered yet
    When its pass <time> hours after the pickup time on an non existing order
    Then No Message : Wrong Order Id


    Examples:
      | time |
      | 1   |

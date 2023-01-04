Feature: Display price of the order

  Background:
    Given an Order

  Scenario Outline: display the price of multiple cookies in an order
    Given a list of <n> same cookies
    When the a cookie is added
    Then the price change to the according value without taxes <final_price>

    Examples:
      |n | final_price|
      |5 | 2.25       |
      |2 | 1.125      |
      |1 | 0.75       |




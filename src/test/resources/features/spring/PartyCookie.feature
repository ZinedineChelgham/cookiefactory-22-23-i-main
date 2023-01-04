Feature: use party cookies

  Background:
    Given a user that want to order some party cookies

  Scenario Outline: a user order a L party cookie and the user want to add it in an order
    Given recipe of party cookie is available
    When the user add <nbCookieAdded> recipe in his order
    Then the party cookie is in the order

    Examples:
      | nbCookieAdded        |
      | 1                    |
      | 50                   |

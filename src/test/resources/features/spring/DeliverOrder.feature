Feature: deliver the order to a client to whom the order is finished
  Every finished order have to be delivered to the client
  association made with the email.

  Scenario Outline:
    Given order id : 12780
    When the order is ready and client come with his known id
    Then Order <id> is delivered

    Examples:
      | id       |
      | 12780    |

  Scenario Outline:
    Given order id : 12780
    When the order is ready and client come with his known id
    Then No Delivery for Order <id> : the order is not found

    Examples:
      | id       |
      | 12781    |

  Scenario Outline:
    Given order id : 12780
    When the order is not ready and client come with his known id
    Then No Delivery for Order <id> : the order is not ready

    Examples:
      | id       |
      | 12780    |

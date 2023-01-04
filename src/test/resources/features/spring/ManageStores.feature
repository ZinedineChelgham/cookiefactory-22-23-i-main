Feature: manage stores

  Background:
    Given a not null list of stores named stores

  Scenario Outline: add stores
    Given a list of all stores
    When someone add a new store named <store>
    Then the store list size is increment and the store is in the list

    Examples:
      | store        |
      | "store 1"    |
      | "store 2"    |

  Scenario Outline: remove stores
    Given a list of all stores
    When someone remove a existing store named <store>
    Then the store list size is decrement and the store is not in the list

    Examples:
      | store        |
      | "store 1"    |
      | "store 2"    |
      | "store 3"    |

  Scenario Outline: Change vat
    Given a list of all stores
    And someone add a new store named <store>
    When the store manager changes the vat of <store> to <tax_rate_input>
    Then the vat of the store <store> is <tax_rate_effective>
    Examples:
      | store        | tax_rate_input | tax_rate_effective |
      | "store 1"    | 1.0            | 1.0                |
      | "store 2"    | -1.0           | 0.0                |
      | "store 3"    | 100.1          | 0.0                |


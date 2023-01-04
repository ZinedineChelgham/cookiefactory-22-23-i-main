Feature: Registration

  Scenario Outline: create an account for a client
    Given no user with the <email> exists
    When a user registers with <email> and <phoneNumber>
    Then a user with the <email> exists in the system
    Examples:
      | email            | phoneNumber |
      | "test@gmail.com" | "1234"   |
      | "test@gmail.com" | "12344"  |

  Scenario Outline: subscribe to the loyalty program
    Given a user with the <email> is registered
    When the user with the <email> activates the loyalty program
    Then the user with the <email> is in the loyalty program
    Examples:
      | email            |
      | "test@gmail.com" |

  Scenario Outline: can not create a account of the email is already used
    Given a user with the <email> is registered
    When a user registers with <email> and <phoneNumber>
    Then only one user with the <email> exists
    Examples:
      | email            | phoneNumber |
      | "test@gmail.com" | "1234"   |

  Scenario Outline: can not create a account of a invalid email
    Given no user with the <email> exists
    When a user registers with <email> and <phoneNumber>
    Then no user with the <email> exists in the system
    Examples:
      | email           | phoneNumber |
      | "testgmail.com" | "1234"   |
      | "test@gmailcom" | "12344"  |
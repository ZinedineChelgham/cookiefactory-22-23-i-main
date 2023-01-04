Feature: Apply the loyalty program

  Background:
    Given A Store
    Given A member that subscribed to the loyalty program
    Given the recipe "3 Chocolates" that cost "1.7"
    Given An order line that contains one "3 chocolate recipe"
    Given An order that contains the previous order line


  Scenario: Apply the 10% discount for a member
    Given A member that order his 31th cookie
    When The member validate the order
    Then The price should be "1.53"


    Scenario: Update the number of cookies ordered for a member
    Given A member that order his 20thh cookie
    When The member validate this new order
    Then The price should be now "1.7"

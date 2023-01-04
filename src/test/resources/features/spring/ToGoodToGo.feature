Feature: To good to go integration


  Background:
    Given a store

  Scenario: upload a surprise box to the too good to go app
    Given an order in forgotten state
    When the system three hours counter trigger
    Then a new surprise box is created and uploaded to the app


  Scenario: remove a surprise box from the too good to go app
    Given a surprise box in the to good to go app
    When the surprise box is picked up
    Then the surprise box is removed from the to good to go app
    And the surprise box is marked as picked up


  Scenario: subscription to the too good to go notifier
    Given a user
    When the user subscribes to receive notification when a surprise box is available on Monday at eleven AM
    Then the user will receives a notification when a surprise box is available on Monday at eleven AM



  Scenario: unsubscribe to the notifier
    Given a user
    When the user unsubscribes to receive notification when a surprise box is available on Monday at eleven AM
    Then the user will not receives a notification when a surprise box is available on Monday at eleven AM

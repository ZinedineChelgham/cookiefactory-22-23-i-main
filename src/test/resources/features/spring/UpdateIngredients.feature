Feature: UpdateIngredients

  Scenario: Update ingredients
    Given A system with accessible Catalog Service
    When An administrator tries to update the ingredients via service
    Then All ingredients provided by the service are registered

  Scenario: Remove ingredients
    Given A system with accessible Catalog Service
    When An administrator tries to update the ingredients via service
    Then All ingredients already registered but not provided are marked unavailable

  Scenario: no Service
    Given A system without a Catalog Service
    When An administrator tries to update the ingredients via service
    Then An error is thrown

  Scenario: Service not available
    Given A system without accessible Catalog Service
    When An administrator tries to update the ingredients via service
    Then An error is thrown


@Regression_new
Feature: new Post call scenarios for regression by registry modification
  

    @TC2-sendFax_load
  Scenario: Post call for Load test
    Given I want submit new post call with multiple pages
    And I validate of status code is 201

  @TC3-sendFax_load
  Scenario: Post call for Load test
   Given submit new request with new page
    And I validate of status code is 201

  @TC4-sendFax_load
  Scenario: Post call for Load test
   Given submit new request with five page
   And I validate of status code is 201

  @TC5-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with six page
   And I validate of status code is 201

  @TC6-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with seven page
    And I validate of status code is 201

  @TC7-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with eight page
    And I validate of status code is 201

  @TC8-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with nine page
    And I validate of status code is 201

  @TC9-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with ten page
    And I validate of status code is 201

  @TC10-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with different page count
    And I validate of status code is 201

 

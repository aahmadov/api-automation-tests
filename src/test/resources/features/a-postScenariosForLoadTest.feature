
@Loadtest
Feature: ^^^ Validation of "sent" Fax status and Ids of *Load* test^^^


 @TC-sendFax_load
  Scenario: Validation of Post call for Load test
    Given I want submit new post call with one page 
    And I validate of status code is 201
   


@TC-loadtestscenario
    Scenario: send new fax with new set-up
    Given send simple fax
    And user validates the status code is 201
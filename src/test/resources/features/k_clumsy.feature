@Regression
Feature: This feature is to crash scheduler with different clumsy set_up 
  

  @clumsy_send&recieve
  Scenario: Post new Fax with different page counts
    Given I want to submit new records to outbound Fax
    And I validate new status code 201 was succesfully generated 
    When I validate FaxId and TsiId of outbound fax is created
    And I submit Get call to Inbound Fax
    And I validate status code of Get call is 200
  

 

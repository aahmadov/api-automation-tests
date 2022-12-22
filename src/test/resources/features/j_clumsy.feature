
@regression1
Feature: This feature is to crash scheduler with network manipulation  


  @send#receive_with_clumsySetUp
  Scenario: Send fax with different count of pages 
    Given I submit post call
    And I validate new records been created with status code 201
    When I validate outbound  FaxId and TSI     
    Then I submit Get call in inbound FaxUserId
    And I check and validate status code is 200


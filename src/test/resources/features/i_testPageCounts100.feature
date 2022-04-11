@regression
@inboundPageValidation100
Feature: This feature file validates the number of outbound&inbound pages with registry setting ("100 pages" )


  @send_receive
  Scenario: Title of your scenario
    Given I submit post call for more than hundred page 
    And first i validate status code is 201
    Then i validate outbound FaxId ,TSI 
    Given i submit Get call by FaxUserID retrieve date 
    And validate status code is 200
  
    



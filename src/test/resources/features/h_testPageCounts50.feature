@Regression
@outboundPageValidation50
Feature: ^^ This feature file validates the number of outbound&inbound pages  ("50 pages" ) ^^


  @post_get50PageValidation
  Scenario: ^^ Submit Fax to manipulate outbound&inbound Data ^^
    Given i submit new Post call with special TSI 
    And first i validate status code is 201
    Then i validate outbound FaxId ,TSI and PagesSent
    Given i submit Get call by FaxUserID
    And  validate status code is 200
    Then i validate inbound FaxStatus and Total pages sent 
   



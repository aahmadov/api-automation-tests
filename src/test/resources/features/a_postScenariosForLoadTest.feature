#Author: abbas@softlinx.com
@Loadtest
Feature: Validate post call for load test

  @TC1-sendFax_load
  Scenario Outline: Validation of Post call for Load test
    Given I want to submit group of post calls with data
      | url              | <url>              |
      | times            | <times>            |
      | ignoreFail       | <ignoreFail>       |
      | coverPageEnabled | <coverPageEnabled> |
      | faxNumFileLoc    | <faxNumFileLoc>    |
      | pageSize         | <pageSize>         |
    Then I validate the status 201 as expected

    Examples:
      | url             | times | ignoreFail | coverPageEnabled | faxNumFileLoc        | pageSize |
      | outbound_URl_65 | 1     | true       | false            | input/faxNumbers.csv | 30       |



#  @TC2-sendFax_load
#  Scenario Outline: Validation of Post call for Load test
#    Given I want to submit gropu of post calls with <url> for <times> and <coverPage>
#    Then I validate the status <code> as expected
#
#    Examples:
#      | url             | times | code | coverPage |
#      | outbound_URl_65 | 20    | 201  | true      |
#      | post_call_Url   | 10    | 201  | false     |
  
  
  
  
  
  
  
  
 # @TC2-sendFax_load
  Scenario: Post call for Load test
    Given I want submit new post call with multiple pages
    And I validate of status code is 201

  #@TC3-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with new page
    And I validate of status code is 201

 # @TC4-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with five page
    And I validate of status code is 201

 # @TC5-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with six page
    And I validate of status code is 201

 # @TC6-sendFax_load
 # Scenario: Post call for Load test
    Given submit new request with seven page
    And I validate of status code is 201

 # @TC7-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with eight page
    And I validate of status code is 201

 # @TC8-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with nine page
    And I validate of status code is 201

 # @TC9-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with ten page
    And I validate of status code is 201

 # @TC10-sendFax_load
  Scenario: Post call for Load test
    Given submit new request with different page count
    And I validate of status code is 201

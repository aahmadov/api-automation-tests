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
      | outbound_URl_65 | 10     | true       | true            | input/Faxnumber.txt  | 1       |
     #| post_call_Url   | 1      | true       | false           | input/Faxnumber.txt  | 3        |










 # @TC2-sendFax_load
 # Scenario Outline: Validation of Post call for Load test
 #  Given I want to submit group of post calls with <url> for <times> and <coverPageEnabled>
 # Then I validate the status <code> as expected

   # Examples:
   #   | url             | times | code | coverPageEnabled |
   #   | outbound_URl_65 | 1    | 201   | true             |
   #  
  
  
  
  
  
  
  
  


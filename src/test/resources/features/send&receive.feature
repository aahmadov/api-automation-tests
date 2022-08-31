
@testCase#1
@faxNumberTest
Feature:  ^^This feature file validates add (1) to the beginning of each submitted number ^^

 Scenario Outline: Prepend 1 to fax number if it starts with [2-9]
    Given I want to submit group of post calls with data
      | url              | <url>              |
      | times            | <times>            |
      | coverPageEnabled | <coverPageEnabled> |
      | faxNumFileLoc    | <faxNumFileLoc>    |
      | pageSize         | <pageSize>         | 
    Then I validate the status 201 as expected

    Examples:
      | url             | times |coverPageEnabled   | faxNumFileLoc             | pageSize |
      | outbound_URl_65   | 4     | false             | input/numberForTest.txt   | 2        |
      
   @retrieve_Fax_Number
 Scenario: ^^ Retrieve recently created fax ^^
  Given user sends request to retrieve all FaxNumbers
  And user validates status code is 200
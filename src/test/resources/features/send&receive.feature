@testCase#1

Feature:  ^^This feature file validates add (1) to the beginning of each submitted number ^^

  @faxNumberTest
  Scenario Outline: Prepend 1 to fax number if it starts with [2-9]
    Given I want to submit group of post calls with data
      | url              | <url>              |
      | times            | <times>            |
      | coverPageEnabled | <coverPageEnabled> |
      | faxNumFileLoc    | <faxNumFileLoc>    |
      | pageSize         | <pageSize>         |
    Then I validate the status 201 as expected

    Examples:
      | url             | times | coverPageEnabled | faxNumFileLoc           | pageSize |
      | post_call_Url   | 12     | false            | input/numberForTest.txt | 2        |

 @retrieve_Fax_Number
  Scenario: ^^ Retrieve recently created fax ^^
    Given user sends request to retrieve all FaxNumbers
    Then I validate the status 200 as expected
    And verify fax numbers are as expected for TSI id

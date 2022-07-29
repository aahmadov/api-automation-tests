@Regression
@outboundPageValidation16

Feature: ^^ This feature file validates the number of outbound pages ("16 pages" ) ^^

 
  Scenario: ^^ Send new Fax with single attachment ^^
    Given User submits requests with TSI ID
    And User validates the status code is 201
    Then User validates is FaxNumber is same Like "1-222-222-2222"
    Given i submit getCall  by FaxUserId
    And  validate status code is 200
    And User validates current FaxStatus
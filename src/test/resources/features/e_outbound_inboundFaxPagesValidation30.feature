@Regression
@outboundPageValidation30

Feature: ^^ This feature file validates the number of outbound pages with some registry setting ("30 pages")^^

 @send1
  Scenario: ^^ Submit Fax to manipulate outbound Data ^^
    Given i submit new Fax regarding registry setting 
    And first i validate status code is 201
    Then i verify number which i created is "1-222-222-2222"
    Given i submit getCall to by FaxUserID
    And  validate status code is 200
    Then user validates FaxStatus and Total pages sent
    Then user validates Inbound FaxStatus after all attemps
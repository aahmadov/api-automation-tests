@Regression

@outbound_FaxwithfCoverPage_Validation
Feature: ^^This feature file validates the number of inbound Fax with CoverPage  ("22+1 pages")^^

  @a_outboundFax
  Scenario:  ^^ Dynamic scenario for fax status and page number validation ^^
    Given User submits request with credentialNewOutbound
    And User validates the send status code is 201
    Then User validates outbound Fax TSI id
    
    @b_queryInboundAfterAttempts
  Scenario: ^^ Dynamic scenario get fax status and page number validation from inbound ^^
    Then User submits getRequest by credentialNewInbound to retrieve data from inbound faxes 
    And User validates getStatusCode 200
    Then User validates inbound FaxStatus after a third attempt and total PagesReceived
    And User validates inbound FaxStatus after a second attempt and total pages recieved
    Then  User validates inbound FaxStatus after a first attempt and total pages recieved


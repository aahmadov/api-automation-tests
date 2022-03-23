@regression

@inbound_FaxwithCoverPage_Validation
Feature: This feature file validates the number of inbound Fax with CoverPage and registry setting ("12")

  @a_tag2
  Scenario:  Dynamic scenario for fax status and page number validation 
    Given User submits request with credentialNewOutbound
    And User validates the send status code is 201
    
    @b_queryInboundAfterAttempts
  Scenario:  Dynamic scenario get fax status and page number validation from inbound
    Then User submits getRequest credentialNewInbound retrieve data from inbound faxes 
    And User validates getStatusCode 200
    Then User validates inbound FaxStatus after a first attempt and total PagesReceived
    And User validates inbound FaxStatus after a second attempt and total pages recieved
    Then  User validates inbound FaxStatus after a third attempt and total pages recieved


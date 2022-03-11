@regression

@inbound_FaxwithCoverPage_Validation
Feature: This feature file validates the number of inbound Fax with CoverPage and registry setting ("12")

  @tag2
  Scenario:  Dynamic scenario for fax status and page number validation 
    Given User submits request with credentialNewOutbound
    And User validates the send status code is 201
    Then User submits getRequest credentialNewOutbound retrieve data from inbound faxes
    When The random TSI is generated 
    And User validates getStatusCode 200
    And User validates before the last FaxStatus and total PagesReceived
    Then User validates latest FaxStatus and total pages recieved



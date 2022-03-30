@regression
@outbound_FaxwithoutgCoverPage_Validation

Feature: This feature file validates the number of inbound Fax pages with registry setting ("20 pages")

 @send2
  Scenario: Send new Fax with attachment 
    Given User submits requests with creadentialInbound
    And User validates the status code is 201
    Then User validates new FaxNumber is generated 
    Then User validates TSI id which is setup in post call
    
     @get1
  Scenario: retrieve new Fax with attachment
    Given user submits getRequest retrieve data from inbound faxes
    And user validates status code is 200
    When user validates random TSI id and FaxStatus
      
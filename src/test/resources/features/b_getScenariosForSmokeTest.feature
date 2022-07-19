  @Smoke

Feature: ^^^ Validation of GetCalls with Fax Number And FaxStatus ^^^

@TC-1getFax_aftersent
 Scenario: ^^ Retrieve recently created fax ^^
 Given user sends request to retrieve valid FaxID
 And user validates status code is 200
 Then user validates FaxNUmber is "(781)-885-4198"

@TC-2FaxByID @smokeG
Scenario: ^^ Retrieve Fax and Fax Data by Id ^^
    Given User sends requests with valid Fax id
    And User validate  status code is 200
    Then User validates FaxStatus as expected

@TC-3All_Faxs
Scenario: ^^ Retrieve all Fax Data ^^
    Given User sends requests with valid URL
    And User validate status code is 200
    Then User validates FaxUserID as "admin"

@TC-4Failed_Faxs
Scenario: ^^ Retrieve all sendFailed fax (Negative scenario) ^^
 Given User sends request to retrieve sendFailed fax
 And User validate status code is 200
 When User validates FaxStatus is "sendFailed"
 Then User validates Errorcode is 4001
 
 @TC-5Faxs_byImage
 Scenario: ^^ Retrieve Fax like an image ^^
 Given user sends request with valid FaxID
 Then user validates status code is 200
 And user validates contentType is "application/pdf"
 
 @TC-6Faxby_email
 Scenario: ^^ Retrieve fax with email ^^
 Given user send request with valid URL
 And user validate status code is 200
 Then user validates email is "test@softlinx.com" 
 
 @TC-7getFax_afterResend
  Scenario: ^^ Retrieve fax  after Resend ^^
 Given user send request with valid defined FaxId
 And user validates code is 404
 Then user validates status message is "Fax not found"



@TC-8getFax_after
 Scenario Outline: ^^ GetFaxs with different FaxIDs ^^
 Given user submits new getCalls by this "<credentialInbound>"
 And user validates status code is 200
 Examples:
 |credentialInbound       |
 |abbas@replixdb:softlinx |
 |admin@acme:softlinx     |
 
 
 @TC-9getFax_afterByRandomFaxId
 Scenario Outline: ^^ GetFaxs with diffirent FaxIDs ^^
 Given user submits new getCalls by this "<credentialInbound>" and "<FaxIds>"
 And user validates status code is 200
 Examples:
 |credentialInbound        |FaxIds|
 |abbas@replixdb:softlinx  |/550  |
 |admin@acme:softlinx      |/646  |
 
 



  
 
  
 
 
 
 
 
 
 @smokeP
Feature: ^^^ Validation of "sent" Fax status and Ids ^^^


@TC-1SentFaxwithValidNumber
 Scenario: ^^ Send Fax with valid Number and Attachment ^^
    Given User sends requests with valid number and attachment
    And User validate if status code is 201
    Then User validates FaxNumber is "(781)-885-4198"

@TC-2SentFax @negative
 Scenario: ^^ Send Fax Data without Number (negative sceanario) ^^ 
    Given User sends requests with no number 
    And User wants validate the status code is 400
    Then User validates Statustext is "Empty fax number"
    
@TC-3SentFaxwithRecipient
  Scenario: ^^ Send Fax Data with recipient Details ^^ 
    Given User sends requests with a single attachment to recipient  
    And User validate the status code is 201
    Then User validates FaxId is generated 
    
@TC-4SentFaxwithMultipRecipient
Scenario: ^^ Send Fax Data with multiple recipient Details ^^ 
    Given User sends requests with a multiple attachments to two recipient  
    And User validate the status code is 201
    Then User validates FaxId is generated
    
@TC-5resendFax
   Scenario: ^^ ReSend Fax Data with failed FaxId (negative sceanario) ^^
    Given User resends requests with failed faxID
    And User validate the status code is 500
    Then User validated new statusFax is "Job not found"
    
@TC-6sendFax
   Scenario: ^^ Send new Fax with attachment ^^
    Given User sends requests with FaxNumber & attachment
    And User validates the status code is 201
    Then User gets new generated unique Id 
    
 
    
    
   
      
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
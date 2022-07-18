#Author: your.email@your.domain.com


Feature: ^^^ Validation of post call TSI id for Load test

@TC-1getFax
 Scenario: ^^ Retrieve recently created fax ^^
 Given user sends request to retrieve valid FaxID for Load
 And user validates 200 is right getCall status code  
 Then user validates Tsi id of Fax
  



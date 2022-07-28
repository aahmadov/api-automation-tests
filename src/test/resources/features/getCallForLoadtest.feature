#Author: abbas@softlinx.com

@GetCallforLoad
Feature: ^^^ Validation of post call TSI ids for Load test 

 @TC-1getFax
 Scenario: ^^ Retrieve recently created fax and verify TSI has max attempts or recvok status ^^
  Given user sends request to retrieve valid FaxID for Load
  And user validates 200 is right getCall status code
  Then user validates TSI has max attempts or recvok status


#@TC-2getFax
 Scenario: ^^ Retrieve recently created fax ^^
 Given user sends request to retrieve valid FaxID for Load
 And user validates 200 is right getCall status code  
 Then user validates Tsi id of Fax
  

 #@TC-3getFax
 Scenario: ^^ Verify recently created TSI is present ^^
  Given user sends request to retrieve valid FaxID for Load
  And user validates 200 is right getCall status code
  Then user validates recent TSI present in response



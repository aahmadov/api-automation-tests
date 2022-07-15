#Author: abbas@softlinx.com

@Loadtest
Feature: Validate post call for load test

  @TC11-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call 
   And I validate of status code is 201
  
  @TC12-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment
   And I validate of status code is 201
   
  @TC13-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment3
   And I validate of status code is 201
   
    @TC14-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment4
   And I validate of status code is 201
   
   
   @TC15-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment5
   And I validate of status code is 201
   
   @TC16-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment6
   And I validate of status code is 201
   
   
   @TC17-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment7
   And I validate of status code is 201
   
   @TC18-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment8
   And I validate of status code is 201
   
   @TC19-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment9
   And I validate of status code is 201
   
   @TC20-sendFax_load
  Scenario: Create more post call for load test
    Given I will submit new post call with new attachment10
   And I validate of status code is 201
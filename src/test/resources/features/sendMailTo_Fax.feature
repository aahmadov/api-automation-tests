#Author: abbas@softlinx.com
#@smoke2
#Feature: I want simply send an email to a rpxqa.com and verify notification back
#@sendMailToFax
#Scenario:  Include the faxnumber in the email address and attach documents to be sent as a fax
#Given I want to send an EmailToFax message
#@receiveMailnotifications
#Scenario: Check email notification
#Given I validate receive email

@smoke2
Feature: I want simply send an email to a rpxfqa.com

  @sendMailToFax
  Scenario Outline: Include the faxnumber in the email address and attach documents to be sent as a fax
    Given I want to send an EmailToFax message and verify
      | to            | <to>            |
      | times         | <times>         |
      | faxNumFileLoc | <faxNumFileLoc> |
      | pageSize      | <pageSize>      |
      | subject       | <subject>       |
      | sendBody      | <sendBody>      |

    Examples: 
      | to                             | times | faxNumFileLoc                | pageSize  | subject               |sendBody|
      | 13333333333@auto1.rpxqa.com    |    1  | input/FaxNumberMail2fax.txt  |  2        | receivedSuccesSubject |   true |
#                                                                                         | receivedFailedSubject |
#                                                                                         | sendFailedSubject     |
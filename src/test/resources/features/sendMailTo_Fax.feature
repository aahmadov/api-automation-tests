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
Feature: I want simply send an email to a rpxfax.com

  @sendMailToFax
  Scenario Outline: Include the faxnumber in the email address and attach documents to be sent as a fax
    Given I want to send an EmailToFax message and verify
      | to            | <to>            |
      | times         | <times>         |
      | faxNumFileLoc | <faxNumFileLoc> |
      | pageSize      | <pageSize>      |
      | subject       | <subject>       |

    Examples: 
      | to                 | times | faxNumFileLoc                | pageSize  | subject               |
      | auto1.rpxqa.com    |    3  | input/FaxNumberMail2fax.txt  |  2       | receivedSuccesSubject |

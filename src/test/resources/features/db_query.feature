@regression
@dataBasequery_test
Feature: ^^ This feature is to excute inbound fax query from DB ^^

  @db_test
  Scenario: ^^ DataBase query for inboundfax ^^
    Given user excutes SQL query get JobId of sendFailed outbound faxs
    Then user excutes SQL query get JobId of receiveFailed inbound faxs



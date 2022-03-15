package stepDefinitions;

import java.sql.SQLException;

import io.cucumber.java.en.*;
import utils.DataBaseUtility;

public class Db_query {
	

	@Given("user excutes SQL query get JobId of sendFailed outbound faxs")
	public void user_excutes_SQL_query_get_JobId_of_sendFailed_outbound_faxs() throws SQLException {
		
		DataBaseUtility.openConnection();
		String query = "SELECT JobID,Pages ,FaxNumber,Error FROM acme.sendstatus where JobStatus='send Failed'"; 
		DataBaseUtility.executeSQLQuery(query);
		
	}

	@Then("user excutes SQL query get JobId of receiveFailed inbound faxs")
	public void user_excutes_SQL_query_get_JobId_of_receiveFailed_inbound_faxs() throws SQLException {
		DataBaseUtility.openConnection();
		
		String query2 = "SELECT JobID,Pages,TransmiStationID,Error FROM acme.recvstatus where JobStatus='Recv Fail'"; 
		DataBaseUtility.executeSQLQuery(query2);
		
	}

}

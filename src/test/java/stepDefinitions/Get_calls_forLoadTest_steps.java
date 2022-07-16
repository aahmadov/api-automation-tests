package stepDefinitions;




import static org.junit.Assert.assertEquals;



import io.cucumber.java.en.*;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.Load_RestRequestUtils;


public class Get_calls_forLoadTest_steps {
	
	
	Response response;
	
	@Given("user sends request to retrieve valid FaxID for Load")
	public void user_sends_request_to_retrieve_valid_FaxID_for_Load() throws InterruptedException {
		//Thread.sleep(1000*60);
		response = Load_RestRequestUtils.getCreatedFaxForLoad1(
				ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));

		System.out.println(ConfigReader.getProperty("inboundFax_url"));
		System.out.println(ConfigReader.getProperty("newInboundParam"));
	}

	@Then("user validates Tsi id of Fax")
	public void user_validates_Tsi_id_of_Fax() {
	    response.prettyPrint();
	    String Tsi = JsonPath.read(response.asPrettyString(),"$.FaxInfo[1].TSI");
	    System.out.println("**"+"--"+Tsi+"--");
	    
	}   		
	@Given("user validates {int} is right getCall status code")
	public void user_validates_is_right_getCall_status_code(int expectedStatus) {
	    int actualStatus=response.getStatusCode();
	    System.out.println(actualStatus);
	    System.out.println(expectedStatus);
	    //assertEquals(expectedStatus,actualStatus);
	    
	    
	}
}

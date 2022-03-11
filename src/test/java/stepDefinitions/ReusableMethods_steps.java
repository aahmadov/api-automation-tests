package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

public class ReusableMethods_steps {
	
	Response  response;
	
	@Given("User submits request with credentialNewOutbound")
	public void user_submits_request_with_credentialNewOutbound() {
		
	
	response=Second_RestRequestUtils.inbound_FaxwithCoverPage(ConfigReader.getProperty("post_call_Url")+ FileReader.randomNumberFor_TSI(),FileReader.readfile("20pages"),
	ConfigReader.getProperty("FaxN"));
	System.out.println("******** "+(ConfigReader.getProperty("post_call_Url")));
	System.out.println("********** "+ConfigReader.getProperty("FaxN"));
	System.out.println("********** "+FileReader.readfile("20pages "+ "and"+" with CoverPager"));

	}
	
	@Given("User validates the send status code is {int}")
	public void user_validates_the_send_status_code_is(int sendStatusCode) {
		int Code = response.getStatusCode();
		System.out.println("***** The expected " +"***"+sendStatusCode+"***"+ " send Fax statusCode lineUp with actual "+"***"+Code+"***");
		assertEquals(Code,sendStatusCode);
		
	}

	@When("The random TSI is generated")
	public void the_random_TSI_is_generated() {
	 response.asPrettyString();
	 
	 
	 String actualsTSI_ID=JsonPath.read( response.asPrettyString(),"$.FaxInfo[0].TSI").toString();
	 
	 int FaxId=JsonPath.read( response.asPrettyString(),"$.FaxInfo[0].FaxId");
	 System.out.println("****New generated TSI Id is "+actualsTSI_ID);
	 System.out.println("****New generated Fax Id is "+FaxId);
	}
	@Then("User submits getRequest credentialNewOutbound retrieve data from inbound faxes")
	public void user_submits_getRequest_credentialNewOutbound_retrieve_data_from_inbound_faxes() throws InterruptedException {
		
		Thread.sleep(1000*600);
	    response=Second_RestRequestUtils.getInboundWithCoverPage(ConfigReader.getProperty("inboundFax_url")+ConfigReader.getProperty("newInboundParam"));
	    
	    System.out.println("****** "+(ConfigReader.getProperty("inboundFax_url")+ConfigReader.getProperty("newInboundParam")));
	   
	}
	    @Then("User validates getStatusCode {int}")
	    public void user_validates_getStatusCode(int getStatus) {
	      response.getStatusCode();
	      
	    assertEquals(response.getStatusCode(),getStatus);
	    }

	    @When("User validates before the last FaxStatus and total PagesReceived")
	    public void user_validates_before_the_last_FaxStatus_and_total_PagesReceived() {
	    	response.asPrettyString();
	    	
	    	String before_the_lastFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
	    	System.out.println("***** The before the last fax status is "+before_the_lastFaxStatus);
	    	int PageRecieved =JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].PagesReceived");
	    	
	    	assertNotNull(PageRecieved);
	    	
	    	System.out.println("***** The before of the last fax total pageRecieved is "+PageRecieved);
	    }
	   

@Then("User validates latest FaxStatus and total pages recieved")
public void user_validates_latest_FaxStatus_and_total_pages_recieved()   {
		
		response.asPrettyString();
		String FaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
	
	
	System.out.println("***** The latest fax status is "+FaxStatus);
	
	int PageRecieved =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesReceived");
	
	assertNotNull(PageRecieved);
	
	System.out.println("***** The pageRecieved total is "+PageRecieved);
}
}
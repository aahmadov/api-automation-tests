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
		
	
	response=Second_RestRequestUtils.inbound_FaxwithCoverPage(ConfigReader.getProperty("post_call_Url")+ FileReader.randomNumberFor_TSI(),FileReader.readfile("Pages3"),
	ConfigReader.getProperty("FaxN"));
		response.asPrettyString();
   
	System.out.println("******** "+(ConfigReader.getProperty("post_call_Url")));
	System.out.println("********** "+ConfigReader.getProperty("FaxN"));
	System.out.println("********** "+FileReader.readfile("pages "+ "and"+" included CoverPage"));
    
	}
	
	@Given("User validates the send status code is {int}")
	public void user_validates_the_send_status_code_is(int sendStatusCode) {
		int Code = response.getStatusCode();
		System.out.println("***** the expected " +"***"+sendStatusCode+"***"+ " send Fax statusCode lineUp with actual "+"***"+Code+"***");
		assertEquals(Code,sendStatusCode);
		
	}

	@Then("User submits getRequest credentialNewInbound retrieve data from inbound faxes")
	public void user_submits_getRequest_credentialNewInbound_retrieve_data_from_inbound_faxes() throws InterruptedException  {
		//Thread.sleep(1000*110);
		
	    response=Second_RestRequestUtils.getInboundWithCoverPage(ConfigReader.getProperty("inboundFax_url")+ConfigReader.getProperty("newInboundParam"));
	    
	    System.out.println("****** "+(ConfigReader.getProperty("inboundFax_url")+ConfigReader.getProperty("newInboundParam")));
	   
	}
	    @Then("User validates getStatusCode {int}")
	    public void user_validates_getStatusCode(int getStatus) {
	      response.getStatusCode();
	      
	    assertEquals(response.getStatusCode(),getStatus);
	    }

	    @Then("User validates inbound FaxStatus after a first attempt and total PagesReceived")
	    public void user_validates_inbound_FaxStatus_after_a_first_attempt_and_total_PagesReceived() throws InterruptedException {
	    	
	    	
	    	//Thread.sleep(1000*600);
	    	String before_the_lastFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
	    	int PageRecieved =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesReceived");
	    	String Tsi=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI");
	    	int firstFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
	    	assertNotNull(PageRecieved);
	    
	    	System.out.println(" Fax id after first attempt "+firstFaxId+ " and "+"***** total pageRecieved after the first attempt is "+PageRecieved+" and TSI id "+Tsi);
	    	System.out.println("***** fax Status after a first attempt is -  "+before_the_lastFaxStatus);
	    }
	   

	    @And("User validates inbound FaxStatus after a second attempt and total pages recieved")
	    public void user_validates_inbound_FaxStatus_after_a_second_attempt_and_total_pages_recieved() throws InterruptedException   {
	     String FaxStatus;
		
		//Thread.sleep(1000*180);
		
//		        for(int i=0; i<10000; i++){
//		    	Thread.sleep(1000*5);
//		    	FaxStatus =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
//		    	 
//		    	 if(FaxStatus=="recvOk") break;
//	    }
		
		    FaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
			int PageRecieved =JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].PagesReceived");
			String tsioflastFax=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].TSI");
			int secondFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId");
			assertNotNull(PageRecieved);
			
			System.out.println(" Fax id after second attempt "+secondFaxId+ " and "+"***** pageRecieved after a second attempt is "+PageRecieved+" and TSI id after a second attempt "+tsioflastFax);
            System.out.println("***** fax status after a second attempt  "+FaxStatus);
	
}
	    @Then("User validates inbound FaxStatus after a third attempt and total pages recieved")
	    public void user_validates_inbound_FaxStatus_after_a_third_attempt_and_total_pages_recieved() throws InterruptedException {
	    	String faxStatus;
	    	
			
			//Thread.sleep(1000*300);
			faxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();

				int PageRecieved =JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].PagesReceived");
				String tsiofThirdFax=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].TSI");
				int thirdFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId");
				assertNotNull(PageRecieved);
				
				System.out.println(" Fax id after third attempt "+ thirdFaxId + " and "+"***** pageRecieved after a third attmept is "+PageRecieved+" and TSI id after a third attempt "+tsiofThirdFax);
				System.out.println("***** fax status after a third attempt  "+faxStatus);
	    }
}
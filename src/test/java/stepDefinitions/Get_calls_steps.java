package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

public class Get_calls_steps {
	Response response;

	@Given("User sends requests with valid Fax id")
	public void user_sends_requests_with_valid_Fax_id() throws InterruptedException {
		Thread.sleep(1000*120);
		response = RestRequestUtils
				.getFax(ConfigReader.getProperty("getFaxByID_url") + ConfigReader.getProperty("valid_ID"));
		System.out.println("**"+ConfigReader.getProperty("getFaxByID_url"));
		System.out.println("**"+ConfigReader.getProperty("valid_ID"));
	}

	@And("User validate  status code is {int}")
	public void user_validate_status_code_is(int statusCode) {
		assertEquals(response.statusCode(),statusCode );
	}

	@Then("User validates FaxStatus as expected")
	public void user_validates_FaxStatus_as_expected() {
		String faxStatus = response.then().extract().path("FaxInfo[0].FaxStatus");
		int faxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		String pagesTotal=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesTotal").toString();
		System.out.println("** fax id is"+"**"+faxId+"**");
		System.out.println("** totalPages sent "+"**"+pagesTotal+"**");
		System.out.println("** faxStatus is "+"**"+faxStatus+"**");
		assertEquals(faxStatus, "sent");
	}

	@Given("User sends requests with valid URL")
	public void user_sends_requests_with_valid_URL() throws InterruptedException {
		Thread.sleep(1000*60);
		response = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url"));
       System.out.println("** "+ConfigReader.getProperty("getFaxByID_url"));
	}

	@And("User validate status code is {int}")
	public void user_validate_status_code_is1(int code) {
		assertEquals(response.statusCode(), code);
	}

	@Then("User validates FaxUserID as {string}")
	public void user_validates_FaxUserID_as(String admin) {

		String strResponse = response.asPrettyString();
		List<String> userID = JsonPath.read(strResponse, "$.FaxInfo[*].FaxUserId");
		System.out.println("*** faxUserId after validation is "+"**"+userID+"**");
		System.out.println("*** total count of userid by name Admin "+"**"+userID.size()+"**");
		
		assertTrue(userID.contains(admin));

	}

	@Given("User sends request to retrieve sendFailed fax")
	public void user_sends_request_to_retrieve_sendFailed_fax() throws InterruptedException {
		Thread.sleep(1000*120);
		
		response = RestRequestUtils.getSendFailed_fax(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("queryParam")));
		System.out.println("** "+ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("queryParam")));
	}

	@And("User validates status codes is {int}")
	public void user_validates_status_codes_is1(int scode) {
		assertEquals(response.statusCode(), scode);
	}

	@When("User validates FaxStatus is {string}")
	public void user_validates_FaxStatus_is(String sendFailed) {
		int FaxId=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].FaxId");
		String respo = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
		String error = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].ErrorText").toString();
		
		System.out.println("** Fax id is generated"+"**"+FaxId+"**");
		System.out.println("** the fax status after a validation is "+"**"+respo+"**");
		System.out.println("** the error text after a validation is "+"**"+error+"**");
		assertEquals(respo, sendFailed);
	}

	@Then("User validates Errorcode is {int}")
	public void user_validates_Errorcode_is(int ErrorCode) {
		int respo = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].ErrorCode");
		System.out.println("** the fax Error code after a validation is "+"**"+respo+"**");
		assertEquals(ErrorCode, respo);
	}

	@Given("user sends request with valid FaxID")
	public void user_sends_request_with_valid_FaxID() throws InterruptedException {
		Thread.sleep(1000*120);
		response = RestRequestUtils
				.getImage(ConfigReader.getProperty("getFaxByID_url") + ConfigReader.getProperty("param"), "pdf");
		System.out.println("** "+(ConfigReader.getProperty("getFaxByID_url")));
		System.out.printf("** "+ConfigReader.getProperty("param"), "pdf");
		
	}

	@Then("user validates status code is {int}")
	public void user_validates_status_code_is(int code) {
		response.getStatusCode();
		assertEquals(response.getStatusCode(), code);
	}

	@And("user validates contentType is {string}")
	public void user_validates_contentType_is(String contType) {
		response.getContentType();
		assertEquals(response.getContentType(), contType);
	}

	@Given("user send request with valid URL")
	public void user_send_request_with_valid_URL() throws InterruptedException {
		Thread.sleep(1000*30);
		response = RestRequestUtils.get_fax_WithEMail(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("queryParamForEmail")));
         System.out.println("** "+ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("queryParamForEmail")));
	}

	@Given("user validate status code is {int}")
	public void user_validate_status_code_is2(int rCode) {
		response.getStatusCode();

		assertEquals(response.getStatusCode(), rCode);
	}

	@Then("user validates email is {string}")
	public void user_validates_email_is(String expectedEmail) {
		
		int FaxId= JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxId");
		String actualEmail = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].NotifyEmailAddress");
		System.out.println("**Fax Id is "+"**"+FaxId+"**");
        System.out.println("** email of fax is"+"** "+actualEmail+"**");
		assertEquals(actualEmail, expectedEmail);
	}

	@Given("user send request with valid defined FaxId")
	public void user_send_request_with_valid_defined_FaxId() {
		response = RestRequestUtils.get_FaxAfterResend(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("definedParam")));

	}

	@Given("user validates code is {int}")
	public void user_validates_code_is(int erCode) {
		int st = response.getStatusCode();
		assertEquals(st, erCode);
	}

	@Then("user validates status message is {string}")
	public void user_validates_status_message_is(String expectedStatus) {

		String actualStatus = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
        System.out.println("** status text is "+"**"+actualStatus+"**");
		assertEquals(actualStatus, expectedStatus);
	}

	@Given("user sends request to retrieve valid FaxID")
	public void user_sends_request_to_retrieve_valid_FaxID() throws InterruptedException {
		
		Thread.sleep(1000*120);
		response = RestRequestUtils.getRecentCreatedFax(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("FaxUserId")));

	}

	@Then("user validates FaxNUmber is {string}")
	public void user_validates_FaxNUmber_is(String expectedNumber) {
		String Faxstatus="";
		String pagesTotalsent=null;
		 int FaxId = 0;
		String resp = response.asPrettyString();
	   List<String> number = JsonPath.read(resp, "$.FaxInfo[*].FaxNumber");
	   System.out.println("**total fax been created is "+number.size());
	         
	   for(String str:number) {
		   
	    if(expectedNumber.equals(str)) {
	    	 FaxId= JsonPath.read(resp, "$.FaxInfo[4].FaxId");
	    	 Faxstatus = JsonPath.read(resp, "$.FaxInfo[4].FaxStatus").toString();
	    	 pagesTotalsent = JsonPath.read(resp, "$.FaxInfo[4].PagesTotal").toString();
	    }
	   
	   }
	    System.out.println("***faxId  is"+"**"+FaxId+"**");
	    System.out.println("***faxStatus  is"+"**"+Faxstatus+"**");
	    System.out.println("***total pages sent "+"**"+pagesTotalsent+"**");
	    System.out.println("***faxNumber is"+"**"+number+"**");
		//assertEquals(expectedNumber, number);
		
		
	}

	@Given("user submits getRequest retrieve data from inbound faxes")
	public void user_submits_getRequest_retrieve_data_from_inbound_faxes() throws InterruptedException {
     //Thread.sleep(1000*420);
		
		response = RestRequestUtils.getFaxsTSINewRestApi(
				ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));
	}

	@When("user validates random TSI id and FaxStatus")
	public void user_validates_random_TSI_id_and_FaxStatus() throws InterruptedException {
        String resp = response.asPrettyString();
		List<String> tsi = JsonPath.read(resp,"$.FaxInfo[*].TSI");
		
		//System.out.println(""+TSi.size());
		
		
		
		System.out.println("**************************************");
		for (int i = 0; i < tsi.size(); i++) {
			System.out.println(i);
			String num = Integer.toString(i);
			String actualTSId = JsonPath.read(resp,"$.FaxInfo["+num +"].TSI").toString();
			System.out.println("The random generated TSI id is "+"***"+actualTSId+"***");
			if (actualTSId!= null) {
				int FaxId= JsonPath.read(resp, "$.FaxInfo["+num +"].FaxId");
				System.out.println("The new generated FaxID is*** " +"***"+FaxId+"***");
				String FaxStatus = (JsonPath.read(resp, "$.FaxInfo["+num +"].FaxStatus"));
				int pageRecieved=JsonPath.read(resp, "$.FaxInfo["+num +"].PagesReceived");
				System.out.println("Expected Fax Status is  **"+ FaxStatus +"**"+ "and pages received  "+"**" +pageRecieved+ "**");
				break;
			}
		}
		
		}
	@Given("User validates current FaxStatus")
	public void user_validates_current_FaxStatus() {
		
		String actualFaxStatus=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].FaxStatus");
		System.out.println("****the actual Fax status "+"**"+actualFaxStatus+"**");
		if(actualFaxStatus!=null) {
			String FaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId").toString();
			int pagesSent=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].PagesSent");
			System.out.println("****fax Id is "+ "**"+FaxId+"**"+ " and total pages sent is "+"**"+pagesSent+"**" +" out of total 16 pages");
		}
	     
		}

	@Given("user submits new getCalls by this {string}")
	public void user_submits_new_getCalls_by_this(String creadS) {
		response = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url"));
		System.out.println("**"+ConfigReader.getProperty("getFaxByID_url"));
	}

	@Given("user submits new getCalls by this {string} and {string}")
	public void user_submits_new_getCalls_by_this_and(String creadS, String FaxIDs) {
		response = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url") + FaxIDs, creadS);
		System.out.println("**"+ConfigReader.getProperty("getFaxByID_url"));
		System.out.println("**"+"**"+FaxIDs+"**");
		System.out.println("**"+"**"+creadS+"**");
	}

	@Given("i submit getCall to by FaxUserID")
	public void i_submit_getCall_to_by_FaxUserID() throws InterruptedException {
		
		Thread.sleep(1000*420);
		response = RestRequestUtils.getFaxWithRegistryBlankSetting(ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam")));
			System.out.println("* "+ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam")));
	}
	@And("validate status code is {int}")
	public void validate_status_code_is(int getExpectStatCode) {
	   int actualsStatCode=response.getStatusCode();
	   
	   assertEquals("it is not expected status Code",getExpectStatCode,actualsStatCode);

	}

	@Then("user validates FaxStatus and Total pages sent")
	public void user_validates_FaxStatus_and_Total_pages_sent() throws InterruptedException {
	    response.asPrettyString();
	    
	   String faxId= JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId").toString();
	    System.out.println("**the new generated fax Id is "+"** "+faxId+" **");
	    
	    
	    String actualsTSI_ID=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].TSI").toString();
	    System.out.println("**Tsi id is "+"***** "+actualsTSI_ID+" *****");
        String FaxStatus;
        FaxStatus =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
        int totalSentPages=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesSent");
    	System.out.println("**faxStatus of postcall is **** "+FaxStatus+"**** and "+" total pages sent is **"+totalSentPages +"**");
	    //String result;

//	    for(int i=0; i<100; i++){
//	    	Thread.sleep(1000*5);
//	    	result =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
//	    	 
//	    	 if(result=="sent") break;
//	    }
	   
	    	//String FaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
	    
	    	//do {
	    		 
	    		 
	    	 //Thread.sleep(1000*5);
	    
	    	
	   
	    	//while(FaxStatus!="sent");
	    	
	    
	}
	
	    	@Then("user validates Inbound FaxStatus after all attemps")
	    	public void user_validates_Inbound_FaxStatus_after_all_attemps() {
	    		response= RestRequestUtils.getFaxsafterAllattempts(ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));
	    		response.asPrettyString();
	    		String  thirdAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
	    		String secondAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
	    		String firstAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
	    		String firstFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId").toString();
	    		String secondFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId").toString();
	    		String thirdrdFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId").toString();
	    		
	    		System.out.println("*** faxstatus after a first attempt is "+"**"+firstAttemptFaxStatus+"**"+" and FaxId is "+"**"+firstFaxId+"**");
	    		System.out.println("*** faxstatus after a second attempt is"+"**"+secondAttemptFaxStatus+"**"+" and FaxId is "+ "**"+secondFaxId+"**");
	    		System.out.println("*** faxstatus after a third attempt is "+"**"+thirdAttemptFaxStatus+"**"+ " and FaxId is "+"**"+thirdrdFaxId+"**");
	}
	@Given("i submit getCall  by FaxUserId")
	public void i_submit_getCall_by_FaxUserId() throws InterruptedException {
		
		Thread.sleep(1000*420);
		
		response = RestRequestUtils.getFaxsTSINewRestApi(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam"))); 
	}
    @Given("i submit Get call by FaxUserID")
    public void i_submit_Get_call_by_FaxUserID() throws InterruptedException {
    	
    	Thread.sleep(1000*1620);
    	response = Second_RestRequestUtils.getInbound50Page(
				ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));
    	
}

    @Then("i validate inbound FaxStatus and Total pages sent")
    public void i_validate_inbound_FaxStatus_and_Total_pages_sent() {
    	String firstAttemptTSI=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].TSI").toString();
  String PagesReceived=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].PagesReceived").toString();
 
  System.out.println("****inbound TSI id is same with outbound TSI id "+"**"+firstAttemptTSI+"**");
  
    
    String  thirdAttemptTSI=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
    String  secondAttemptTSI=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].TSI").toString();
   
    
    String  thirdAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
	String secondAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
	String firstAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
	int firstFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
	int secondFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId");
	int thirdrdFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId");
	
	System.out.println("*** faxstatus after a first attempt  is "+"**"+thirdAttemptFaxStatus+"**"+ " and FaxId is "+"**"+thirdrdFaxId+"**"+"and TSI id is "+"**"+thirdAttemptTSI+"**");
	System.out.println("*** faxstatus after a second attempt is "+"**"+secondAttemptFaxStatus+"**"+" and FaxId is "+ "**"+secondFaxId+"**"+"and TSI id is "+"**"+secondAttemptTSI+"**");
	System.out.println("*** faxstatus after a last attempt   is "+"**"+firstAttemptFaxStatus+"**"+"   and FaxId is "+"**"+firstFaxId+"**"+ "and TSI id is "+"**"+firstAttemptTSI+"**"+"and total page received in inbound from out of 50 page was sent "+"**"+PagesReceived+"**");
}
    @Then("i validate outbound FaxId ,TSI")
    public void i_validate_outbound_FaxId_TSI() {
    	
    	response = Second_RestRequestUtils.outbound100PageValidation(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam"))); 
    	int outboundfaxid=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
        String outbountTsi =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();

    		System.out.println("*** outbound FaxId is "+"**"+outboundfaxid+"**");	
    		System.out.println("*** outbount Fax Tsi "+"**"+outbountTsi+"**");	

	}
   
    @Given("i submit Get call by FaxUserID retrieve date")
    public void i_submit_Get_call_by_FaxUserID_retrieve_date() throws InterruptedException {
    	
    	Thread.sleep(1000*200);
    	response = Second_RestRequestUtils.Inbound100PageValidation(
				ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam"))); 
    	int inboundfaxid=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
        String inboundTsi =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
    			

    	    String  thirdAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
    		String secondAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
    		String firstAttemptFaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
    		int inboundPageReceived=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].PagesReceived");
    		int secondFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId");
    		int thirdrdFaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId");
    		
    		System.out.println("*** faxstatus after a first attempt  is "+"**"+thirdAttemptFaxStatus+"**"+ " and FaxId is "+"**"+thirdrdFaxId+"**"+"and TSI id is "+"**"+inboundTsi+"**");
    		System.out.println("*** faxstatus after a second attempt is "+"**"+secondAttemptFaxStatus+"**"+" and FaxId is "+ "**"+secondFaxId+"**"+"and TSI id is "+"**"+inboundTsi+"**");
    		System.out.println("*** faxstatus after a last attempt   is "+"**"+firstAttemptFaxStatus+"**"+"   and FaxId is "+"**"+inboundfaxid+"**"+ "and TSI id is "+"**"+inboundTsi+"**"+"and total pages received in inbound "+"**"+inboundPageReceived+"**");
    		
    }
}

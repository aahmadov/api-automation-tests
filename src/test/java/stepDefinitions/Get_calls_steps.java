package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;

public class Get_calls_steps {
	Response response;

	@Given("User sends requests with valid Fax id")
	public void user_sends_requests_with_valid_Fax_id() {
		response = RestRequestUtils
				.getFax(ConfigReader.getProperty("getFaxByID_url") + ConfigReader.getProperty("valid_ID"));
	}

	@And("User validate  status code is {int}")
	public void user_validate_status_code_is(int statusCode) {
		Assert.assertEquals(statusCode, response.statusCode());
	}

	@Then("User validates FaxStatus as expected")
	public void user_validates_FaxStatus_as_expected() {
		String dc = response.then().extract().path("FaxInfo[0].FaxStatus");
		assertEquals(dc, "sent");
	}

	@Given("User sends requests with valid URL")
	public void user_sends_requests_with_valid_URL() {
		response = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url"));

	}

	@And("User validate status code is {int}")
	public void user_validate_status_code_is1(int code) {
		Assert.assertEquals(response.statusCode(), code);
	}

	@Then("User validates FaxUserID as {string}")
	public void user_validates_FaxUserID_as(String admin) {

		String strResponse = response.prettyPrint();
		String userID = JsonPath.read(strResponse, "$.FaxInfo[0].FaxUserId").toString();
		System.out.println(userID);
		assertEquals(userID, admin);

	}

	@Given("User sends request to retrieve sendFailed fax")
	public void user_sends_request_to_retrieve_sendFailed_fax() {
		response = RestRequestUtils.getSendFailed_fax(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("queryParam")));
	}

	@And("User validates status codes is {int}")
	public void user_validates_status_codes_is1(int scode) {
		Assert.assertEquals(response.statusCode(), scode);
	}

	@When("User validates FaxStatus is {string}")
	public void user_validates_FaxStatus_is(String sendFailed) {
		String respo = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxStatus").toString();
		assertEquals(respo, sendFailed);
	}

	@Then("User validates Errorcode is {int}")
	public void user_validates_Errorcode_is(int ErrorCode) {
		int respo = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].ErrorCode");

		assertEquals(ErrorCode, respo);
	}

	@Given("user sends request with valid FaxID")
	public void user_sends_request_with_valid_FaxID() {
		response = RestRequestUtils
				.getImage(ConfigReader.getProperty("getFaxByID_url") + ConfigReader.getProperty("param"), "pdf");
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
	public void user_send_request_with_valid_URL() {
		response = RestRequestUtils.get_fax_WithEMail(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("queryParamForEmail")));

	}

	@Given("user validate status code is {int}")
	public void user_validate_status_code_is2(int rCode) {
		response.getStatusCode();

		assertEquals(response.getStatusCode(), rCode);
	}

	@Then("user validates email is {string}")
	public void user_validates_email_is(String expectedEmail) {
		response.prettyPrint();
		String actualEmail = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].NotifyEmailAddress");

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

		String actualStatus = JsonPath.read(response.prettyPrint(), "$.RequestStatus.StatusText");

		assertEquals(actualStatus, expectedStatus);
	}

	@Given("user sends request to retrieve valid FaxID")
	public void user_sends_request_to_retrieve_valid_FaxID() {
		response = RestRequestUtils.getRecentCreatedFax(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("recentlyCreatedFaxID")));

	}

	@Then("user validates FaxNUmber is {string}")
	public void user_validates_FaxNUmber_is(String expectedNumber) {

		String resp = response.prettyPrint();
		String number = JsonPath.read(resp, "$.FaxInfo[0].FaxNumber");
		assertEquals(expectedNumber, number);
	}

	@Given("user submits getRequest retrieve data from inbound faxes")
	public void user_submits_getRequest_retrieve_data_from_inbound_faxes() throws InterruptedException {

		Thread.sleep(1000*300);
		response = RestRequestUtils.getFaxsTSINewRestApi(
				ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));
	}

	@When("user validates random TSI id and FaxStatus")
	public void user_validates_random_TSI_id_and_FaxStatus() {
		String resp = response.asPrettyString();
		
		System.out.println("**************************************");
		for (int i = 0; i < 1500; i++) {
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
		System.out.println("****the actual Fax status "+"****"+actualFaxStatus+"*****");
		if(actualFaxStatus!=null) {
			String FaxId=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId").toString();
			int pagesSent=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].PagesSent");
			System.out.println(" fax Id is "+ "****"+FaxId+"****"+ " and total pages sent is "+"***"+pagesSent+"****");
		}
	     
		}

	@Given("user submits new getCalls by this {string}")
	public void user_submits_new_getCalls_by_this(String creadS) {
		response = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url"));
	}

	@Given("user submits new getCalls by this {string} and {string}")
	public void user_submits_new_getCalls_by_this_and(String creadS, String FaxIDs) {
		response = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url") + FaxIDs, creadS);
	}

	@Given("i submit getCall to by FaxUserID")
	public void i_submit_getCall_to_by_FaxUserID() throws InterruptedException {
		
		Thread.sleep(1000*300);
		response = RestRequestUtils.getFaxWithRegistryBlankSetting(ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam")));
			System.out.println("************ "+ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam")));
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
	    System.out.println("The new generated fax Id is "+"** "+faxId+" **");
	    String errorText=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].ErrorText").toString();
	    if(errorText!="") { 
	    	System.out.println("There is error accured since it stack in scheduled "+"***"+errorText+"***");
	    }
	    
	    String actualsTSI_ID=JsonPath.read(response.asPrettyString(),"$.FaxInfo[0].TSI").toString();
	    System.out.println("TSI ID is "+"***** "+actualsTSI_ID+" *****");
	    System.out.println("Loading page.......................... ");
	    //String result;
	   
//	    do {
//	    	 result =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
//	    	 Thread.sleep(1000*5);
//	    }while(!result.equals("sent") );
//	    
//	    for(int i=0; i<100; i++){
//	    	Thread.sleep(1000*5);
//	    	result =JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
//	    	 
//	    	 if(result=="sent") break;
//	    }
	    if(actualsTSI_ID!=null) {
	    	int totalSentPages=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesSent");
	    	String FaxStatus=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
	    	System.out.println("FaxStatus is like ****"+FaxStatus+"**** and "+" total pages sent is **"+totalSentPages +"**");
	    }
	    
	}
	@Given("i submit getCall  by FaxUserId")
	public void i_submit_getCall_by_FaxUserId() throws InterruptedException {
		
		Thread.sleep(1000*300);
		
		response = RestRequestUtils.getFaxsTSINewRestApi(
				ConfigReader.getProperty("getFaxByID_url") + (ConfigReader.getProperty("newOutboundParam"))); 
	}

}

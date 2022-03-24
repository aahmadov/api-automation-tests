package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;

public class Post_calls_steps {

	Response response;

	@Given("User sends requests with valid number and attachment")
	public void user_sends_requests_with_valid_number_and_attachment() {
		response = RestRequestUtils.createFaxSingleNum(ConfigReader.getProperty("post_call_Url"),
				FileReader.readfile("Pages_1"), "(781)-885-4197");
		
		System.out.println("******* "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("******* "+(FileReader.readfile("Pages_1")+ " (781)-885-4197"));
		
		
	}

	@And("User validate if status code is {int}")
	public void user_validate_if_status_code_is(int expectedCode) {
		int realCode = response.getStatusCode();
		System.out.println(realCode);
		assertEquals(expectedCode,realCode);

	}

	@Then("User validates FaxNumber is {string}")
	public void user_validates_FaxNumber_is(String faxNumber) {

		String expected = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");

		assertEquals(expected, faxNumber);

	}

	@Given("User sends requests with no number")
	public void user_sends_requests_with_no_number() {
		response = RestRequestUtils.faxWithNoNumber(ConfigReader.getProperty("post_call_Url"),
				FileReader.readfile("Pages"), "");

	}

	@And("User wants validate the status code is {int}")
	public void user_wants_validate_the_status_code_is(int int1) {
		response.getStatusCode();
		assertEquals(response.getStatusCode(), int1);
	}

	@Then("User validates Statustext is {string}")
	public void user_validates_Statustext_is(String expectedError) {
		System.out.println(response.asPrettyString());
		String actual = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
		System.out.println(actual);
		assertEquals(actual, expectedError);
	}
	@Given("User sends requests with a single attachment to recipient")
	public void user_sends_requests_with_a_single_attachment_to_recipient() {
	    response=RestRequestUtils
	    		.sendFaxWithRecipent_details(ConfigReader.getProperty("post_call_Url")
	    				,FileReader.readfile("Pages"),ConfigReader.getProperty("Recipent_data1"));
	}

	@And("User validate the status code is {int}")
	public void user_validate_the_status_code_is(int realScode) {
	  
	  assertEquals(response.getStatusCode(),realScode);
	}

	@Then("User validates FaxId is generated")
	public void user_validates_FaxId_is_generated() {
	 String resp=response.prettyPrint();
		String data=JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();
		
		assertTrue(resp.contains(data));
	}
	
	@Given("User sends requests with a multiple attachments to two recipient")
	public void user_sends_requests_with_a_multiple_attachments_to_two_recipient() {
		response=RestRequestUtils
				.createFaxmultipRecip(ConfigReader.getProperty("post_call_Url"),
				   FileReader.readfile("Pages"), FileReader.readfile("Pages_1"), 
				   ConfigReader.getProperty("Recipent_data1"), ConfigReader.getProperty("Recipent_data2"));
	}
	@Given("User resends requests with failed faxID")
	public void user_resends_requests_with_failed_faxID() {
		response = RestRequestUtils.recendaFax(ConfigReader.getProperty("post_call_Url")+ConfigReader.getProperty("querParamforResent")
				,FileReader.readfile("Pages"),"9980080");
	}

	@Then("User validated new statusFax is {string}")
	public void user_validated_new_statusFax_is(String faxStatus) {
	   String resp=response.prettyPrint();
	   String actualStatCode=JsonPath.read(resp, "$.RequestStatus.StatusText").toString();
	   
	  assertEquals(faxStatus,actualStatCode);
	}
	
	@Given("User sends requests with FaxNumber & attachment")
	public void user_sends_requests_with_FaxNumber_attachment() {
	    response= RestRequestUtils.sendSimpleFax(ConfigReader.getProperty("post_call_Url"),FileReader.readfile("Pages"),"78907867");
	}

	@Given("User validates the status code is {int}")
	public void user_validates_the_status_code_is(int expectedStCode) {
	
	assertEquals(response.statusCode(),expectedStCode);
	}

	@Then("User gets new generated unique Id")
	public void user_gets_new_generated_unique_Id() {
		
	  int expectedFaxId=JsonPath.read(response.prettyPrint(),"$.FaxInfo[0].FaxId");
	  System.out.println(expectedFaxId);
	}
	@Given("User submits requests with TSI ID")
	public void user_submits_requests_with_TSI_ID() {
	  response=RestRequestUtils.sendFaxWithTSI(ConfigReader.getProperty("post_call_Url")+FileReader.randomNumberFor_TSI(),FileReader.readfile("20pages")
			  ,ConfigReader.getProperty("FaxN"));
			System.out.println("******* "+ConfigReader.getProperty("post_call_Url"));
			System.out.println("******* "+FileReader.readfile("20pages"));
			System.out.println("******* "+ConfigReader.getProperty("FaxN")); 
	}

	@Then("User validates is FaxNumber is same Like {string}")
	public void user_validates_is_FaxNumber_is_same_Like(String expectedFaxNum) {
	   String resp=response.prettyPrint();
	   String actualFaxNum=JsonPath.read(resp, "$.FaxInfo[0].FaxNumber");
			
	   assertEquals(actualFaxNum,expectedFaxNum);
	}
	
	
	@Given("User submits requests with creadentialInbound")
	public void user_submits_requests_with_creadentialInbound() {
		response=RestRequestUtils.sendFaxWithNewTSI(ConfigReader.getProperty("post_call_Url")+FileReader.randomNumberFor_TSI(),FileReader.readfile("20pages"),
				  ConfigReader.getProperty("FaxN"));
		System.out.println("************ "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("********** "+FileReader.readfile("20pages"));
		System.out.println("********* "+ConfigReader.getProperty("FaxN"));
		
	}
	
	@Then("User validates new FaxNumber is generated")
	public void user_validates_new_FaxNumber_is_generated() {
	   String faxId=JsonPath.read(response.prettyPrint(),"$.FaxInfo[0].FaxNumber");
	
	   
	  System.out.println("***************this is new genearated  Faxs number "+faxId);
	  
	}
	

	@Given("i submit new Fax regarding registry setting")
	public void i_submit_new_Fax_regarding_registry_setting() {
		response=RestRequestUtils.submitFaxwithBlankRegistry(ConfigReader.getProperty("post_call_Url")+FileReader.randomNumberFor_TSI(),FileReader.readfile("20pages"),
				  ConfigReader.getProperty("FaxN"));
		System.out.println("******* "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("******* "+FileReader.readfile("20pages"));
		System.out.println("****** "+ConfigReader.getProperty("FaxN"));
	}

	@And("first i validate status code is {int}")
	public void first_i_validate_status_code_is(int ExpectsCode) {
	    int actualStatusCode=response.getStatusCode();
	    assertEquals(actualStatusCode,ExpectsCode);
	}

	@Then("i verify number which i created is {string}")
	public void i_verify_number_which_i_created_is(String expectedFaxNumber) {
	    response.prettyPrint();
	    String actualfaxNumber=JsonPath.read( response.prettyPrint(),"$.FaxInfo[0].FaxNumber").toString();
	    System.out.println("the new submited Fax Number is " +actualfaxNumber);
	    assertEquals(actualfaxNumber,expectedFaxNumber);
	}
	
}

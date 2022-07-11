package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

public class Post_calls_steps {
	public static final Logger logger = LogManager.getLogger(Post_calls_steps.class);
	Response response;
    
	@Given("User sends requests with valid number and attachment")
	public void user_sends_requests_with_valid_number_and_attachment() {
		response = RestRequestUtils.createFaxSingleNum(ConfigReader.getProperty("post_call_Url"),
				FileReader.readfile("Pages_1"), "(781)-885-4198");
		
		System.out.println("------------------------------------------------------------------------");
	
		System.out.println("******* "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("******* "+(FileReader.readfile("Pages_1")+ " (781)-885-4198"));
		System.out.println("------------------------------------------------------------------------");
		
	}

	@And("User validate if status code is {int}")
	public void user_validate_if_status_code_is(int expectedCode) {
		int realCode = response.getStatusCode();
		logger.info("** this status code after a validation "+"**"+realCode+"**");
		assertEquals(expectedCode,realCode);
		logger.error("** this status code after a validation "+"**"+realCode+"**");
      System.out.println("** this status code after a validation "+"**"+realCode+"**");
      
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
		logger.error("this message will replace sysoutprint");
		System.out.println("------------------------------------------------------------------------");
		System.out.println("**"+(ConfigReader.getProperty("post_call_Url")));
		System.out.printf("**"+FileReader.readfile("Pages"), "");
		
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
		
		assertEquals(actual, expectedError);
	}
	@Given("User sends requests with a single attachment to recipient")
	public void user_sends_requests_with_a_single_attachment_to_recipient() {
	    response=RestRequestUtils
	    		.sendFaxWithRecipent_details(ConfigReader.getProperty("post_call_Url")
	    				,FileReader.readfile("Pages"),ConfigReader.getProperty("Recipent_data1"));
	    System.out.println("------------------------------------------------------------------------");
	    System.out.println("**"+(ConfigReader.getProperty("post_call_Url")));
	    System.out.println("**"+(ConfigReader.getProperty("Recipent_data1")));
	    System.out.println("**"+FileReader.readfile("Pages"));
	    System.out.println("------------------------------------------------------------------------");
	    
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
		System.out.println("------------------------------------------------------------------------");
		System.out.println("**"+(ConfigReader.getProperty("post_call_Url")));
		System.out.println("**"+FileReader.readfile("Pages"));
		System.out.println("**"+FileReader.readfile("Pages"));		
		System.out.println("**"+ConfigReader.getProperty("Recipent_data1"));
		System.out.println("**"+ConfigReader.getProperty("Recipent_data2"));
		System.out.println("------------------------------------------------------------------------");
	}
	@Given("User resends requests with failed faxID")
	public void user_resends_requests_with_failed_faxID() {
		response = RestRequestUtils.recendaFax(ConfigReader.getProperty("post_call_Url")+ConfigReader.getProperty("querParamforResent")
				,FileReader.readfile("Pages"),"9980080");
		System.out.println("------------------------------------------------------------------------");
		System.out.println("**"+(ConfigReader.getProperty("post_call_Url")+ConfigReader.getProperty("querParamforResent")));
		System.out.printf("**"+FileReader.readfile("Pages"),"9980080");
		
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
	    System.out.println("------------------------------------------------------------------------");
	    System.out.println("**"+(ConfigReader.getProperty("post_call_Url")));
		System.out.printf("**"+FileReader.readfile("Pages"),"78907867");
		
	}

	@Given("User validates the status code is {int}")
	public void user_validates_the_status_code_is(int expectedStCode) {
	
	assertEquals(response.statusCode(),expectedStCode);
	
	}

	@Then("User gets new generated unique Id")
	public void user_gets_new_generated_unique_Id() {
		
	  int expectedFaxId=JsonPath.read(response.prettyPrint(),"$.FaxInfo[0].FaxId");
	System.out.println("** unique fax Id is "+"**"+expectedFaxId+"**" );
	}
	@Given("User submits requests with TSI ID")
	public void user_submits_requests_with_TSI_ID() {
	  response=RestRequestUtils.sendFaxWithTSI(ConfigReader.getProperty("post_call_Url")+FileReader.randomNumberFor_TSI(),FileReader.readfile("16pages")
			  ,ConfigReader.getProperty("FaxN"));
	  System.out.println("------------------------------------------------------------------------");
			System.out.println("******* "+ConfigReader.getProperty("post_call_Url"));
			System.out.println("******* "+FileReader.readfile("16pages"));
			System.out.println("******* "+ConfigReader.getProperty("FaxN")); 
			System.out.println("------------------------------------------------------------------------");
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
		System.out.println("------------------------------------------------------------------------");
		System.out.println("************ "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("********** "+FileReader.readfile("20pages"));
		System.out.println("********* "+ConfigReader.getProperty("FaxN"));
		System.out.println("------------------------------------------------------------------------");
	}
	
	@Then("User validates new FaxNumber is generated")
	public void user_validates_new_FaxNumber_is_generated() {
	   String faxId=JsonPath.read(response.prettyPrint(),"$.FaxInfo[0].FaxNumber");
	  System.out.println("***** this is new genearated  Fax number "+"**"+faxId+"**");
	}
	  
	  @Then("User validates TSI id which is setup in post call")
	  public void user_validates_TSI_id_which_is_setup_in_post_call() throws InterruptedException {
		  Thread.sleep(1000*420);
	  response = RestRequestUtils.getFaxsTSINewRestApi2(
		ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));
	  String tsi=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
	  
	  System.out.println("***the random generated TSI on post call is  "+"***"+tsi+"***");
	}
	

	@Given("i submit new Fax regarding registry setting")
	public void i_submit_new_Fax_regarding_registry_setting() {
		response=RestRequestUtils.submitFaxwithBlankRegistry(ConfigReader.getProperty("post_call_Url")+FileReader.randomNumberFor_TSI(),FileReader.readfile("30pages"),
				  ConfigReader.getProperty("FaxN"));
		System.out.println("------------------------------------------------------------------------");
		System.out.println("****** "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("****** "+FileReader.readfile("30pages"));
		System.out.println("****** "+ConfigReader.getProperty("FaxN"));
		System.out.println("------------------------------------------------------------------------");
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
	
	@Given("i submit new Post call with special TSI")
	public void i_submit_new_Post_call_with_special_TSI() {
	   
		response=Second_RestRequestUtils.faxWith50Pages(ConfigReader.getProperty("post_call_Url")+FileReader.randomNumberFor_TSI(), FileReader.readfile("50page"), ConfigReader.getProperty("FaxN"));
		System.out.println("------------------------------------------------------------------------");
		System.out.println("****** "+ConfigReader.getProperty("post_call_Url"));
		System.out.println("****** "+FileReader.readfile("50page"));
		System.out.println("****** "+ConfigReader.getProperty("FaxN"));
		System.out.println("------------------------------------------------------------------------");
	}

	@Then("i validate outbound FaxId ,TSI and PagesSent")
	public void i_validate_outbound_FaxId_TSI_and_PagesSent() throws InterruptedException {
		Thread.sleep(1000*40);
	    response=Second_RestRequestUtils.Outbound_getCall50Page(ConfigReader.getProperty("getFaxByID_url"));
	    
		int faxid=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		System.out.println("***outbound faxId  generated "+"**"+faxid+"**");
		String Tsi=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
		System.out.println("***outbound Fax TSI  generated "+"**"+Tsi+"**");
		String totalPagesSent=JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesTotal").toString();
		System.out.println("***outbound Fax total page on attachment "+"**"+totalPagesSent+"**");
	}

	@Given("I submit post call for more than hundred page")
	public void i_submit_post_call_for_more_than_hundred_page() {
			String Tsi1= FileReader.randomNumberFor_TSI();
		
		response=Second_RestRequestUtils.faxWith100Pages(ConfigReader.getProperty("post_call_Url")+Tsi1,FileReader.readfile("100page")
				  ,ConfigReader.getProperty("FaxN"));
		
	}

}

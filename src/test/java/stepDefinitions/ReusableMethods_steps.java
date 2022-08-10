package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jayway.jsonpath.JsonPath;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

public class ReusableMethods_steps {
	public static final Logger logger = LogManager.getLogger(Post_calls_steps.class);
	Response response;
	String TSIofClumsy;
	String TSIofOutboundFax;

	@Given("User submits request with credentialNewOutbound")
	public void user_submits_request_with_credentialNewOutbound() throws InterruptedException {

		response = Second_RestRequestUtils.inbound_FaxwithCoverPage(
				ConfigReader.getProperty("post_call_Url") + FileReader.randomNumberFor_TSI(),
				FileReader.readfile("Pages3"), ConfigReader.getProperty("FaxN"));
		response.asPrettyString();
		int firstFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		System.out.println("******** faxId of post call  " + "**" + firstFaxId + "**");
		System.out.println("******** " + (ConfigReader.getProperty("post_call_Url")));

		System.out.println("******** " + ConfigReader.getProperty("FaxN"));
		System.out.println("******** " + FileReader.readfile("pages " + "and" + " included CoverPage"));

	}

	@Given("User validates the send status code is {int}")
	public void user_validates_the_send_status_code_is(int sendStatusCode) {
		int Code = response.getStatusCode();
		System.out.println("***** the expected status code " + "***" + sendStatusCode + "***"
				+ " send Fax statusCode lineUp with actual " + "***" + Code + "***");
		assertEquals(Code, sendStatusCode);

	}

	@Then("User validates outbound Fax TSI id")
	public void user_validates_outbound_Fax_TSI_id() throws InterruptedException {

		Thread.sleep(1000 * 40);
		response = Second_RestRequestUtils.getOutboundWithCoverPage(
				ConfigReader.getProperty("getFaxByID_url") + ConfigReader.getProperty("newOutboundParam"));
		response.asPrettyString();
		int totalPagesend = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesTotal");
		String Tsi = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
		System.out.println("****** the post call TSI id " + "**" + Tsi + "**" + " and "
				+ " total page in attachment is " + "**" + totalPagesend + "**");
	}

	@Then("User submits getRequest by credentialNewInbound to retrieve data from inbound faxes")
	public void user_submits_getRequest_by_credentialNewInbound_to_retrieve_data_from_inbound_faxes()
			throws InterruptedException {

		Thread.sleep(1000 * 480);
		System.out.println(
				"****** " + (ConfigReader.getProperty("inboundFax_url") + ConfigReader.getProperty("newInboundParam")));
		response = Second_RestRequestUtils.getInboundWithCoverPage1(
				ConfigReader.getProperty("inboundFax_url") + ConfigReader.getProperty("newInboundParam"));

	}

	@Then("User validates getStatusCode {int}")
	public void user_validates_getStatusCode(int getStatus) {
		response.getStatusCode();

		assertEquals(response.getStatusCode(), getStatus);
	}

	@Then("User validates inbound FaxStatus after a third attempt and total PagesReceived")

	public void user_validates_inbound_FaxStatus_after_a_third_attempt_and_total_PagesReceived()
			throws InterruptedException {

		String thirdAttemtpFaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();

		int PageRecieved = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesReceived");
		String Tsi = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
		int FaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		assertNotNull(PageRecieved);
		System.out.println("-------------------------------------------------------------");
		System.out.println("***** after last attempt fax id " + "*" + FaxId + "*" + " and "
				+ " total pageRecieved after the last attempt is " + "*" + PageRecieved + "*" + " and TSI id " + "*"
				+ Tsi + "*");
		System.out.println("***** fax Status after a last attempt is -  " + "*" + thirdAttemtpFaxStatus + "*");
	}

	@And("User validates inbound FaxStatus after a second attempt and total pages recieved")
	public void user_validates_inbound_FaxStatus_after_a_second_attempt_and_total_pages_recieved()
			throws InterruptedException {

		String FaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();

		int PageRecieved = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].PagesReceived");
		String tsioflastFax = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].TSI").toString();
		int secondFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId");
		assertNotNull(PageRecieved);

		System.out.println("-------------------------------------------------------------");
		System.out.println("***** after second attempt fax id " + "*" + secondFaxId + "*" + " and "
				+ " pageRecieved after a second attempt is " + "*" + PageRecieved + "*"
				+ " and TSI id after a second attempt " + "*" + tsioflastFax + "*");
		System.out.println("***** fax status after a second attempt  " + "*" + FaxStatus + "*");

	}

	@Then("User validates inbound FaxStatus after a first attempt and total pages recieved")
	public void user_validates_inbound_FaxStatus_after_a_first_attempt_and_total_pages_recieved()
			throws InterruptedException {

		
		String faxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
		int PageRecieved = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].PagesReceived");
		String tsiofThirdFax = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].TSI").toString();
		int thirdFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId");
		// assertNotNull(PageRecieved);

		String tsiofLastFax = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
		System.out.println("***** TSI of first post call" + "---------" + tsiofLastFax);
		if (tsiofThirdFax.equals(tsiofLastFax)) {

			System.out.println("-------------------------------------------------------------");
			System.out.println("***** after first attempt fax id " + "*" + thirdFaxId + "*" + " and "
					+ " pageRecieved after a first attmept is " + "*" + PageRecieved + "*"
					+ " and TSI id after a first attempt " + "*" + tsiofThirdFax + "*");
			System.out.println("***** fax status after a first attempt  " + "*" + faxStatus + "*");
		} else {
			System.out.println("***** there is only two attempts , per current registry settings");
		}
	}

	@Given("I submit post call")
	public void i_submit_post_call() {
		response = Second_RestRequestUtils.clumsyOutbound_Fax(
				ConfigReader.getProperty("outbound_URl_65") + FileReader.randomNumberFor_TSI(),
				FileReader.readfile("23page"), ConfigReader.getProperty("FaxN"));
		response.asPrettyString();
		int firstFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		System.out.println("******** faxId of post call  " + "**" + firstFaxId + "**");
		System.out.println("******** " + (ConfigReader.getProperty("outbound_URl_65")));

		System.out.println("******** " + ConfigReader.getProperty("FaxN"));
		System.out.println("******** " + FileReader.readfile("23page "));

	}

	@Given("I validate new records been created with status code {int}")
	public void i_validate_new_records_been_created_with_status_code(int statusCode) {
		response.getStatusCode();
		assertEquals(response.getStatusCode(), statusCode);
	}

	@When("I validate outbound  FaxId and TSI")
	public void i_validate_outbound_FaxId_and_TSI() throws InterruptedException {
		Thread.sleep(1000 * 480);
		response = Second_RestRequestUtils.getCall_clumsy_65Validation(
				ConfigReader.getProperty("outbound_URl_65") + ConfigReader.getProperty("OutboundParam_65"));

		TSIofClumsy = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
		String faxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
		String error = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].ErrorText").toString();
		System.out.println("**" + "TSI of outbound fax is " + TSIofClumsy);
		if (faxStatus.equals("scheduled")) {

			logger.error("this message confirms, error occurred due the connection  " + "---------..... " + error);

		} else if (faxStatus.equals("sendFailed")) {

			logger.error("this message confirms, error occurred due the connection  " + "-- " + error);
			System.out.println("this message confirms, error occurred due the connection  " + "-- " + error);
		}

		else {

			int faxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");

			String Faxstatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();

			String totalPagesOnAttachmenets = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesTotal")
					.toString();
			String totalPagesSent = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesSent").toString();

			System.out.println("***" + "the outbound faxId is " + "** " + faxId + "**");
			System.out.println("***" + "the outbound faxStatus is like " + "** " + Faxstatus + "**");

			System.out.println("**" + "total page on attachment  " + totalPagesOnAttachmenets);
			System.out.println("**" + "count of pages been sent  " + totalPagesSent);

		}
	}

	@Then("I submit Get call in inbound FaxUserId")
	public void i_submit_Get_call_in_inbound_FaxUserId() throws InterruptedException {

		Thread.sleep(1000 * 420);

		response = Second_RestRequestUtils.Inbound_getCall_clumsy_65Validation(
				ConfigReader.getProperty("inboundFax_url") + ConfigReader.getProperty("newInboundParam"));

		int FaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		int faxId_first = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId");
		int faxId_last = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId");
		String TSI = JsonPath.read(response.asPrettyString(), "$.FaxInfo.[0].TSI").toString();
		String TSIofsecondAttempt = JsonPath.read(response.asPrettyString(), "$.FaxInfo.[1].TSI").toString();
		String TSIofthirdAttempt = JsonPath.read(response.asPrettyString(), "$.FaxInfo.[2].TSI").toString();
		String secondAttemptFaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
		String firstAttemptFaxsStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
		String lastAttemptFaxsStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();

		int lastAttemptPageReceived = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesReceived");
		int firstAttemptpageReceived = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].PagesReceived");
		int secondAttemptpageReceived = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].PagesReceived");

		

		if (TSI.equals(TSIofClumsy) && TSIofsecondAttempt.equals(TSIofClumsy)
				&& TSIofthirdAttempt.equals(TSIofClumsy)) {
            System.out.println("** " + "TSI of inbound fax is " + TSI);
			System.out.println("** " + "last attempt FaxId of inbound fax is " + "** " + FaxId + "");
			System.out.println("** " + "second attempt FaxId of inbound fax is " + "** " + faxId_first + "**");
			System.out.println("** " + "first attempt FaxId of inbound fax is " + "** " + faxId_last + "**");

			System.out.println("** " + "FaxStatus of last Attempt " + "** " + secondAttemptFaxStatus + "**");
			System.out.println("** " + "FaxStatus of second Attempt " + "** " + firstAttemptFaxsStatus + "**");
			System.out.println("** " + "FaxStatus of first Attempt " + "** " + lastAttemptFaxsStatus + "**");

			System.out.println(
					"** " + "TotalPages received is at last attempt " + "** " + lastAttemptPageReceived + "**");
			System.out.println(
					"** " + "TotalPages received is at second attempt " + "** " + secondAttemptpageReceived + "**");
			System.out.println(
					"** " + "TotalPages received is at first attempt " + "** " + firstAttemptpageReceived + "**");

		} else if (TSI.equals(TSIofClumsy) && TSIofsecondAttempt.equals(TSIofClumsy)) {

			System.out.println("** " + "last attempt FaxId of inbound fax is " + "** " + FaxId + "");
			System.out.println("** " + "first attempt FaxId of inbound fax is " + "** " + faxId_first + "**");
			System.out.println("** " + "FaxStatus of last Attempt " + "** " + secondAttemptFaxStatus + "**");
			System.out.println("** " + "FaxStatus of first Attempt " + "** " + firstAttemptFaxsStatus + "**");
			System.out.println("** " + "TotalPages received is at last attempt " + "** " + lastAttemptPageReceived
					+ "**" + "** " + TSI);
			System.out.println("** " + "TotalPages received is at first attempt " + "** " + firstAttemptpageReceived
					+ "**" + "** " + TSIofsecondAttempt);
		}

		else if (TSI.equals(TSIofClumsy) && !TSIofsecondAttempt.equals(TSIofClumsy)) {

			System.out.println("** " + "TotalPages received is at first attempt " + "** " + firstAttemptpageReceived
					+ "**" + "and " + "Tsi id is " + "* " + TSI + "*");
			System.out.println("________DebugAbortSendAtPage set-up probably more then total page count");
		}

		else if (!TSI.equals(TSIofClumsy)) {

			logger.error("---------.....this message about an error ,inbound faxes not generated");
		} else {

			System.out.println("** " + " first attempt FaxId of inbound  fax is" + faxId_first);
			System.out.println("** " + " second attempt FaxId of inbound fax is" + FaxId);
			System.out.println("** " + "FaxStatus of 1st Attempt " + firstAttemptFaxsStatus);
			System.out.println("** " + "FaxStatus of 2nd Attempt " + secondAttemptFaxStatus);
			System.out.println(
					"** " + "TotalPages received is at first attempt " + "**" + firstAttemptpageReceived + "**");
			System.out
					.println("** " + "TotalPages received is at last attempt " + "**" + lastAttemptPageReceived + "**");
		}
	}

	@Then("I check and validate status code is {int}")
	public void i_check_and_validate_status_code_is(int statusCode2) {
		response.statusCode();

		assertEquals(response.statusCode(), statusCode2);

	}

	@Given("I want to submit new records to outbound Fax")
	public void i_want_to_submit_new_records_to_outbound_Fax() throws InterruptedException {

	
		response = Second_RestRequestUtils.secondClumsy100PageSubmit(
				ConfigReader.getProperty("outbound_URl_65") + FileReader.randomNumberFor_TSI(),
				FileReader.readfile("100page"), ConfigReader.getProperty("FaxN"));

		response.asPrettyString();

		int firstFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");

		System.out.println("******** faxId of post call  " + "**" + firstFaxId + "**");
		System.out.println("******** " + (ConfigReader.getProperty("outbound_URl_65")));

		System.out.println("******** " + ConfigReader.getProperty("FaxN"));
		System.out.println("******** " + FileReader.readfile("100page "));
	}

	@Given("I validate new status code {int} was succesfully generated")
	public void i_validate_new_status_code_was_succesfully_generated(int statusCodeClumsy) {
		response.getStatusCode();
		System.out.println("** " + "new status code generated is " + "** " + statusCodeClumsy);
		assertEquals(response.getStatusCode(), statusCodeClumsy);
	}

	@When("I validate FaxId and TsiId of outbound fax is created")
	public void i_validate_FaxId_and_TsiId_of_outbound_fax_is_created() throws InterruptedException {

		Thread.sleep(1000*240);
		response = Second_RestRequestUtils.secondClumsy100PageGet(
				ConfigReader.getProperty("outbound_URl_65") + ConfigReader.getProperty("OutboundParam_65"));

		TSIofOutboundFax = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].TSI").toString();
		String faxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
		String error = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].ErrorText").toString();
		System.out.println("**" + "TSI of outbound fax is " + TSIofOutboundFax);
		if (faxStatus.equals("scheduled")) {

			logger.error("this message confirms, error occurred due the connection  " + "---------....." + error);

		} else if (faxStatus.equals("sendFailed")) {

			logger.error("this message confirms, error occurred due the connection"+ "or due the mentioned error message  " +"**"+ error);
			
		}

		else {

			int faxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");

			String Faxstatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();

			String totalPagesOnAttachmenets = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesTotal")
					.toString();
			String totalPagesSent = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesSent").toString();

			System.out.println("***" + "the outbound faxId is " + "** " + faxId + "**");
			System.out.println("***" + "the outbound faxStatus is like " + "** " + Faxstatus + "**"+"and FaxId"+faxId);

			System.out.println("**" + "total page on attachment  " + totalPagesOnAttachmenets);
			System.out.println("**" + "count of pages been sent  " + totalPagesSent);
		}
	}
	@When("I submit Get call to Inbound Fax")
	public void i_submit_Get_call_to_Inbound_Fax() throws InterruptedException {
		Thread.sleep(1000 * 480);
		response = Second_RestRequestUtils.secondClumsy100InboundPageGet(
				ConfigReader.getProperty("inboundFax_url") + ConfigReader.getProperty("newInboundParam"));

		int FaxId_first = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
		int faxId_second = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId");
		int faxId_last = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId");
		String TSI = JsonPath.read(response.asPrettyString(), "$.FaxInfo.[0].TSI").toString();
		String TSIofsecondAttempt = JsonPath.read(response.asPrettyString(), "$.FaxInfo.[1].TSI").toString();
		String TSIofthirdAttempt = JsonPath.read(response.asPrettyString(), "$.FaxInfo.[2].TSI").toString();
		String firstAttemptFaxsStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
		String secondAttemptFaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
		String lastAttemptFaxsStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();

		int  firstAttemptpageReceived= JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesReceived");
		int secondAttemptpageReceived = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].PagesReceived");
		int lastAttemptPageReceived = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].PagesReceived");

		if (TSI.equals(TSIofOutboundFax) && TSIofsecondAttempt.equals(TSIofOutboundFax)
				&& TSIofthirdAttempt.equals(TSIofOutboundFax)) {
			System.out.println("** " + "TSI of inbound fax is " + TSI);
			System.out.println("** " + "last attempt FaxId of inbound fax is " + "** " + FaxId_first + "");
			System.out.println("** " + "second attempt FaxId of inbound fax is " + "** " + faxId_second + "**");
			System.out.println("** " + "first attempt FaxId of inbound fax is " + "** " + faxId_last + "**");

			System.out.println("** " + "FaxStatus of last Attempt " + "** " + secondAttemptFaxStatus + "**");
			System.out.println("** " + "FaxStatus of second Attempt " + "** " + firstAttemptFaxsStatus + "**");
			System.out.println("** " + "FaxStatus of first Attempt " + "** " + lastAttemptFaxsStatus + "**");

			System.out.println(
					"** " + "TotalPages received is at last attempt " + "** " + lastAttemptPageReceived + "**");
			System.out.println(
					"** " + "TotalPages received is at second attempt " + "** " + secondAttemptpageReceived + "**");
			System.out.println(
					"** " + "TotalPages received is at first attempt " + "** " + firstAttemptpageReceived + "**");

		} else if (TSI.equals(TSIofOutboundFax) && TSIofsecondAttempt.equals(TSIofOutboundFax)) {

			System.out.println("** " + "last attempt FaxId of inbound fax is " + "** " + faxId_last + "");
			System.out.println("** " + "first attempt FaxId of inbound fax is " + "** " + FaxId_first + "**");
			System.out.println("** " + "FaxStatus of last Attempt " + "** " + secondAttemptFaxStatus + "**");
			System.out.println("** " + "FaxStatus of first Attempt " + "** " + firstAttemptFaxsStatus + "**");
			System.out.println("** " + "TotalPages received is at last attempt " + "** " + lastAttemptPageReceived
					+ "**" + "** " + TSI);
			System.out.println("** " + "TotalPages received is at first attempt " + "** " + firstAttemptpageReceived
					+ "**" + "** " + TSIofsecondAttempt);
		}

		else if (TSI.equals(TSIofOutboundFax) && !TSIofsecondAttempt.equals(TSIofOutboundFax)) {

			System.out.println("** " + "TotalPages received is at first attempt " + "** " + firstAttemptpageReceived
					+ "**" + "and " + "Tsi id is " + "* " + TSI + "*");
			System.out.println("________DebugAbortSendAtPage set-up probably more then total page count");
		}

		else if (!TSI.equals(TSIofOutboundFax)) {

			logger.error("---------.....this message about an error ,inbound faxes not generated");
		} else {

			System.out.println("** " + " first attempt FaxId of inbound  fax is" + FaxId_first);
			System.out.println("** " + " second attempt FaxId of inbound fax is" + faxId_second);
			System.out.println("** " + "FaxStatus of 1st Attempt " + firstAttemptFaxsStatus);
			System.out.println("** " + "FaxStatus of 2nd Attempt " + secondAttemptFaxStatus);
			System.out.println(
					"** " + "TotalPages received is at first attempt " + "**" + firstAttemptpageReceived + "**");
			System.out
					.println("** " + "TotalPages received is at last attempt " + "**" + lastAttemptPageReceived + "**");
		}
	}

	@When("I validate status code of Get call is {int}")
	public void i_validate_status_code_of_Get_call_is(int Expected_statusC_of_inbound_GetCall) {
		int actualstatus = response.getStatusCode();
		
		assertEquals(actualstatus,Expected_statusC_of_inbound_GetCall);
		System.out.println("The status code of inbound_Getcall is "+"** "+actualstatus+" **");
	}

	@Given("send simple fax")
	public void send_simple_fax() {

	}

	@Given("user validates the status code is {int}")
	public void user_validates_the_status_code_is(Integer int1) {

	}


}
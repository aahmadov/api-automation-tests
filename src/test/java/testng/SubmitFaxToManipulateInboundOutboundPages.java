package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.FileReader;
import utils.JsonUtils;
import utils.Second_RestRequestUtils;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SubmitFaxToManipulateInboundOutboundPages extends TestBase {

    @Test(testName = "Submit Fax to manipulate outbound&inbound Data", groups = {"Regression5"})
    public void submitFaxToManipulateInboundOutboundPages() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Response response = Second_RestRequestUtils.faxWith50Pages(data.get("post_call_Url") + FileReader.randomNumberFor_TSI(),
                FileReader.readfile("50page"), data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("****** " + data.get("post_call_Url"));
        System.out.println("****** " + FileReader.readfile("50page"));
        System.out.println("****** " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(response.getStatusCode(), 201);

        Thread.sleep(1000 * 40);
        Response outboundGetCall50Page = Second_RestRequestUtils.Outbound_getCall50Page(data.get("post_call_Url"),
                data.get("credentialOutbound"));

        int faxid = JsonPath.read(outboundGetCall50Page.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("***outbound FaxId  is " + "**" + faxid + "**");
        String Tsi = JsonPath.read(outboundGetCall50Page.asPrettyString(), "$.FaxInfo[0].TSI").toString();
        System.out.println("***outbound Fax TSI is " + "**" + Tsi + "**");
        String totalPagesSent = JsonPath.read(outboundGetCall50Page.asPrettyString(), "$.FaxInfo[0].PagesTotal").toString();
        System.out.println("***outbound Fax total page on attachment " + "**" + totalPagesSent + "**");


        Thread.sleep(1000 * 420);
        Response inbound50Page = Second_RestRequestUtils.getInbound50Page(
                ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));

        assertEquals("it is not expected status Code", inbound50Page.getStatusCode(), 200);

        String lastAttemptTSIInbound = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[0].TSI").toString();
        String PagesReceived = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[0].PagesReceived").toString();

        System.out.println("****inbound TSI id is same with outbound TSI id " + "**" + lastAttemptTSIInbound + "**");


        String secondAttemptTSIInbound = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[1].TSI").toString();
        String firstAttemptTSIInbound = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[2].TSI").toString();

        String lastAttemptFaxStatus = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
        String secondAttemptFaxStatus = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
        String firstAttemptFaxStatus = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
        int lastFaxId = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[0].FaxId");
        int secondFaxId = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[1].FaxId");
        int firstdFaxId = JsonPath.read(inbound50Page.asPrettyString(), "$.FaxInfo[2].FaxId");

        System.out.println("*** faxstatus after a first attempt  is " + "**" + firstAttemptFaxStatus + "**" + " and FaxId is " + "**" + firstdFaxId + "**" + "and TSI id is " + "**" + firstAttemptTSIInbound + "**");
        System.out.println("*** faxstatus after a second attempt is " + "**" + secondAttemptFaxStatus + "**" + " and FaxId is " + "**" + secondFaxId + "**" + "and TSI id is " + "**" + secondAttemptTSIInbound + "**");
        System.out.println("*** faxstatus after a last attempt   is " + "**" + lastAttemptFaxStatus + "**" + "   and FaxId is " + "**" + lastFaxId + "**" + "and TSI id is " + "**" + lastAttemptTSIInbound + "**" + "and total page received in inbound from out of 50 is" + "**" + PagesReceived + "**");
    }
}

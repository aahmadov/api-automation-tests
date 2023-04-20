package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.Second_RestRequestUtils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SubmitFaxToManipulateInboundOutboundPages extends TestBase {

    @Test(testName = "Submit Fax to manipulate outbound&inbound Data", groups = {"Regression"})
    public void submitFaxToManipulateInboundOutboundPages() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response response = Second_RestRequestUtils.faxWith50Pages(data.get("post_call_Url") + tsi,
                FileReader.readfile("50page"), data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("****** " + data.get("post_call_Url"));
        System.out.println("****** " + FileReader.readfile("50page"));
        System.out.println("****** " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(response.getStatusCode(), 201);


        Response outbound;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outbound = Second_RestRequestUtils.getOutboundWithCoverPage(
                    data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
            Assert.assertEquals(200, outbound.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 15);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }

        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));
        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString().toString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from response
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());

        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }
    }
    @Test(testName = "Submit Fax to manipulate outbound&inbound Data", groups = {"Regression1"})
    public void submitFaxToManipulateInboundOutboundPagesCopy() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response response = Second_RestRequestUtils.faxWith50Pages(data.get("post_call_Url") + tsi,
                FileReader.readfile("50page"), data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("****** " + data.get("post_call_Url"));
        System.out.println("****** " + FileReader.readfile("50page"));
        System.out.println("****** " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(response.getStatusCode(), 201);


        Response outbound;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outbound = Second_RestRequestUtils.getOutboundWithCoverPage(
                    data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
            Assert.assertEquals(200, outbound.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 15);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }

        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));
        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString().toString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from response
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());

        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }
    }
}

package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.Second_RestRequestUtils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.testng.Assert.assertEquals;

public class TestInbound extends TestBase {

    @Test(testName = "Dynamic scenario for fax status and page number validation from inbound", groups = {"smoke"})
    public void faxStatusAndPageNumberValidationFromInbound2() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response inboundFaxwithCoverPage = Second_RestRequestUtils.inbound_FaxwithCoverPage(
                data.get("post_call_Url") + tsi,
                file, data.get("faxNumber"), data.get("credentialNewOutbound"));
        int code = inboundFaxwithCoverPage.getStatusCode();
        System.out.println("***** the expected status code " + "***" + data.get("inboundStatusCode") + "***"
                + " send Fax statusCode lineUp with actual " + "***" + code + "***");
        assertEquals(Integer.parseInt(data.get("inboundStatusCode")), code);
        int faxId = JsonPath.read(inboundFaxwithCoverPage.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("******** faxId of post call  " + "**" + faxId + "**");
        System.out.println("******** " + (data.get("post_call_Url")));
        System.out.println("******** " + data.get("faxNumber"));
        System.out.println("******** " + file);

        Response outboundWithCoverPage;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outboundWithCoverPage = Second_RestRequestUtils.getOutboundWithCoverPage(
                    data.get("getFaxByID_url") + data.get("newOutboundParam"), data.get("credentialNewOutbound"));
            assertEquals(200, outboundWithCoverPage.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outboundWithCoverPage.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outboundWithCoverPage.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 10);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }

        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));
        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialNewInbound"));
        assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());

        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
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


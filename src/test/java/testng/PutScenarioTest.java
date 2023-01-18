package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.testng.Assert.assertEquals;

public class PutScenarioTest extends TestBase {

    @Test(testName = "Put scenario", groups = {"Regression1"})
    public void putScenarioForRegistrySettings() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Response response = RestRequestUtils.putScenario(data.get("put_call_Url"));
        assertEquals(response.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url")));
        System.out.println("------------------------------------------------------------------------");


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
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        if (tsiArray.size() == 0) {
            fail("No response found with the tsi id" + onlyTsi);
        }
        String faxNumber = ((LinkedHashMap) tsiArray.get(0)).get("DestFaxNumber").toString();
        //assertEquals(faxNumber, data.get("faxNumber"));

        Thread.sleep(1000 * 600);

        Response inboundResponse = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialNewInbound"));
        assertEquals(200, inboundResponse.getStatusCode());

        JSONArray tsiArray1 = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray1.toJSONString());


        if (tsiArray1.size() == 0) {
            fail("No response found with the tsi id" + onlyTsi);
        }
        String finalFaxNumber = ((LinkedHashMap) tsiArray1.get(0)).get("DestFaxNumber").toString();
        assertEquals(finalFaxNumber, data.get("updatedFaxNumber"));
    }
}

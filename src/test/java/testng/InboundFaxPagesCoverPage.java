package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.Second_RestRequestUtils;

import java.io.File;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;

public class InboundFaxPagesCoverPage extends TestBase {

    @Test(testName = "Dynamic scenario for fax status and page number validation from inbound", groups = {"smoke2"})
    public void faxStatusAndPageNumberValidationFromInbound() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response inboundFaxwithCoverPage = Second_RestRequestUtils.inbound_FaxwithCoverPage(
                data.get("post_call_Url") + FileReader.randomNumberFor_TSI(),
                file, data.get("faxNumber"), data.get("credentialNewOutbound"));
        int faxId = JsonPath.read(inboundFaxwithCoverPage.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("******** faxId of post call  " + "**" + faxId + "**");
        System.out.println("******** " + (data.get("post_call_Url")));
        System.out.println("******** " + data.get("faxNumber"));
        System.out.println("******** " + file);

        int code = inboundFaxwithCoverPage.getStatusCode();
        System.out.println("***** the expected status code " + "***" + data.get("inboundStatusCode") + "***"
                + " send Fax statusCode lineUp with actual " + "***" + code + "***");
        assertEquals(Integer.parseInt(data.get("inboundStatusCode")), code);

        Thread.sleep(1000 * 40);
        Response outboundWithCoverPage = Second_RestRequestUtils.getOutboundWithCoverPage(
                data.get("getFaxByID_url") + data.get("newOutboundParam"), data.get("credentialNewOutbound"));

        int totalPagesend = JsonPath.read(outboundWithCoverPage.asPrettyString(), "$.FaxInfo[0].PagesTotal");
        String Tsi = JsonPath.read(outboundWithCoverPage.asPrettyString(), "$.FaxInfo[0].TSI").toString();
        System.out.println("****** the post call TSI id " + "**" + Tsi + "**" + " and "
                + " total page in attachment is " + "**" + totalPagesend + "**");

        Thread.sleep(1000 * 480);
        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));
        Response inboundWithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialNewInbound"));
        assertEquals(200, inboundWithCoverPage1.getStatusCode());

        //first
        String firstFaxStatus = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[0].FaxStatus");
        int firstPagesReceived = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[0].PagesReceived");
        String firstTsi = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[0].TSI").toString();
        int firstFaxId = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[0].FaxId");
        assertNotNull(firstPagesReceived);
        System.out.println("-------------------------------------------------------------");
        System.out.println("***** after last attempt fax id " + "*" + firstFaxId + "*" + " and "
                + " total pageRecieved after the last attempt is " + "*" + firstPagesReceived + "*" + " and TSI id " + "*"
                + firstTsi + "*");
        System.out.println("***** fax Status after a last attempt is -  " + "*" + firstFaxStatus + "*");

        //second
        String secondFaxStatus = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
        int secondPagesReceived = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[1].PagesReceived");
        String secondTsi = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[1].TSI").toString();
        int secondFaxId = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[1].FaxId");
        assertNotNull(secondPagesReceived);

        System.out.println("-------------------------------------------------------------");
        System.out.println("***** after second attempt fax id " + "*" + secondFaxId + "*" + " and "
                + " pageRecieved after a second attempt is " + "*" + secondPagesReceived + "*"
                + " and TSI id after a second attempt " + "*" + secondTsi + "*");
        System.out.println("***** fax status after a second attempt  " + "*" + secondFaxStatus + "*");

        //third
        String thirdFaxStatus = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
        int thirdPagesReceived = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[2].PagesReceived");
        String thirdTsi = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[2].TSI").toString();
        int thirdFaxId = JsonPath.read(inboundWithCoverPage1.asPrettyString(), "$.FaxInfo[2].FaxId");
        // assertNotNull(thirdPagesReceived);

        System.out.println("***** TSI of first post call" + "---------" + firstTsi);
        if (thirdTsi.equals(firstTsi)) {

            System.out.println("-------------------------------------------------------------");
            System.out.println("***** after first attempt fax id " + "*" + thirdFaxId + "*" + " and "
                    + " pageRecieved after a first attmept is " + "*" + thirdPagesReceived + "*"
                    + " and TSI id after a first attempt " + "*" + thirdTsi + "*");
            System.out.println("***** fax status after a first attempt  " + "*" + thirdFaxStatus + "*");
        } else {
            System.out.println("***** there is only two attempts , per current registry settings");
        }
    }
}

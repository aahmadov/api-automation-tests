package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class OutboundInboundFaxPageValidation30Test extends TestBase {

    @Test(testName = "Validates the number of outbound pages with some registry setting (\"30 pages\")", groups = {"Regression3"})
    public void outboundFaxPageValidation30() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + FileReader.randomNumberFor_TSI(),
                FileReader.readfile("30page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("30page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));

        String faxNumber = JsonPath.read(responseSubmitFax.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        System.out.println("***** this is new generated  Fax number " + "**" + faxNumber + "**");
        assertEquals(faxNumber, data.get("faxNumber"));

        Thread.sleep(1000 * 420);
        Response getFax = RestRequestUtils.getFaxsTSINewRestApi2(
                data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
        assertEquals(getFax.getStatusCode(), 200);

        String faxId = JsonPath.read(getFax.asPrettyString(), "$.FaxInfo[0].FaxId").toString();
        System.out.println("**the new generated fax Id is " + "** " + faxId + " **");

        String actualsTSI_ID = JsonPath.read(getFax.asPrettyString(), "$.FaxInfo[0].TSI").toString();
        System.out.println("**Tsi id is " + "***** " + actualsTSI_ID + " *****");

        String FaxStatus = JsonPath.read(getFax.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
        int totalSentPages = JsonPath.read(getFax.asPrettyString(), "$.FaxInfo[0].PagesSent");
        System.out.println("**faxStatus of postcall is **** " + FaxStatus + "**** and " + " total pages sent is **" + totalSentPages + "**");

        Response response = RestRequestUtils.getFaxsafterAllattempts(data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));

        String thirdAttemptFaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxStatus").toString();
        String secondAttemptFaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxStatus").toString();
        String firstAttemptFaxStatus = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxStatus").toString();
        String firstFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId").toString();
        String secondFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].FaxId").toString();
        String thirdFaxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[2].FaxId").toString();

        System.out.println("*** faxstatus after a first attempt is " + "**" + firstAttemptFaxStatus + "**" + " and FaxId is " + "**" + thirdFaxId + "**");
        System.out.println("*** faxstatus after a second attempt is" + "**" + secondAttemptFaxStatus + "**" + " and FaxId is " + "**" + secondFaxId + "**");
        System.out.println("*** faxstatus after a third attempt is " + "**" + thirdAttemptFaxStatus + "**" + " and FaxId is " + "**" + firstFaxId + "**");
    }
}

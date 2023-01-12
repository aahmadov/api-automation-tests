package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class InboundFaxPageValidation20Test extends TestBase {

    @Test(testName = "validates the number of inbound Fax pages with registry setting (\"20 pages\")", groups = {"Regression1"})
    public void inboundFaxPageValidation20() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;


        Response response2 = RestRequestUtils.putScenario(data.get("put_call_Url"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url")));
        System.out.println("------------------------------------------------------------------------");
        Thread.sleep(1000*20);
        System.out.println(": registry settings "+"Abort page at 0");


        Response response = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + FileReader.randomNumberFor_TSI(),
                FileReader.readfile("20page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("20page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(response.statusCode()), data.get("statusCode"));

        String faxId = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        System.out.println("***** this is new generated  Fax number " + "**" + faxId + "**");

        Thread.sleep(1000 * 120);
        Response getFax = RestRequestUtils.getFaxsTSINewRestApi2(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        String tsi = JsonPath.read(getFax.asPrettyString(), "$.FaxInfo[0].TSI").toString();

        System.out.println("***the random generated TSI on post call is  " + "***" + tsi + "***");

        Response getFaxWithAPI = RestRequestUtils.getFaxsTSINewRestApi(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        assertEquals(getFaxWithAPI.getStatusCode(), 200);

        String resp = getFaxWithAPI.asPrettyString();
        List<String> tsis = JsonPath.read(resp, "$.FaxInfo[*].TSI");

        System.out.println("**************************************");
        for (int i = 0; i < tsis.size(); i++) {
            System.out.println(i);
            String num = Integer.toString(i);
            String actualTSId = JsonPath.read(resp, "$.FaxInfo[" + num + "].TSI").toString();
            System.out.println("The random generated TSI id is " + "***" + actualTSId + "***");
            if (actualTSId != null) {
                int FaxId = JsonPath.read(resp, "$.FaxInfo[" + num + "].FaxId");
                System.out.println("The new generated FaxID is*** " + "***" + FaxId + "***");
                String FaxStatus = (JsonPath.read(resp, "$.FaxInfo[" + num + "].FaxStatus"));
                int pageRecieved = JsonPath.read(resp, "$.FaxInfo[" + num + "].PagesReceived");
                System.out.println("Expected Fax Status is  **" + FaxStatus + "**" + "and pages received  " + "**" + pageRecieved + "**");
                break;
            }
        }
    }
}

package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.io.File;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PostScenariosForSmokeTest_Tiff extends TestBase {

    @Test(testName = "Send Fax Data with recipient Details", groups = {"smoke"})
    public void sendFaxDataWithRecipientDetails() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithRecipent_details(data.get("post_call_Url"),
                file, data.get("Recipent_data1"), data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("**" + (data.get("post_call_Url")));
        System.out.println("**" + (data.get("Recipent_data1")));
        System.out.println("**" + file);
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();
        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();

        assertTrue(resp.contains(faxId));
    }

    @Test(testName = "Send Fax with valid Number and Attachment", groups = {"smoke"})
    public void sendFaxWithValidNumberAndAttachment() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response response = RestRequestUtils.createFaxSingleNum(data.get("post_call_Url"),
                file, data.get("faxNumber"), data.get("credentials"));

        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get("post_call_Url"));
        System.out.println("******* " + file + " " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        System.out.println("****** this status code after a validation " + "**" + response.getStatusCode() + "**");

        String actual = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        assertEquals(data.get("faxNumber"), actual);
    }

    @Test(testName = "Send Fax Data without Number (negative scenario)", groups = {"smoke"})
    public void sendFaxDataWithoutNumber() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response response = RestRequestUtils.faxWithNoNumber(data.get("post_call_Url"),
                file, data.get("faxNumber"), data.get("credentials"));

        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get("post_call_Url"));
        System.out.println("******* " + file + " " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());

        String actual = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
        Assert.assertEquals(data.get("expectedErrorMessage"), actual);
    }
    @Test(testName = "Send Fax Data with multiple attachment", groups = {"Regression"})
    public void sendFaxDataWithTiff() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        String tsi = FileReader.randomNumberFor_TSI();
        File file = FileReader.getFileUsingPageSize2forTiff(data.get("pageSize"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithRecipent_withTiff(data.get("post_call_Url")+tsi,
                file, data.get("faxNumber"), data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("TSI ID "+tsi);
        System.out.println("**" + (data.get("post_call_Url")));
        System.out.println("**" + (data.get("faxNumber")));
        System.out.println("**" + file);
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();
        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();

        assertTrue(resp.contains(faxId));
        Thread.sleep(1000*60);
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFaxforTiff(data.get("get_call_Url"), data.get("credentialInbound"));

        String Status= JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxStatus");

        int pagesCount = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].PagesReceived");
        System.out.println("Status message: " +Status + " and pagesReceived about " +pagesCount);

    }

    @Test(testName = "Send Fax Data with multiple attachment", groups = {"Regression1"})
    public void sendFaxDataWithTiff_Copy() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        String tsi = FileReader.randomNumberFor_TSI();
        File file = FileReader.getFileUsingPageSize2forTiff(data.get("pageSize"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithRecipent_withTiff_216(data.get("post_call_Url")+tsi,
                file, data.get("faxNumber"), data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("TSI ID "+tsi);
        System.out.println("**" + (data.get("post_call_Url")));
        System.out.println("**" + (data.get("faxNumber")));
        System.out.println("**" + file);
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();
        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();

        assertTrue(resp.contains(faxId));
        Thread.sleep(1000*100);
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFaxforTiff_216(data.get("get_call_Url"), data.get("credentialInbound"));

        String Status= JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxStatus");

        int pagesCount = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].PagesReceived");
        System.out.println("Status message: " +Status + " and pagesReceived about " +pagesCount);

    }




}


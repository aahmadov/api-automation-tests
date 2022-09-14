package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.io.File;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PostScenariosForSmokeTest extends TestBase {

    @Test(testName = "Send Fax Data with recipient Details", groups = {"smoke"})
    public void sendFaxDataWithRecipientDetails() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.readfile(data.get("Pages"));
        Response response = RestRequestUtils.sendFaxWithRecipent_details(data.get("post_call_Url"),
                file, data.get("Recipent_data1"));
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
        File file = FileReader.readfile(data.get("Pages"));
        Response response = RestRequestUtils.createFaxSingleNum(data.get("post_call_Url"), file, data.get("faxNumber"));

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
        File file = FileReader.readfile(data.get("Pages"));
        Response response = RestRequestUtils.faxWithNoNumber(data.get("post_call_Url"), file, data.get("faxNumber"));

        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get("post_call_Url"));
        System.out.println("******* " + file + " " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());

        String actual = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
        Assert.assertEquals(data.get("expectedErrorMessage"), actual);
    }

    @Test(testName = "Send Fax Data with multiple recipient Details")
    @Parameters({"postCallUrl", "fileName1", "fileName2", "recipientData1", "recipientData2", "expectedStatusCode"})
    void sendFaxDataWithMultipleRecipientDetails(String postCallUrl, String fileName1,
                                                 String fileName2,
                                                 String recipientData1, String recipientData2,
                                                 int expectedStatusCode) {
        Response response = RestRequestUtils.createFaxmultipRecip(postCallUrl, FileReader.readfile(fileName1),
                FileReader.readfile(fileName2), recipientData1, recipientData2);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**" + postCallUrl);
        System.out.println("**" + FileReader.readfile(fileName1));
        System.out.println("**" + FileReader.readfile(fileName2));
        System.out.println("**" + recipientData1);
        System.out.println("**" + recipientData2);
        System.out.println("------------------------------------------------------------------------");

        assertEquals(expectedStatusCode, response.getStatusCode());

        String resp = response.prettyPrint();
        String data = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();

        Assert.assertTrue(resp.contains(data));
    }
}

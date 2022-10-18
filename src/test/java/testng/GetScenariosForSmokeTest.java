package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class GetScenariosForSmokeTest extends TestBase {

    @Test(testName = "Retrieve recently created fax", groups = {"smoke"})
    public void retrieveRecentlyCreatedFax() throws InterruptedException {
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Thread.sleep(1000 * 60);
        Response response = RestRequestUtils.getRecentCreatedFax(
                data.get("get_call_Url") + data.get("FaxUserId"), data.get("credentials"));

        assertEquals( response.getStatusCode(),Integer.parseInt(data.get("expectedStatusCode")));

        String expectedNumber = data.get("expectedNumber");
        String faxstatus = "";
        String pagesTotalsent = null;
        int FaxId = 0;
        String resp = response.asPrettyString();
        List<String> number = JsonPath.read(resp, "$.FaxInfo[*].FaxNumber");
        System.out.println("**total fax been created is " + number.size());

        for (String str : number) {
            if (expectedNumber.equals(str)) {
                FaxId = JsonPath.read(resp, "$.FaxInfo[4].FaxId");
                faxstatus = JsonPath.read(resp, "$.FaxInfo[4].FaxStatus").toString();
                pagesTotalsent = JsonPath.read(resp, "$.FaxInfo[4].PagesTotal").toString();
            }
        }
        System.out.println("***faxId  is" + "**" + FaxId + "**");
        System.out.println("***faxStatus  is" + "**" + faxstatus + "**");
        System.out.println("***total pages sent " + "**" + pagesTotalsent + "**");
        //System.out.println("***faxNumber is" + "**" + number + "**");
        //assertEquals(expectedNumber, number);
    }

    @Test(testName = "Retrieve Fax and Fax Data by Id", groups = {"smoke"})
    public void retrieveFaxAndFaxDataById() throws InterruptedException {
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Thread.sleep(1000 * 60);
        Response response = RestRequestUtils.getFax(data.get("get_call_Url") + data.get("valid_ID"),
                data.get("credentials"));
        System.out.println("**" + data.get("get_call_Url"));
        System.out.println("**" + data.get("valid_ID"));

        assertEquals(response.getStatusCode(),Integer.parseInt(data.get("expectedStatusCode")));

        String faxStatus = response.then().extract().path("FaxInfo[0].FaxStatus");
        int faxId = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].FaxId");
        String pagesTotal = JsonPath.read(response.asPrettyString(), "$.FaxInfo[0].PagesTotal").toString();
        System.out.println("** fax id is" + "**" + faxId + "**");
        System.out.println("** totalPages sent " + "**" + pagesTotal + "**");
        System.out.println("** faxStatus is " + "**" + faxStatus + "**");
        assertEquals(faxStatus, "sent");
    }

    @Test(testName = "Retrieve all Fax Data", groups = {"smoke"})
    public void retrieveAllFaxData() throws InterruptedException {
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Thread.sleep(1000 * 60);
        Response response = RestRequestUtils.getFax(data.get("get_call_Url"), data.get("credentials"));
        System.out.println("** " + data.get("get_call_Url"));

        assertEquals(response.getStatusCode(),Integer.parseInt(data.get("expectedStatusCode")));

        List<String> userID = JsonPath.read(response.asPrettyString(), "$.FaxInfo[*].FaxUserId");
        //System.out.println("*** faxUserId after validation is " + "**" + userID + "**");
        System.out.println("*** total count of userid by name Admin " + "**" + userID.size() + "**");

        assertTrue(userID.contains(data.get("faxUserId")));
    }
}


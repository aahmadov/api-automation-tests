package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class ResendFax extends TestBase{

    @Test(testName = "Resend a fax to a different fax number", groups = {"Regression81_12"})
    public void resendFailedFaxDataWithNumber81() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        Response response1 = RestRequestUtils.sendFaxWithNewTSI81(data.get("post_call_Url") ,
                FileReader.readfile("2page"),
                data.get("faxNumber"), data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("2page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("File: " + FileReader.readfile("2page"));

        int faxId = JsonPath.read(response1.prettyPrint(), "$.FaxInfo[0].FaxId");
        int nextFaxId =faxId-1;
        System.out.println("***** this is new generated  Fax ID of outboundfax " + "**" + faxId + "**");
        Thread.sleep(1000*10);
//        Response getResponse = RestRequestUtils.getRecentCreatedFax(data.get("get_call_Url"), data.get("credentials"));
//        int FaxId = JsonPath.read(getResponse.asString(),"$.FaxInfo[0].FaxId");
//        Thread.sleep(1000*3);
        Response response = RestRequestUtils.resendfaxWith81(data.get("post_call_Url1"+faxId+"/resend"),
                FileReader.readfile("2page") ,data.get("credentials"),data.get("faxNumber"));
        System.out.println("******* " + data.get(("post_call_Url1"+"/"+faxId+"/"+"resend")));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get(("post_call_Url1"+"/"+faxId+"/"+"resend")));
        System.out.println("******* " + data.get("faxNumber"));
        System.out.println("******* " + data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");

//        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
    }

    @Test(testName = "Resend a fax to a different fax number", groups = {"Regression"})
    public void resendFailedFaxDataWithNumber() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
       // File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));

        Response response = RestRequestUtils.resendfaxWith(data.get("post_call_Url"),
                data.get("credentials"),data.get("faxNumber"));

        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get("post_call_Url"));
        System.out.println("******* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");

        //assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());

        String actual = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
        //Assert.assertEquals(data.get("expectedErrorMessage"), actual);
    }
    @Test(testName = "Resend a fax to a different fax number", groups = {"Regression1"})
    public void resendFailedFaxDataWithNumberCopy() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;
        // File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));

        Response response = RestRequestUtils.resendfaxWith(data.get("post_call_Url"),
                data.get("credentials"),data.get("faxNumber"));

        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get("post_call_Url"));
        System.out.println("******* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");

        //assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());

        String actual = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
        //Assert.assertEquals(data.get("expectedErrorMessage"), actual);
    }




}

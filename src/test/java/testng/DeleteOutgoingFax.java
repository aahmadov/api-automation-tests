package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class DeleteOutgoingFax extends TestBase {
    @Test(testName = "Delete a fax after a sent", groups = {"Regression81"})
    public void DeleteFaxAfterSend81() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        Response response1 = RestRequestUtils.DeletAfterSend1(data.get("post_call_Url") ,
                FileReader.readfile("2page"),
                data.get("faxNumber"), data.get("credentials"),data.get("coverPageEnabled"),data.get("DeleteAfterSend"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("2page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("********* " + data.get("coverPageEnabled"));
        System.out.println("********* " + data.get("DeleteAfterSend"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("File: " + FileReader.readfile("2page"));
        int faxId = JsonPath.read(response1.prettyPrint(), "$.FaxInfo[0].FaxId");
        System.out.println("***** this is new generated  Fax ID of outboundfax " + "**" + faxId + "**");
        Thread.sleep(1000*60);
        Response getResponse = RestRequestUtils.getRecentCreatedFax(data.get("get_call_Url")+"/"+faxId, data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");

        System.out.println(getResponse.asPrettyString());
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), 200);
    }
    @Test(testName = "Delete a fax after a sent", groups = {"Regression46"})
    public void DeleteFaxAfterSend46() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName46(testName);
        assert data != null;

        Response response1 = RestRequestUtils.DeletAfterSend1(data.get("post_call_Url") ,
                FileReader.readfile("2page"),
                data.get("faxNumber"), data.get("credentials"),data.get("coverPageEnabled"),data.get("DeleteAfterSend"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("2page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("********* " + data.get("coverPageEnabled"));
        System.out.println("********* " + data.get("DeleteAfterSend"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("File: " + FileReader.readfile("2page"));
        int faxId = JsonPath.read(response1.prettyPrint(), "$.FaxInfo[0].FaxId");
        System.out.println("***** this is new generated  Fax ID of outboundfax " + "**" + faxId + "**");
        Thread.sleep(1000*60);
        Response getResponse = RestRequestUtils.getRecentCreatedFax(data.get("get_call_Url")+"/"+faxId, data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");

        System.out.println(getResponse.asPrettyString());
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), 200);
    }

}

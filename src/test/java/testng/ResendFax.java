package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

import java.io.File;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class ResendFax extends TestBase{

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
}

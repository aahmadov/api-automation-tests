package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.sql.SQLException;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class ResendFax extends TestBase{

    @Test(testName = "Resend a fax to a different fax number", groups = {"Regression81_12"})
    public void resendFailedFaxDataWithNumber81() throws InterruptedException, SQLException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;
        String query = "SELECT JobID FROM acme1.sendstatus where JobStatus ='Send Failed' and Error ='Cover page: Cover page (cover.pdf) not found' order by JobID desc limit 1;";
        String jobId = DataBaseUtility.executeSQLQueryAuto184(query).toString();
        String JobIdTrimed = jobId.replace("JobID=", "").replace("[{", "").replace("}]","");
        System.out.println(JobIdTrimed);

        Response response = RestRequestUtils.resendfaxWith84(data.get("post_call_Url1")+"/"+JobIdTrimed+"/resend",
                data.get("credentials"),data.get("faxNumber"));

        System.out.println("******* " + data.get("post_call_Url1")+"/"+JobIdTrimed+"/resend");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.prettyPrint());
        System.out.println("******* " + data.get("faxNumber"));
        System.out.println("******* " + data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        Thread.sleep(1000*5);
        Response response1 = RestRequestUtils.responseRecieveFaxforResend84(data.get("get_call_Url")+"/"+JobIdTrimed ,
        data.get("credentials"));

        System.out.println(response1.asPrettyString());
        assertEquals(Integer.parseInt(data.get("Job not found:StatusCode")), response1.getStatusCode());

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

package testng;


import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CoverPageSavedIssue extends TestBase {

    @Test(testName = "Cover Page not saved when faxing fails in scheduler.", groups = {"Regression4612"})
    public void coverPageSavedIssueTest46() throws InterruptedException, IOException, SQLException {
        System.out.println("Test case name: " + testName);

        // Step 1: Send the fax and receive metadata
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName46(testName);
        assert data != null;
        String tsi = FileReader.randomNumberFor_TSI();
        File file = FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType"));
        System.out.println("File in the attachment is :" +file);
        System.out.println("CoverPageEnabled passed in: " + data.get("coverPageEnabled"));

        Response response = RestRequestUtils.CoverPagesavedISsue46(
                data.get("post_call_Url"), file,data.get("faxNumber"),data.get("coverPageEnabled"), data.get("credentials"), data.get("RetryCount"));

        assertEquals(Integer.parseInt(data.get("StatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();
        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();
        assertTrue(resp.contains(faxId));
        // Wait for the fax to be processed
        Thread.sleep(1000 * 40);

        // Step 2: Receive the fax and save it as a PDF file
        Response responseReceiveFax2 = RestRequestUtils.responseCoverPageSaveIssue46(data.get("get_call_Url") , data.get("credentials"));
        String metadata = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0]").toString();
        System.out.println(metadata);
        String metadata2 = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxStatus").toString();
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("" + data.get("get_call_Url"));
        Thread.sleep(1000 * 20);
        System.out.println("-FaxStatus of Received Fax is: " + metadata2);

        System.out.println("------------------------------------------------------------------------");
        Assert.assertEquals(data.get("expectedStatusCode"), Integer.toString(responseReceiveFax2.statusCode()));
        Thread.sleep(1000*5);
        String coverpageJobId = String.format("SELECT * FROM auto4.coverpage WHERE JobId = '%s'", faxId); ;
        DataBaseUtility.executeSQLQueryAuto46(coverpageJobId);


    }
}
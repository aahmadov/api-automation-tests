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

public class ResendFax extends TestBase{

    @Test(testName = "Resend a fax to a different fax number", groups = {"smoke5"})
    public void resendFailedFaxDataWithNumber() {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response response = RestRequestUtils.resendfaxWithinvalidIdNumber(data.get("post_call_Url"),
                file, data.get("faxNumber"), data.get("credentials"));

        System.out.println("------------------------------------------------------------------------");
        System.out.println(response.asPrettyString());
        System.out.println("******* " + data.get("post_call_Url"));
        System.out.println("******* " + file + " " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());

        String actual = JsonPath.read(response.asPrettyString(), "$.RequestStatus.StatusText");
        //Assert.assertEquals(data.get("expectedErrorMessage"), actual);
    }
}

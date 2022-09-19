package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.RestRequestUtils;

import static org.testng.Assert.assertEquals;

public class PostCallDataManipulationFromJenkins {

    @Test(testName = "Send Fax Data with multiple recipient Details", groups = {"smokeInput"})
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

        assertEquals(response.getStatusCode(), expectedStatusCode);

        String resp = response.prettyPrint();
        String data = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();

        Assert.assertTrue(resp.contains(data));
    }


}




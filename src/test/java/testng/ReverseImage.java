package testng;

import com.jayway.jsonpath.JsonPath;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import io.restassured.response.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReverseImage extends TestBase {
    @Test(testName = "Resend a fax to a different fax number", groups = {"Regression81"})
    public void sendFaxReverseImage() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;
        String tsi = FileReader.randomNumberFor_TSI();
        File file = FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithRecipent_withTiff_81(data.get("post_call_Url") + tsi,
                file, data.get("faxNumber"), data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("TSI ID " + tsi);
        System.out.println("**" + (data.get("post_call_Url")));
        System.out.println("**" + (data.get("faxNumber")));
        System.out.println("**" + file);
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();
        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();
        assertTrue(resp.contains(faxId));
        Thread.sleep(1000 * 60);
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFaxforTiff_81(data.get("get_call_Url"), data.get("credentialInbound"));

        String Status = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].FaxStatus");

        int FaxId = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].FaxId");

        int pagesCount = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].PagesReceived");
        System.out.println("Status message: " + Status + " and pagesReceived about " + pagesCount+"  FaxId "+FaxId);
        String remoteFilePath = "C:\\Users\\Administrator\\Downloads\\_recv-fax-" + FaxId + ".pdf";
        try {
            // Step 1: Receive the file
            InputStream is = responseReceiveFax2.asInputStream();
            File receivedFile = new File(remoteFilePath);

            FileOutputStream fos = new FileOutputStream(receivedFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            is.close();

            // Step 2: Check if the received file size is more than 1KB locally
            if (receivedFile.length() > 1024) {
                System.out.println("The received file is likely a reverse image.");
            } else {
                System.out.println("The received file is not a reverse image.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test(testName = "Resend a fax to a different fax number", groups = {"Regression46"})
    public void sendFaxReverseImage_46() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName46(testName);
        assert data != null;
        String tsi = FileReader.randomNumberFor_TSI();
        File file = FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithRecipent_withTiff_81(data.get("post_call_Url") + tsi,
                file, data.get("faxNumber"), data.get("credentials"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("TSI ID " + tsi);
        System.out.println("**" + (data.get("post_call_Url")));
        System.out.println("**" + (data.get("faxNumber")));
        System.out.println("**" + file);
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();
        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();
        assertTrue(resp.contains(faxId));
        Thread.sleep(1000 * 60);
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFaxforTiff_81(data.get("get_call_Url"), data.get("credentialInbound"));

        String Status = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].FaxStatus");

        int FaxId = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].FaxId");

        int pagesCount = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].PagesReceived");
        System.out.println("Status message: " + Status + " and pagesReceived about " + pagesCount+"  FaxId "+FaxId);
        String remoteFilePath = "C:\\Users\\Administrator\\Downloads\\_recv-fax-" + FaxId + ".pdf";
        try {
            // Step 1: Receive the file
            InputStream is = responseReceiveFax2.asInputStream();
            File receivedFile = new File(remoteFilePath);

            FileOutputStream fos = new FileOutputStream(receivedFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            fos.close();
            is.close();

            // Step 2: Check if the received file size is more than 1KB locally
            if (receivedFile.length() > 1024) {
                System.out.println("The received file is likely a reverse image.");
            } else {
                System.out.println("The received file is not a reverse image.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

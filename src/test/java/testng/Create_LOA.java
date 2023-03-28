package testng;

import com.testautomationguru.utility.PDFUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Create_LOA extends TestBase {

    @Test(testName = "Creates a PDF letter of Authorization ", groups = {"Regression"})
    void CreateLOA() throws Exception  {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA(data.get("post_call_Url"),data.get("body"));
        byte[] fileContents = responseSubmitFaxLong.getBody().asByteArray();
        File outputFile = new File("C:\\Users\\Administrator\\Downloads\\test.pdf");
        try (OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {
            outputStream.write(fileContents);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*5);
        String PdfLocation ="C:\\Users\\Administrator\\Downloads\\test.pdf";
        PDFUtil pdfUtil = new PDFUtil();
        String content = pdfUtil.getText(PdfLocation).toString();
        System.out.println("The entire page content: "+content);
        int count = pdfUtil.getPageCount(PdfLocation);
        System.out.println("Page count is: "+count);
    }

    @Test(testName="add_new_number_request/tollFree",groups = {"Regression"})
    void addNewNumberTollFree() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA(data.get("post_call_Url"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);
        System.out.println(responseSubmitFaxLong.asPrettyString());
    }
    @Test(testName="Creates a request to port-in a fax number",groups = {"Regression"})
    void addPortRequest() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithSwagger(data.get("post_call_Url"),
                file, data.get("data"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(":" + (data.get("post_call_Url")));
        System.out.println(":" + file);
        System.out.println("------------------------------------------------------------------------");

        Assert.assertEquals(Integer.parseInt(data.get("statusCode")), response.getStatusCode());
        Thread.sleep(1000*5);
        System.out.println(response.asPrettyString());
}
    @Test(testName="remove_Number_request_release",groups = {"Regression"})
    void removeNumberRequest() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA(data.get("post_call_Url"),data.get("body"));
        System.out.println(responseSubmitFaxLong.asPrettyString());
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*5);
        System.out.println(responseSubmitFaxLong.asPrettyString());


}
    @Test(testName="creates new requests to remove fax number",groups = {"Regression"})
    void removeNumberRequestPortOut() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA(data.get("post_call_Url"),data.get("body"));
        System.out.println(responseSubmitFaxLong.asPrettyString());
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*5);
        System.out.println(responseSubmitFaxLong.asPrettyString());
}
    @Test(testName = "Creates a PDF letter of Authorization with new URL ", groups = {"Regression"})
    void CreateLOA2() throws Exception  {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2(data.get("post_call_Url"),data.get("body"));
        byte[] fileContents = responseSubmitFaxLong.getBody().asByteArray();
        File outputFile = new File("C:\\Users\\Administrator\\Downloads\\test.pdf");
        try (OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {
            outputStream.write(fileContents);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*5);
        String PdfLocation ="C:\\Users\\Administrator\\Downloads\\test.pdf";
        PDFUtil pdfUtil = new PDFUtil();
        String content = pdfUtil.getText(PdfLocation).toString();
        System.out.println("The entire page content: "+content);
        int count = pdfUtil.getPageCount(PdfLocation);
        System.out.println("Page count is: "+count);
    }
    @Test(testName="add_new_number_request/tollFree with new URL",groups = {"Regression"})
    void addNewNumberTollFree2() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2(data.get("post_call_Url1"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url1"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.getStatusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);
        System.out.println(responseSubmitFaxLong.asPrettyString());
    }
    @Test(testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression"})
    void addNewNumberNoneTollFree2() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2(data.get("post_call_Url2"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url2"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);
        System.out.println(responseSubmitFaxLong.asPrettyString());
    }
    @Test(testName="Creates a request to port-in a fax number with new URL",groups = {"Regression"})
    void addPortRequest2() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Response response = RestRequestUtils.sendFaxWithSwagger2(data.get("post_call_Url"),
                file, data.get("data"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(":" + (data.get("post_call_Url")));
        System.out.println(":" + file);
        System.out.println("------------------------------------------------------------------------");

        Assert.assertEquals(Integer.parseInt(data.get("statusCode")), response.getStatusCode());
        Thread.sleep(1000*5);
        System.out.println(response.asPrettyString());
    }

}
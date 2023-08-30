package testng;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.jsonpath.JsonPath;
import com.testautomationguru.utility.PDFUtil;
import io.restassured.RestAssured;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.*;
import utils.FileReader;

import java.io.*;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
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
    void addPortRequest() throws InterruptedException, JsonProcessingException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String number2  = FileReader.randomFaxNumberEmailToFax();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data.get("data"));
        ((ObjectNode) jsonNode).put("number_to_port", number2);
        String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));

        String credentials = ConfigReader.getProperty("Token");

        Response responseSubmitFaxLong2 = RestAssured.given()
                .header("Authorization ", "Bearer " + credentials)
                .contentType("multipart/form-data")
                .multiPart("loaFile", file)
                .multiPart("billFile", file)
                .queryParam("data", modifiedJsonData)
                .when()
                .post(data.get("post_call_Url"));


       // Response response = RestRequestUtils.sendFaxWithSwagger(data.get("post_call_Url"),
        //        file, modifiedJsonData);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(":" + (data.get("post_call_Url")));
        System.out.println(":" + file);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(responseSubmitFaxLong2.asPrettyString());
        System.out.println(modifiedJsonData);
        Assert.assertEquals(responseSubmitFaxLong2.getStatusCode(),Integer.parseInt(data.get("statusCode")));
        Thread.sleep(1000*5);
        System.out.println(responseSubmitFaxLong2.asPrettyString());
}
    @Test(testName="remove_Number_request_release",groups = {"Regressionteze"})
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
        System.out.println(responseSubmitFaxLong.asPrettyString());
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*5);
        System.out.println(responseSubmitFaxLong.asPrettyString());


}
    @Test(testName="creates new requests to remove fax number",groups = {"RegressionForLocalrun"})
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
        System.out.println(responseSubmitFaxLong.asPrettyString());
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
    @Test(testName = "Creates a PDF letter of Authorization with new URL ", groups = {"Regression1"})
    void CreateLOA2_COPY() throws Exception  {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2_147(data.get("post_call_Url"),data.get("body"));
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
        String content = pdfUtil.getText(PdfLocation);
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
    @Test(testName="add_new_number_request/tollFree with new URL",groups = {"Regression1"})
    void addNewNumberTollFree2_Copy() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2_147(data.get("post_call_Url1"),data.get("body"));
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
    @Test(testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression1"})
    void addNewNumberNoneTollFree2_Copy() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2_147(data.get("post_call_Url2"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url2"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);
        System.out.println(responseSubmitFaxLong.asPrettyString());
    }
    @Test(testName="Creates a request to port-in a fax number with new URL",groups = {"Regression"})
    void addPortRequest2() throws InterruptedException, SQLException, JsonProcessingException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String number2  = FileReader.randomFaxNumberEmailToFax();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data.get("data"));
        ((ObjectNode) jsonNode).put("number_to_port", number2);
        String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));

        String credentials = ConfigReader.getProperty("Token2");

        Response responseSubmitFaxLong2 = RestAssured.given()
                .header("Authorization ", "Bearer " + credentials)
                .contentType("multipart/form-data")
                .multiPart("loaFile", file)
                .multiPart("billFile", file)
                .queryParam("data", modifiedJsonData)
                .when().log().all()
                .post(data.get("post_call_Url"));

//        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
//        Response response = RestRequestUtils.sendFaxWithSwagger2(data.get("post_call_Url"),
//                file, data.get("data"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(":" + (data.get("post_call_Url")));
        System.out.println(":" + file);
        System.out.println("------------------------------------------------------------------------");
//        List <Integer> JobID = JsonPath.read(responseSubmitFaxLong2.asPrettyString(),"$..id");

        System.out.println(responseSubmitFaxLong2.asPrettyString());
        Assert.assertEquals(Integer.parseInt(data.get("statusCode")), responseSubmitFaxLong2.getStatusCode());
//        Thread.sleep(1000*5);
//        System.out.println(responseSubmitFaxLong2.asPrettyString());
//        String database=String.format("update replixdb.faxnumber_requests set status = 'Complete' where (id='%s')",JobID.toString().replace("[","").replace("]",""));
//        DataBaseUtility.executeSQLUpdate2(database);
//        System.out.println(database);
    }
    @Test(testName="remove_Number_request_release with new URL",groups = {"RegressionFailed"})
    void removeNumberRequest2() throws InterruptedException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2(data.get("post_call_Url"),data.get("body"));
        System.out.println(responseSubmitFaxLong.asPrettyString());
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Response:  "+responseSubmitFaxLong.asPrettyString());
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*5);

        System.out.println(responseSubmitFaxLong.asPrettyString());

    }
    @Test(priority = 1,testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression"})
    void addNewNumberNonTollFreeWithUpdatedApp_Complete() throws InterruptedException, SQLException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOAApp_complete_scenario(data.get("post_call_Url"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);

        System.out.println(responseSubmitFaxLong.asPrettyString());
        List <Integer> JobID = JsonPath.read(responseSubmitFaxLong.asPrettyString(),"$..id");

        //Execute first query
        String database=String.format("update replixdb.faxnumber_requests set assigned_faxnumber='' , status = 'Complete' where (id='%s')",JobID.toString().replace("[","").replace("]",""));
        DataBaseUtility.executeSQLUpdate2(database);
        System.out.println(database);

        String URL=String.format("http://10.250.1.100:8082/api/numbers/requests/add/%s",JobID.toString().replace("[","").replace("]",""));
        String credential = ConfigReader.getProperty("Token2");

        Response responseGetCall = RestAssured.given()
         .header("Authorization " ,"Bearer "+ credential)
         .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(priority = 1,testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression1"})
    void addNewNumberNonTollFreeWithUpdatedApp_Complete_Copy() throws InterruptedException, SQLException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOAApp_complete_scenario_147(data.get("post_call_Url"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);

        System.out.println(responseSubmitFaxLong.asPrettyString());
        List <Integer> JobID = JsonPath.read(responseSubmitFaxLong.asPrettyString(),"$..id");

        //Execute first query
        String database=String.format("update replixdb.faxnumber_requests set assigned_faxnumber='' , status = 'Complete' where (id='%s')",JobID.toString().replace("[","").replace("]",""));
        DataBaseUtility.executeSQLUpdate(database);
        System.out.println(database);

        String URL=String.format("http://10.250.1.147:8081/api/numbers/requests/add/%s",JobID.toString().replace("[","").replace("]",""));
        String credential = ConfigReader.getProperty("Token_for_147");

        Response responseGetCall = RestAssured.given()
                .header("Authorization " ,"Bearer "+ credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression"})
    void addNewNumberNonTollFreeWithUpdatedApp_inProgress() throws InterruptedException, SQLException, JsonProcessingException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String number2  = FileReader.randomFaxNumberEmailToFax();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree("{\n\"country_code\": \"US\",\n \"department_name\": \"\",\n  \"forward_from\":\"12345678901\",\n \"assign_to_user\":\"\",\n \"notify_email\": [\n \"auto@softlinx.com\"\n ],\n \"comment\": \"Process ASAP.\",\n \"city\":\"Boston\",\n \"region\":\"MA\",\n \"area_code\":[\"781\"\n ]\n}");
        ((ObjectNode) jsonNode).put("forward_from", number2);
        String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);

        String credentials = ConfigReader.getProperty("Token2");

         Response responseSubmitFaxLong = RestAssured.given()
                 .header("Authorization ", "Bearer " + credentials)
                 .contentType("application/json")
        .body(modifiedJsonData)
        .when()
        .post(data.get("post_call_Url"));
        //Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA(data.get("post_call_Url"),modifiedJsonData);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        //System.out.println(data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(modifiedJsonData);
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 10);

        System.out.println(responseSubmitFaxLong.asPrettyString());
        List<Integer> JobID = JsonPath.read(responseSubmitFaxLong.asPrettyString(), "$..id");

        //Execute first query
        String database = String.format("update replixdb.faxnumber_requests set assigned_faxnumber='%s' , status = 'In-process' where (id='%s')",number2, JobID.toString().replace("[", "").replace("]", ""));
        DataBaseUtility.executeSQLUpdate2(database);
        System.out.println(database);

        String URL = String.format("http://10.250.1.100:8082/api/numbers/requests/add/%s", JobID.toString().replace("[", "").replace("]", ""));
        String credential = ConfigReader.getProperty("Token2");

        Response responseGetCall = RestAssured.given()
                .header("Authorization ", "Bearer " + credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression1"})
    void addNewNumberNonTollFreeWithUpdatedApp_inProgress_Copy() throws InterruptedException, SQLException, JsonProcessingException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String number2  = FileReader.randomFaxNumberEmailToFax();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree("{\n\"country_code\": \"US\",\n \"department_name\": \"\",\n  \"forward_from\":\"12345678901\",\n \"assign_to_user\":\"\",\n \"notify_email\": [\n \"auto@softlinx.com\"\n ],\n \"comment\": \"Process ASAP.\",\n \"city\":\"Boston\",\n \"region\":\"MA\",\n \"area_code\":[\"781\"\n ]\n}");
        ((ObjectNode) jsonNode).put("forward_from", number2);
        String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);

        String credentials = ConfigReader.getProperty("Token_for_147");

        Response responseSubmitFaxLong = RestAssured.given()
                .header("Authorization ", "Bearer " + credentials)
                .contentType("application/json")
                .body(modifiedJsonData)
                .when()
                .post(data.get("post_call_Url"));
        //Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA(data.get("post_call_Url"),modifiedJsonData);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        //System.out.println(data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println(modifiedJsonData);
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 10);

        System.out.println(responseSubmitFaxLong.asPrettyString());
        List<Integer> JobID = JsonPath.read(responseSubmitFaxLong.asPrettyString(), "$..id");

        //Execute first query
        String database = String.format("update replixdb.faxnumber_requests set assigned_faxnumber='%s' , status = 'In-process' where (id='%s')",number2, JobID.toString().replace("[", "").replace("]", ""));
        DataBaseUtility.executeSQLUpdate(database);
        System.out.println(database);

        String URL = String.format("http://10.250.1.147:8081/api/numbers/requests/add/%s", JobID.toString().replace("[", "").replace("]", ""));
        String credential = ConfigReader.getProperty("Token_for_147");

        Response responseGetCall = RestAssured.given()
                .header("Authorization ", "Bearer " + credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());
    }


    @Test(priority = 1,testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression"})
    void addNewNumberNonTollFreeWithUpdatedApp_onHold() throws InterruptedException, SQLException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOAApp_complete_scenario(data.get("post_call_Url"), data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println(data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 10);

        System.out.println(responseSubmitFaxLong.asPrettyString());
        List<Integer> JobID = JsonPath.read(responseSubmitFaxLong.asPrettyString(), "$..id");

        //Execute first query

        String databaseSelectquery = String.format("select status,on_hold,on_hold_reason from replixdb.faxnumber_requests where (id='%s')", JobID.toString().replace("[", "").replace("]", ""));
        System.out.println(databaseSelectquery);
        DataBaseUtility.executeSQLQueryRecvD(databaseSelectquery);


        System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><>");
        String database = String.format("update replixdb.faxnumber_requests set on_hold=1, on_hold_reason = 'testing' where (id='%s')", JobID.toString().replace("[", "").replace("]", ""));
        DataBaseUtility.executeSQLUpdate2(database);
        System.out.println(database);
        String URL = String.format("http://10.250.1.100:8082/api/numbers/requests/add/%s", JobID.toString().replace("[", "").replace("]", ""));
        String credential = ConfigReader.getProperty("Token2");

        Response responseGetCall = RestAssured.given()
                .header("Authorization ", "Bearer " + credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(priority = 1,testName="add_new_number_request/None_tollFree with new URL",groups = {"Regression1"})
    void addNewNumberNonTollFreeWithUpdatedApp_onHold_Copy() throws InterruptedException, SQLException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOAApp_complete_scenario_147(data.get("post_call_Url"), data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println(data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 10);

        System.out.println(responseSubmitFaxLong.asPrettyString());
        List<Integer> JobID = JsonPath.read(responseSubmitFaxLong.asPrettyString(), "$..id");

        //Execute first query

        String databaseSelectquery = String.format("select status,on_hold,on_hold_reason from replixdb.faxnumber_requests where (id='%s')", JobID.toString().replace("[", "").replace("]", ""));
        System.out.println(databaseSelectquery);
        DataBaseUtility.executeSQLQuery(databaseSelectquery);


        System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><>");
        String database = String.format("update replixdb.faxnumber_requests set on_hold=1, on_hold_reason = 'testing' where (id='%s')", JobID.toString().replace("[", "").replace("]", ""));
        DataBaseUtility.executeSQLUpdate(database);
        System.out.println(database);
        String URL = String.format("http://10.250.1.147:8081/api/numbers/requests/add/%s", JobID.toString().replace("[", "").replace("]", ""));
        String credential = ConfigReader.getProperty("Token_for_147");

        Response responseGetCall = RestAssured.given()
                .header("Authorization ", "Bearer " + credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());
    }



    @Test(testName="add_new_number_request/tollFree with new URL",groups = {"Regression"})
    void addNewNumberTollFreeReleasedStatus() throws InterruptedException, JsonProcessingException, SQLException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String number2  = FileReader.randomFaxNumberEmailToFax();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data.get("body"));
        ((ObjectNode) jsonNode).put("fax_number", number2);
        String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);

        String credentials = ConfigReader.getProperty("Token2");

        Response responseSubmitFaxLong2 = RestAssured.given()
                .header("Authorization ", "Bearer " + credentials)
                .contentType("application/json")
                .body(modifiedJsonData)
                .when()
                .post(data.get("post_call_Url1"));
        List<Integer> JobID = JsonPath.read(responseSubmitFaxLong2.asPrettyString(), "$..id");

        //Execute first query
        String database = String.format("update  replixdb.faxnumber_requests set released_on=NOW(), cancel_reason='testing', assigned_faxnumber=null, released_faxnumber='%s' where (id='%s')",number2, JobID.toString().replace("[", "").replace("]", ""));
        DataBaseUtility.executeSQLUpdate2(database);
        System.out.println(database);

       // Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2(data.get("post_call_Url1"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url1"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong2.getStatusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);
        System.out.println(responseSubmitFaxLong2.asPrettyString());

        String URL = String.format("http://10.250.1.100:8082/api/numbers/requests/add/%s", JobID.toString().replace("[", "").replace("]", ""));
        String credential = ConfigReader.getProperty("Token2");

        Response responseGetCall = RestAssured.given()
                .header("Authorization ", "Bearer " + credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());


    }
    @Test(testName="add_new_number_request/tollFree with new URL",groups = {"Regression1"})
    void addNewNumberTollFreeReleasedStatus_Copy() throws InterruptedException, JsonProcessingException, SQLException {

        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String number2  = FileReader.randomFaxNumberEmailToFax();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(data.get("body"));
        ((ObjectNode) jsonNode).put("fax_number", number2);
        String modifiedJsonData = objectMapper.writeValueAsString(jsonNode);

        String credentials = ConfigReader.getProperty("Token_for_147");

        Response responseSubmitFaxLong2 = RestAssured.given()
                .header("Authorization ", "Bearer " + credentials)
                .contentType("application/json")
                .body(modifiedJsonData)
                .when()
                .post(data.get("post_call_Url1"));
        List<Integer> JobID = JsonPath.read(responseSubmitFaxLong2.asPrettyString(), "$..id");

        //Execute first query
        String database = String.format("update  replixdb.faxnumber_requests set released_on=NOW(), cancel_reason='testing', assigned_faxnumber=null, released_faxnumber='%s' where (id='%s')",number2, JobID.toString().replace("[", "").replace("]", ""));
        DataBaseUtility.executeSQLUpdate(database);
        System.out.println(database);

        // Response responseSubmitFaxLong = RestRequestUtils.PostCalltoCreateLOA2(data.get("post_call_Url1"),data.get("body"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url1"));
        System.out.println( data.get("body"));
        System.out.println("------------------------------------------------------------------------");

        assertEquals(Integer.toString(responseSubmitFaxLong2.getStatusCode()), data.get("statusCode"));
        Thread.sleep(1000*10);
        System.out.println(responseSubmitFaxLong2.asPrettyString());

        String URL = String.format("http://10.250.1.147:8081/api/numbers/requests/add/%s", JobID.toString().replace("[", "").replace("]", ""));
        String credential = ConfigReader.getProperty("Token_for_147");

        Response responseGetCall = RestAssured.given()
                .header("Authorization ", "Bearer " + credential)
                .contentType("application/json")
                .when()
                .get(URL);

        System.out.println(responseGetCall.asPrettyString());


    }

    }
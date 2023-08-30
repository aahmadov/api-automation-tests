package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.json.XML;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.ResourceUtils;
import utils.SoapRequestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.testng.Assert.assertEquals;

public class SoapInboundFaxPagesCoverPage extends TestBase {

    @Test(testName = "SOAP - Dynamic scenario for fax status and page number validation from inbound",
            groups = {"smoke"})
    public void soapFaxStatusAndPageNumberValidationFromInbound() throws InterruptedException, IOException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        String tsi = FileReader.randomTsi();
        String[] outboundCred = data.get("credentialNewOutboundLogin").split("@");
        //add more values to the data to replace the values from the xml file
        data.put("login", outboundCred[0]);
        data.put("password", data.get("credentialNewOutboundPassword"));
        data.put("realm", outboundCred[1]);
        data.put("faxUserId", outboundCred[0]);
        data.put("tsi", tsi);
        data.put("fileName", FileReader.getFileName(file.getAbsolutePath()));
        data.put("attachment", FileReader.fileToByteString(file.getAbsolutePath()));
        data.put("contentType", FileReader.getContentTypeForFile(file.getAbsolutePath()));

        //replace the values in the xml files from the testData (data)
        String sendFaxBody = replaceValues(data, String.format("soapRequestBody/%s_sendFax.xml", testName));

        Response sendFaxWithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
                sendFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("sendFaxSoapAction")));
        int code = sendFaxWithCoverPage.getStatusCode();
        System.out.println("***** the expected status code " + "***" + data.get("inboundStatusCode") + "***"
                + " send Fax statusCode lineUp with actual " + "***" + code + "***");
        assertEquals(code, Integer.parseInt(data.get("inboundStatusCode")));
        String jsonData = XML.toJSONObject(sendFaxWithCoverPage.asPrettyString()).toString();

        String faxId = ((JSONArray) JsonPath.read(jsonData, "$..FaxInfo.FaxId")).get(0).toString();
        data.put("faxId", faxId);
        System.out.println("******** faxId of post call  " + "**" + faxId + "**");
        System.out.println("******** " + (data.get("post_call_Url")));
        System.out.println("******** " + data.get("faxNumber"));
        System.out.println("******** " + file);

        Response outboundWithCoverPage;
        boolean isNotCompleted = true;
        int times = 0;
        String querySendFaxBody = replaceValues(data, String.format("soapRequestBody/%s_querySendFax.xml", testName));
        do {
            Thread.sleep(1000 * 30);
            outboundWithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
                    querySendFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("querySendFaxSoapAction")));
            assertEquals(200, outboundWithCoverPage.getStatusCode());
            String outboundResponseData = XML.toJSONObject(outboundWithCoverPage.asPrettyString()).toString();
            JSONArray tsiArray = JsonPath.read(outboundResponseData, "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                System.out.println("****** the post call TSI id " + "**" + tsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
            }
            times++;
        } while (isNotCompleted && times < 16);

        //replacing the login credentials for inbound fax
        String[] inboundCred = data.get("credentialNewInboundLogin").split("@");
        data.put("login", inboundCred[0]);
        data.put("password", data.get("credentialNewInboundPassword"));
        data.put("realm", inboundCred[1]);
        data.put("faxUserId", inboundCred[0]);
        String queryReceiveFaxBody = replaceValues(data, String.format("soapRequestBody/%s_queryReceiveFax.xml", testName));

        Response inboundFaxwithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
                queryReceiveFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("queryReceiveFaxSoapAction")));
        assertEquals(200, inboundFaxwithCoverPage.getStatusCode());

        System.out.println(":checking for this TSI  in entire response " + "**" + tsi);
        //Get all metadata of the TSI from the response
        String inboundFaxData = XML.toJSONObject(inboundFaxwithCoverPage.asPrettyString()).toString();
        JSONArray tsiArray = JsonPath.read(inboundFaxData, "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");

        System.out.println("*** RESPONSE DATA FOR TSI ***");
        //System.out.println(tsiArray.toString());

        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(tsi + "****" + " doesn't have neither recvOk status or 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from excel
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());
        System.out.println(":fax status with this tsi ID " + tsi + " is " + statuses);
        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }
    }

    private String replaceValues(Map<String, String> data, String filePath) throws IOException {
        String content = IOUtils.toString(Files.newInputStream(Paths.get(ResourceUtils.getResourceFilePathAbsPath(filePath))),
                StandardCharsets.UTF_8);
        return content.replace("{faxNumber}", data.get("faxNumber"))
                .replace("{tsi}", data.get("tsi"))
                .replace("{login}", data.get("login"))
                .replace("{password}", data.get("password"))
                .replace("{realm}", data.get("realm"))
                .replace("{fileName}", data.get("fileName"))
                .replace("{attachment}", data.get("attachment"))
                .replace("{faxUserId}", data.get("faxUserId"))
                .replace("{faxId}", data.getOrDefault("faxId", ""))
                .replace("{coverPageEnabled}", data.get("coverPageEnabled"))
                .replace("{contentType}", data.get("contentType"));
    }
//    @Test(testName = "SOAP - Dynamic scenario for fax status and page number validation from inbound",
//            groups = {"smoke12"})
//    public void soapFaxStatusAndPageNumberValidationFromInboundCopy() throws InterruptedException, IOException {
//        System.out.println("Test case name: " + testName);
//        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
//        assert data != null;
//
//        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
//        String tsi = FileReader.randomTsi();
//        String[] outboundCred = data.get("credentialNewOutboundLogin").split("@");
//        //add more values to the data to replace the values from the xml file
//        data.put("login", outboundCred[0]);
//        data.put("password", data.get("credentialNewOutboundPassword"));
//        data.put("realm", outboundCred[1]);
//        data.put("faxUserId", outboundCred[0]);
//        data.put("tsi", tsi);
//        data.put("fileName", FileReader.getFileName(file.getAbsolutePath()));
//        data.put("attachment", FileReader.fileToByteString(file.getAbsolutePath()));
//        data.put("contentType", FileReader.getContentTypeForFile(file.getAbsolutePath()));
//
//        //replace the values in the xml files from the testData (data)
//        String sendFaxBody = replaceValuesCopy(data, String.format("soapRequestBody/%s_sendFax.xml", testName));
//
//        Response sendFaxWithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
//                sendFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("sendFaxSoapAction")));
//        int code = sendFaxWithCoverPage.getStatusCode();
//        System.out.println("***** the expected status code " + "***" + data.get("inboundStatusCode") + "***"
//                + " send Fax statusCode lineUp with actual " + "***" + code + "***");
//        assertEquals(code, Integer.parseInt(data.get("inboundStatusCode")));
//        String jsonData = XML.toJSONObject(sendFaxWithCoverPage.asPrettyString()).toString();
//
//        String faxId = ((JSONArray) JsonPath.read(jsonData, "$..FaxInfo.FaxId")).get(0).toString();
//        data.put("faxId", faxId);
//        System.out.println("******** faxId of post call  " + "**" + faxId + "**");
//        System.out.println("******** " + (data.get("post_call_Url")));
//        System.out.println("******** " + data.get("faxNumber"));
//        System.out.println("******** " + file);
//
//        Response outboundWithCoverPage;
//        boolean isNotCompleted = true;
//        int times = 0;
//        String querySendFaxBody = replaceValuesCopy(data, String.format("soapRequestBody/%s_querySendFax.xml", testName));
//        do {
//            Thread.sleep(1000 * 30);
//            outboundWithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
//                    querySendFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("querySendFaxSoapAction")));
//            assertEquals(200, outboundWithCoverPage.getStatusCode());
//            String outboundResponseData = XML.toJSONObject(outboundWithCoverPage.asPrettyString()).toString();
//            JSONArray tsiArray = JsonPath.read(outboundResponseData, "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");
//            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
//                isNotCompleted = false;
//                System.out.println("****** the post call TSI id " + "**" + tsi + "**" + " and "
//                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
//            }
//            times++;
//        } while (isNotCompleted && times < 16);
//
//        //replacing the login credentials for inbound fax
//        String[] inboundCred = data.get("credentialNewInboundLogin").split("@");
//        data.put("login", inboundCred[0]);
//        data.put("password", data.get("credentialNewInboundPassword"));
//        data.put("realm", inboundCred[1]);
//        data.put("faxUserId", inboundCred[0]);
//        String queryReceiveFaxBody = replaceValuesCopy(data, String.format("soapRequestBody/%s_queryReceiveFax.xml", testName));
//
//        Response inboundFaxwithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
//                queryReceiveFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("queryReceiveFaxSoapAction")));
//        assertEquals(200, inboundFaxwithCoverPage.getStatusCode());
//
//        System.out.println(":checking for this TSI  in entire response " + "**" + tsi);
//        //Get all metadata of the TSI from the response
//        String inboundFaxData = XML.toJSONObject(inboundFaxwithCoverPage.asPrettyString()).toString();
//        JSONArray tsiArray = JsonPath.read(inboundFaxData, "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");
//
//        System.out.println("*** RESPONSE DATA FOR TSI ***");
//        //System.out.println(tsiArray.toString());
//
//        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
//        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
//            fail(tsi + "****" + " doesn't have neither recvOk status or 3 attempts" + "**");
//        }
//
//        //Get the Fax status values of all the TSi from excel
//        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());
//        System.out.println(":fax status with this tsi ID " + tsi + " is " + statuses);
//        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
//        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));
//
//        //checking if the last status is recvOk then previous status should be recvIncomplete
//        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
//            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
//        }
//    }
//
//    private String replaceValuesCopy(Map<String, String> data, String filePath) throws IOException {
//        String content = IOUtils.toString(Files.newInputStream(Paths.get(ResourceUtils.getResourceFilePathAbsPath(filePath))),
//                StandardCharsets.UTF_8);
//        return content.replace("{faxNumber}", data.get("faxNumber"))
//                .replace("{tsi}", data.get("tsi"))
//                .replace("{login}", data.get("login"))
//                .replace("{password}", data.get("password"))
//                .replace("{realm}", data.get("realm"))
//                .replace("{fileName}", data.get("fileName"))
//                .replace("{attachment}", data.get("attachment"))
//                .replace("{faxUserId}", data.get("faxUserId"))
//                .replace("{faxId}", data.getOrDefault("faxId", ""))
//                .replace("{coverPageEnabled}", data.get("coverPageEnabled"))
//                .replace("{contentType}", data.get("contentType"));
//    }

//    @Test(testName = "SOAP ", groups = {"SmokeNewIMG"})
//    public void soapRequestForIMG() throws InterruptedException, IOException {
//        System.out.println("Test case name: " + testName);
//        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
//        assert data != null;
//
//        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
//        String tsi = FileReader.randomTsi();
//        String[] outboundCred = data.get("credentialNewOutboundLogin").split("@");
//        //add more values to the data to replace the values from the xml file
//        data.put("login", outboundCred[0]);
//
//        data.put("password", data.get("credentialNewOutboundPassword"));
//        data.put("realm", outboundCred[1]);
//
//        data.put("tsi", tsi);
//        data.put("fileName", FileReader.getFileName(file.getAbsolutePath()));
//        data.put("attachment", FileReader.fileToByteString(file.getAbsolutePath()));
//        data.put("contentType", FileReader.getContentTypeForFile(file.getAbsolutePath()));
//
//        //replace the values in the xml files from the testData (data)
//        String sendFaxBody = replaceValues2(data, String.format("soapRequestBody/soapFaxStatusAndPageNumberValidationFromInbound_SendFax2.xml", testName));
//
//        Response sendFaxWithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPageIMG(data.get("post_call_Url"),
//                sendFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("sendFaxSoapAction")));
//
//        int code = sendFaxWithCoverPage.getStatusCode();
//        System.out.println("***** the expected status code " + "***" + data.get("inboundStatusCode") + "***"
//                + " send Fax statusCode lineUp with actual " + "***" + code + "***");
//        assertEquals(code, Integer.parseInt(data.get("inboundStatusCode")));
//        String jsonData = XML.toJSONObject(sendFaxWithCoverPage.asPrettyString()).toString();
//
//        String faxId = ((JSONArray) JsonPath.read(jsonData, "$..FaxInfo.FaxId")).get(0).toString();
//        //data.put("faxId", faxId);
//        System.out.println("******** faxId of post call  " + "**" + faxId + "**");
//        System.out.println("******** " + (data.get("post_call_Url")));
//        System.out.println("******** " + data.get("faxNumber"));
//        System.out.println("******** " + file);
//
//    }
//
//    private String replaceValues2(Map<String, String> data, String filePath) throws IOException {
//        String content = IOUtils.toString(Files.newInputStream(Paths.get(ResourceUtils.getResourceFilePathAbsPath(filePath))),
//                StandardCharsets.UTF_8);
//        return content.replace("{faxNumber}", data.get("faxNumber"))
//                .replace("{tsi}", data.get("tsi"))
//                .replace("{login}", data.get("login"))
//                .replace("{password}", data.get("password"))
//                .replace("{realm}", data.get("realm"))
//                .replace("{fileName}", data.get("fileName"))
//                .replace("{reservedWC}", data.get("reservedWC"))
//                //.replace("{attachment}", data.get("attachment"))
//                .replace("{passwordSecurity}", data.get("passwordSecurity"))
//                .replace("{attachmentContent}", data.get("attachmentContent"))
//                .replace("{faxHeader}", data.get("faxHeader"))
//                .replace("{reserved1}", data.get("reserved1"))
//                .replace("{requestOrigin}", data.get("requestOrigin"));
//               //.replace("{coverPageEnabled}", data.get("coverPageEnabled"))
//               // .replace("{contentType}", data.get("contentType"));
//    }
}

//    @Test(testName = "SOAP ", groups = {"SmokeNewIMGNew"})
//    public void sendSoapRequest_forMultipleIMG() throws IOException {
//
//        System.out.println("Test case name: " + testName);
//        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
//        assert data != null;
//        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
//        String tsi = FileReader.randomTsi();
//
//        // Read JSON data from file
//        String jsonData = new String(Files.readAllBytes(Paths.get("path_to_json_file.json")));
//        JSONObject jsonObject = new JSONObject(jsonData);
//
//        // Extract data from JSON
//        String login = jsonObject.getString("login");
//        String password = jsonObject.getString("password");
//        String realm = jsonObject.getString("realm");
//        // ... extract other data
//
//        // Construct SOAP request
//        String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rep=\"http://www.softlinx.com/ReplixFax\">\n"
//                // ... add other XML elements
//                + "<Login>" + login + "</Login>"
//                + "<Password>" + password + "</Password>"
//                + "<Realm>" + realm + "</Realm>"
//                // ... add other XML elements
//                + "</soapenv:Envelope>";
//
//        // Send SOAP request using RestAssured
//        Response response = RestAssured.given()
//                .header("Content-Type", "text/xml; charset=utf-8")
//                .body(soapRequest)
//                .post("your_soap_endpoint_url");
//
//        // Print response
//        System.out.println("Response: " + response.getBody().asString());
//
//    }
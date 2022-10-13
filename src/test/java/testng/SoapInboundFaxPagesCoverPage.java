package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
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
        //add more values to the data to replace the values from the xml file
        data.put("login", data.get("credentialNewOutboundLogin"));
        data.put("password", data.get("credentialNewOutboundPassword"));
        data.put("faxUserId", data.get("credentialNewOutboundLogin"));
        data.put("tsi", tsi);
        data.put("fileName", FileReader.getFileName(file.getAbsolutePath()));
        data.put("attachment", FileReader.fileToByteString(file.getAbsolutePath()));

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
        data.put("login", data.get("credentialNewInboundLogin"));
        data.put("password", data.get("credentialNewInboundPassword"));
        data.put("faxUserId", data.get("credentialNewInboundLogin"));
        String queryReceiveFaxBody = replaceValues(data, String.format("soapRequestBody/%s_queryReceiveFax.xml", testName));

        Response inboundFaxwithCoverPage = SoapRequestUtils.soapInboundFaxWithCoverPage(data.get("post_call_Url"),
                queryReceiveFaxBody, String.format("%s:%s", data.get("login"), data.get("password")), data.get(("queryReceiveFaxSoapAction")));
        assertEquals(200, inboundFaxwithCoverPage.getStatusCode());

        System.out.println("checking for this TSI  in entire response " + "**" + tsi);
        //Get all metadata of the TSI from the response
        String inboundFaxData = XML.toJSONObject(inboundFaxwithCoverPage.asPrettyString()).toString();
        JSONArray tsiArray = JsonPath.read(inboundFaxData, "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");
        System.out.println("*** RESPONSE DATA FOR TSI ***");
        System.out.println(tsiArray.toString());

        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(tsi + "****" + " doesn't have neither recvOk status or 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from excel
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());
        System.out.println(statuses);
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
                .replace("{fileName}", data.get("fileName"))
                .replace("{attachment}", data.get("attachment"))
                .replace("{faxUserId}", data.get("faxUserId"))
                .replace("{faxId}", data.getOrDefault("faxId", ""))
                .replace("{coverPageEnabled}", "true");
    }

}



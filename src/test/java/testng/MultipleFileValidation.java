package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class MultipleFileValidation extends TestBase {

    @Test(testName = "validation of multiple Fax files is successfully got received  ", groups = {"Regression81"})
    public void ScanMultipleFiles_FaxPageValidation81() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;
        // Extract the filesToTest list from the JSON data and cast it properly
        List<Map<String, String>> filesToTest = Arrays.asList(
                Map.of("Pages", "2", "fileType", "htm"),
                Map.of("Pages", "2", "fileType", "jpg"),
                Map.of("Pages", "2", "fileType", "pdf"),
                Map.of("Pages", "2", "fileType", "tiff"),
                Map.of("Pages", "2", "fileType", "doc"),
                Map.of("Pages", "2", "fileType", "docx"),
                Map.of("Pages", "2", "fileType", "html"),
                Map.of("Pages", "2", "fileType", "txt"),
                Map.of("Pages", "2", "fileType", "xls"),
                Map.of("Pages", "2", "fileType", "bmp"),
                Map.of("Pages", "2", "fileType", "gif"),
                Map.of("Pages", "2", "fileType", "jpeg"),
                Map.of("Pages", "2", "fileType", "xlsx"));

        for (Map<String, String> fileData : filesToTest) {
            try {
                File file = FileReader.getFileUsingPageSize(fileData.get("Pages"), fileData.get("fileType"));
                String tsi = FileReader.randomNumberFor_TSI();
                String onlyTsi = tsi.split("=")[1];
                Response response = RestRequestUtils.sendFaxWithNewTSI81(data.get("post_call_Url") + tsi,
                        file,
                        data.get("faxNumber"), data.get("credentialOutbound"));
                System.out.println("------------------------------------------------------------------------");
                System.out.println("************ " + data.get("post_call_Url"));
                System.out.println("********** " + file);
                System.out.println("********* " + data.get("faxNumber"));
                System.out.println("------------------------------------------------------------------------");
                System.out.println("File: " + file.getName() + " sent with TSI: " + onlyTsi);

                String faxId = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
                System.out.println("***** this is new generated  Fax number of outboundfax " + "**" + faxId + "**");

                Response outbound;
                boolean isNotCompleted = true;
                boolean isFailed = false;
                int times = 0;
                do {
                    System.out.println("*** waiting 30 secs to get the fax sending status ***");
                    Thread.sleep(1000 * 30);
                    outbound = Second_RestRequestUtils.getOutboundWithCoverPage81(
                            data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
                    Assert.assertEquals(200, outbound.getStatusCode());
                    JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
                    System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
                    if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                        isNotCompleted = false;
                        if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                            isFailed = true;
                        }
                        System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                                + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                        String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                        System.out.println("Error message: " + "**" + errorMessage + "**");
                    }
                    times++;
                } while (isNotCompleted && times < 20);

                if (isFailed) {
                    System.out.println("Send failed for TSI id:" + onlyTsi);
                }

                System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));

                Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1_81(
                        data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
                Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

                System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
                System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
                JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
                System.out.println("Response related TSI is: " + tsiArray.toJSONString());

                if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
                    System.out.println(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
                }

                List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());
                assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

                if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
                    assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
                }
            } catch (Exception e) {
                System.err.println("Error processing file with Pages: " + fileData.get("Pages") + ", fileType: " + fileData.get("fileType"));
                e.printStackTrace();
            }
        }


}}

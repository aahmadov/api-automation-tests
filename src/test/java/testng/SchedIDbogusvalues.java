package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class SchedIDbogusvalues extends TestBase {


    @Test(testName = "Validation SchedID test", groups = {"Regression81"})
    public void SchedID_gets_bogus_values81() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        String deleteSettings1 = "DELETE FROM replixdb.settings WHERE sname = 'sched.schedulerids';";
        DataBaseUtility.executeSQLUpdateRecvD84(deleteSettings1);
        Thread.sleep(1000*3);


        String insertSettings1 = "INSERT INTO replixdb.settings (sname, svalue)VALUES ('sched.schedulerids', '0,1') ON DUPLICATE KEY UPDATE svalue = '0,1';";
        DataBaseUtility.executeSQLUpdateRecvD84(insertSettings1);
        List<String> tsiList = new ArrayList<>(); // To store all TSI values

        // Loop for sending faxes
        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("** Fax iteration time in the loop: " + i);

            String tsi = FileReader.randomNumberFor_TSI();
            String onlyTsi = tsi.split("=")[1];
            tsiList.add(onlyTsi); // Store the TSI

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSizepdfforemail2fax(data.get("pageSize")));
            requestData.put("FaxNumber", data.get("faxNumber"));
            requestData.put("url", data.get("post_call_Url") + tsi);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response response = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentialOutbound"));

            System.out.println(response.asString());
            System.out.println("******* Status code: " + response.statusCode());
            assertEquals(201, response.statusCode());
        }
        // Validate the status of each fax
        for (String onlyTsi : tsiList) {
            boolean isNotCompleted = true;
            boolean isFailed = false;
            int times = 0;

            do {
                System.out.println("*** Waiting 30 secs to get the fax sending status ***");
                Thread.sleep(1000 * 30);
                Response outbound = Second_RestRequestUtils.getOutboundWithCoverPage81(data.get("get_call_Url"), data.get("credentialOutbound"));
                org.testng.Assert.assertEquals(200, outbound.getStatusCode());

                JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
                System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());

                if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                    isNotCompleted = false;
                    if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                        isFailed = true;
                    }
                    System.out.println("****** The post call TSI id " + "**" + onlyTsi + "**" +
                            " and total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                    String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");
                    System.out.println("Error message: " + "**" + errorMessage + "**");
                }
                times++;
            } while (isNotCompleted && times < 15);

            if (isFailed) {
                fail("Send failed for TSI id: " + onlyTsi);
            }
        }
        Thread.sleep(1000*3);
        String deleteSettings = "DELETE FROM replixdb.settings WHERE sname = 'sched.schedulerids';";
        DataBaseUtility.executeSQLUpdateRecvD84(deleteSettings);
        Thread.sleep(1000*3);

        String insertSettings = "INSERT INTO replixdb.settings (sname, svalue) VALUES ('sched.schedulerids', '0');";
        DataBaseUtility.executeSQLUpdateRecvD84(insertSettings);

    }



//    @Test(testName = "Validation SchedID test", groups = {"Regression8113"})
//    public void SchedID_gets_bogus_values81() throws Exception {
//        System.out.println("Test case name: " + testName);
//        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
//        assert data != null;
//
//
//        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
//            System.out.println("**" + "Fax iteration time in the loop :" + i);
//
//            String tsi = FileReader.randomNumberFor_TSI();
//            String onlyTsi = tsi.split("=")[1];
//
//
//            Map<String, Object> requestData = new ConcurrentHashMap<>();
//            requestData.put("filename", FileReader.getFileUsingPageSizepdfforemail2fax(data.get("pageSize")));
//            requestData.put("FaxNumber", data.get("faxNumber"));
//            requestData.put("url", data.get("post_call_Url") + tsi);
//            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));
//
//            Response response = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentialOutbound"));
//
//            System.out.println(response.asString());
//
//
//            System.out.println("******* Status code:" + response.statusCode());
//            assertEquals(201, response.statusCode());
//
//            Response outbound;
//            boolean isNotCompleted = true;
//            boolean isFailed = false;
//            int times = 0;
//            do {
//                System.out.println("*** waiting 30 secs to get the fax sending status ***");
//                Thread.sleep(1000 * 30);
//                outbound = Second_RestRequestUtils.getOutboundWithCoverPage81(data.get("get_call_Url"), data.get("credentialOutbound"));
//                org.testng.Assert.assertEquals(200, outbound.getStatusCode());
//                JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
//                System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
//                if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
//                    isNotCompleted = false;
//                    if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
//                        isFailed = true;
//                    }
//                    System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
//                            + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
//                    String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");
//
//                    System.out.println("Error message: " + "**" + errorMessage + "**");
//                }
//                times++;
//            } while (isNotCompleted && times < 15);
//
//            if (isFailed) {
//                fail("Send failed for TSI id:" + onlyTsi);
//            }
//
//        }
//    }






}

package testng;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class PostScenarioForLoadTestReceiveD extends TestBase {

    @Test(testName = "Validation of Post call for Load test", groups = {"Regression81"})
    public void validationOfPostCallForLoadTestReceiveD_81() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        DataBaseUtility.executeSQLUpdateRecvD84(ConfigReader.getProperty("truncate_recvstatusAcme1_81"));
        DataBaseUtility.executeSQLUpdateRecvD84(ConfigReader.getProperty("truncate_billing_table_81"));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
            String faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSizepdfforemail2fax(data.get("pageSize")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("post_call_Url") + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response response = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentialOutbound"));

            System.out.println(response.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                Assert.assertEquals(201, response.statusCode());
            }

            if (response.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }

            System.out.println("******* Status code:" + response.statusCode());
            assertEquals(201, response.statusCode());

            Thread.sleep(1000*160);
            Response responseReceiveFax = RestRequestUtils.responseRecieveFaxcollsionRecv_81(data.get("get_call_Url"), data.get("credentialInbound"));
            String metadata = responseReceiveFax.prettyPrint();
            System.out.println(metadata);

            // Parse the metadata string into a JSON object
            JSONObject jsonObject = new JSONObject(metadata);

            // Extract the FaxInfo array
            JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

            // Search for the FaxInfo object with the desired TSI value
            int jobId = -1;
            for (int j = 0; j < faxInfoArray.length(); j++) {
                JSONObject faxInfo = faxInfoArray.getJSONObject(j);

                if (faxInfo.getString("TSI").equals(firstLoadTest_TSI.toString().replace("?TSI=",""))) {
                    jobId = faxInfo.getInt("FaxId");
                }
            }
            //System.out.println("************ Inbound Fax Job id: " + jobId);
            DataBaseUtility.executeSQLUpdateRecvD84(ConfigReader.getProperty("truncate_recvstatusAcme1_81"));
        }
        Thread.sleep(1000*120);
        DataBaseUtility.executeSQLQueryAuto184(ConfigReader.getProperty("checking_receiveD_81"));
    }

    @Test(testName = "Validation of Post call for Load test", groups = {"Regression"})
    public void validationOfPostCallForLoadTestReceiveD() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        DataBaseUtility.executeSQLUpdateRecvD(ConfigReader.getProperty("truncate_recvstatusAcme1"));
        DataBaseUtility.executeSQLUpdateRecvD(ConfigReader.getProperty("truncate_billing_table"));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
            String faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSizepdfforemail2fax(data.get("pageSize")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("post_call_Url") + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response response = Load_RestRequestUtils.sendFax_loadTest(requestData, data.get("credentialOutbound"));

            System.out.println(response.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                Assert.assertEquals(201, response.statusCode());
            }

            if (response.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }

            System.out.println("******* Status code:" + response.statusCode());
            assertEquals(201, response.statusCode());

            Thread.sleep(1000*160);
            Response responseReceiveFax = RestRequestUtils.responseRecieveFaxcollsionRecvD(data.get("get_call_Url"), data.get("credentialInbound"));
            String metadata = responseReceiveFax.prettyPrint();
            System.out.println(metadata);

            // Parse the metadata string into a JSON object
            JSONObject jsonObject = new JSONObject(metadata);

            // Extract the FaxInfo array
            JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

            // Search for the FaxInfo object with the desired TSI value
            int jobId = -1;
            for (int j = 0; j < faxInfoArray.length(); j++) {
                JSONObject faxInfo = faxInfoArray.getJSONObject(j);

                if (faxInfo.getString("TSI").equals(firstLoadTest_TSI.toString().replace("?TSI=",""))) {
                    jobId = faxInfo.getInt("FaxId");
                }
            }
            //System.out.println("************ Inbound Fax Job id: " + jobId);
            DataBaseUtility.executeSQLUpdateRecvD(ConfigReader.getProperty("truncate_recvstatusAcme1"));
        }
        Thread.sleep(1000*120);
        DataBaseUtility.executeSQLQueryRecvD(ConfigReader.getProperty("checking_receiveD"));
    }
    @Test(testName = "Validation of Post call for Load test", groups = {"Regression1"})
    public void validationOfPostCallForLoadTestReceiveDCopy() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

//        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("truncate_recvstatus1"));
//        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("truncate_billing_table"));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
            String faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSizepdfforemail2fax(data.get("pageSize")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("post_call_Url") + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response response = Load_RestRequestUtils.sendFax_loadTest(requestData, data.get("credentialOutbound"));

            System.out.println(response.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                Assert.assertEquals(201, response.statusCode());
            }

            if (response.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }

            System.out.println("******* Status code:" + response.statusCode());
            assertEquals(201, response.statusCode());

            Thread.sleep(1000*120);
            Response responseReceiveFax = RestRequestUtils.responseRecieveFaxcollsionRecvD_147(data.get("get_call_Url"), data.get("credentialInbound"));
            String metadata = responseReceiveFax.prettyPrint();
            System.out.println(metadata);

            // Parse the metadata string into a JSON object
            JSONObject jsonObject = new JSONObject(metadata);

            // Extract the FaxInfo array
            JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

            // Search for the FaxInfo object with the desired TSI value
            int jobId = -1;
            for (int j = 0; j < faxInfoArray.length(); j++) {
                JSONObject faxInfo = faxInfoArray.getJSONObject(j);

                if (faxInfo.getString("TSI").equals(firstLoadTest_TSI.toString().replace("?TSI=",""))) {
                    jobId = faxInfo.getInt("FaxId");
                }
            }
            //System.out.println("************ Inbound Fax Job id: " + jobId);

//            DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("truncate_recvstatusSmoke"));
        }
        Thread.sleep(1000*180);
        DataBaseUtility.executeSQLQuery(ConfigReader.getProperty("checking_receiveD"));
    }

}

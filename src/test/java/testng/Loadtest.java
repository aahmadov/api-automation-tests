package testng;

import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class Loadtest extends TestBase {

    @Test(testName = "Validation of Post call for Load test", groups = {"Regression81"})
    public void PostCallForLoadTest81() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

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
        }
    }
    @Test(testName = "Validation of Post call for Load test", groups = {"Regression46"})
    public void PostCallForLoadTest46() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName46(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

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
        }
    }

    @Test(testName = "Validation of Post call for Load test", groups = {"Regression"})
    public void PostCallForLoadTest() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

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
        }
    }
    @Test(testName = "Validation of Post call for Load test", groups = {"Regression1"})
    public void PostCallForLoadTestCopy() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        String ExcelPath = ResourceUtils.getResourceFilePathAbsPath(data.get("testDataFile"));
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

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
        }
    }





}
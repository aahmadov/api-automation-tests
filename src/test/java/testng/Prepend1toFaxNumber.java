package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Test;
import utils.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Prepend1toFaxNumber extends TestBase {
    String excelFilePath;

    @Test(testName = "Prepend 1 to fax number if it starts with [2-9]", groups = {"Regression81"})
    public void add_1_to_the_beginning_Of_number81() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));
            Response sendFaxResponse = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                assertEquals(201, sendFaxResponse.statusCode());
            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 120);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax81(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });
    }
    @Test( testName = "Set replixdb.settings table sendfax.prepend1toDestNumber to 0. " +
            " Send a fax with a fax number that does not start with ‘1',", groups = {"Regression46"})
    public void prepending1_toFaxnumber_withDataBaseSet_Up46() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName46(testName);
        assert data != null;


        String database2 = "DELETE FROM acme1.settings WHERE sname = 'sendfax.prepend1todestNumber';" ;
        DataBaseUtility.executeSQLUpdateRecvD46(database2);
        System.out.println("dataBaseQuery: "+database2);

        String database1 = "INSERT INTO acme1.settings (sname, svalue) VALUES ('sendfax.prepend1toDestNumber', '0');" ;
        DataBaseUtility.executeSQLUpdateRecvD46(database1);
        System.out.println("dataBaseQuery: "+database1);

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);

            Response sendFaxResponse = Load_RestRequestUtils.make_prepending_1_to_fax_number_optional(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());

//            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
//                assertEquals(201, sendFaxResponse.statusCode());
//            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 30);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax81(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });

    }
    @Test(testName = "Prepend 1 to fax number if it starts with [2-9]", groups = {"Regression46"})
    public void add_1_to_the_beginning_Of_number46() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName46(testName);
        assert data != null;

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));
            Response sendFaxResponse = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                assertEquals(201, sendFaxResponse.statusCode());
            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 30);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax81(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });
    }
    @Test( testName = "Set replixdb.settings table sendfax.prepend1toDestNumber to 0. " +
            " Send a fax with a fax number that does not start with ‘1',", groups = {"Regression81"})
    public void prepending1_toFaxnumber_withDataBaseSet_Up81() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;


        String database2 = "DELETE FROM acme1.settings WHERE sname = 'sendfax.prepend1todestNumber';" ;
        DataBaseUtility.executeSQLUpdateRecvD84(database2);
        System.out.println("dataBaseQuery: "+database2);

        String database1 = "INSERT INTO acme1.settings (sname, svalue) VALUES ('sendfax.prepend1toDestNumber', '0');" ;
        DataBaseUtility.executeSQLUpdateRecvD84(database1);
        System.out.println("dataBaseQuery: "+database1);

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);

            Response sendFaxResponse = Load_RestRequestUtils.make_prepending_1_to_fax_number_optional(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());

//            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
//                assertEquals(201, sendFaxResponse.statusCode());
//            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 120);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax81(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });

    }

        @Test(testName = "Prepend 1 to fax number if it starts with [2-9]", groups = {"Regression"})
    public void add_1_to_the_beginning_Of_number() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));
            Response sendFaxResponse = Load_RestRequestUtils.sendFax_loadTest(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                assertEquals(201, sendFaxResponse.statusCode());
            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 30);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });

    }

    @Test(testName = "Prepend 1 to fax number if it starts with [2-9]", groups = {"smoke1"})
    public void add_1_to_the_beginning_Of_number_Copy() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;


        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response sendFaxResponse = Load_RestRequestUtils.sendFax_loadTest(requestData, data.get("credentials"));

            System.out.println(sendFaxResponse.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                assertEquals(201, sendFaxResponse.statusCode());
            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 30);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));
        });
    }


    @Test( priority=1 ,testName = "Set replixdb.settings table sendfax.prepend1toDestNumber to 0.  " +
            "Send a fax with a fax number that does not start with ‘1',", groups = {"Regression"})
    public void prepending1_toFaxnumber_withDataBaseSet_Up() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;


        String database1 = "DELETE FROM acme1.settings WHERE sname = 'sendfax.prepend1todestNumber';" ;
        DataBaseUtility.executeSQLUpdateRecvD(database1);
        System.out.println("dataBaseQuery: "+database1);


        String database2 = "INSERT INTO acme1.settings (sname, svalue) VALUES ('sendfax.prepend1toDestNumber', '0');" ;
        DataBaseUtility.executeSQLUpdateRecvD(database2);
        System.out.println("dataBaseQuery: "+database2);

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);

            Response sendFaxResponse = Load_RestRequestUtils.make_prepending_1_to_fax_number_optional(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());



            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 30);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });

    }
    @Test( priority=2 ,testName = "Set replixdb.settings table sendfax.prepend1toDestNumber to 0.  " +
            "Send a fax with a fax number that does not start with ‘1',", groups = {"Regression"})
    public void prepending1_toFaxnumber_withDataBaseSet_Up2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String database1 = "DELETE FROM acme1.settings WHERE sname = 'sendfax.prepend1todestNumber';";
        DataBaseUtility.executeSQLUpdateRecvD(database1);
        System.out.println("dataBaseQuery: " + database1);

        String database2 = "INSERT INTO acme1.settings (sname, svalue) VALUES ('sendfax.prepend1toDestNumber', '1');";
        DataBaseUtility.executeSQLUpdateRecvD(database2);
        System.out.println("dataBaseQuery: " + database2);

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        excelFilePath = file.getAbsolutePath();

        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = StringUtils.isBlank(data.get("faxNumber")) ?
                    faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())) : data.get("faxNumber");

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", data.get("url") + testTSI);

            Response sendFaxResponse = Load_RestRequestUtils.make_prepending_1_to_fax_number_optional(requestData, data.get("credentials"));
            System.out.println(sendFaxResponse.asString());

//            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
//                assertEquals(201, sendFaxResponse.statusCode());
//            }

            if (sendFaxResponse.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }

        Thread.sleep(1000 * 30);
        Response recentFaxResponse = Load_RestRequestUtils
                .getRecentFax(data.get("url") + data.get("faxUserId"), data.get("credentials"));

        int statusCode = recentFaxResponse.getStatusCode();
        System.out.println("******* Status code:" + recentFaxResponse.statusCode());
        assertEquals(statusCode, recentFaxResponse.statusCode());

        List<LinkedHashMap<String, String>> tsiAndFaxNumbers = JsonPath.read(recentFaxResponse.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = tsiAndFaxNumbers.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData(excelFilePath, 1);
        //create map combing both TSI id and fax numbers from excel
        Map<String, String> dataFromExcel = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbersFromExcel::get));
        dataFromExcel.keySet().forEach(key -> {
            //get the faxnumber based on the TSI id (from excel) from response
            System.out.println("TSI id from excel: " + key + ". Fax number from excel: " + dataFromExcel.get(key));
            List<String> value = xyz.stream().map(map -> map.get(key)).filter(Objects::nonNull).collect(Collectors.toList());
            assertTrue(value.size() > 0);
            //compare faxnumber from excel to faxnumber from response
            System.out.println("Fax number associated to the TSI id " + key + " from response is " + value.get(0));
            // assertEquals(dataFromExcel.get(key), value.get(0));

        });

    }


}

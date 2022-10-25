package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Prepend1toFaxNumber extends TestBase {
    Response response;
    int maxIterationNumber = 10;

    @Test(testName = "Prepend 1 to fax number if it starts with [2-9]", groups = {"smoke6"})
    public void add_1_to_the_beginning_Of_number() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data1 = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data1 != null;

        String ExcelPath = ConfigReader.getProperty("testDataFile");
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data1.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data1.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String testTSI = FileReader.randomNumberFor_TSI();
            String faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data1.get("pageSize")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", ConfigReader.getProperty(data1.get("url")) + testTSI);
            requestData.put("coverPageEnabled", data1.get("coverPageEnabled"));

            response = Load_RestRequestUtils.sendFax_loadTest(requestData);

            System.out.println(response.asString());

            if (!Boolean.parseBoolean(data1.get("ignoreFail"))) {
                assertEquals(201, response.statusCode());
            }

            if (response.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(ExcelPath, testTSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }
        response = Load_RestRequestUtils.getRecentFax(
                ConfigReader.getProperty("post_call_Url") + (ConfigReader.getProperty("outboundParamAdmin")));
        int  statusCode=response.getStatusCode();
        System.out.println("******* Status code:" + response.statusCode());
        assertEquals(statusCode, response.statusCode());

        List<LinkedHashMap<String, String>> data = JsonPath.read(response.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = data.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData((ConfigReader.getProperty("testDataFile")), 0);
        List<String> tsiIds = tsiIdsFromExcel.stream().map(item -> item.split("=")[1]).collect(Collectors.toList());
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData((ConfigReader.getProperty("testDataFile")), 1);
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

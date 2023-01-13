package stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Post_calls_forLoadTest_steps {

    Response response;
    int maxIterationNumber = 10;

//@Given("I want submit group of post calls with this URL {string} with {string} id {string} and {string}")
//public void i_want_submit_group_of_post_calls_with_this_URL_with_id_and(String URL, String firstLoadTest_TSI, String FileReader, String string4) {

////        String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();

//        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

//        for(int i=0;i<maxIterationNumber;i++) {
//        	System.out.println("it is in loop");
//        response = Load_RestRequestUtils.sendFax_loadTest(ConfigReader.getProperty(URL) + FileReader.randomNumberFor_TSI(), FileReader.randomFileFromFolder()
//                , FileReader.randomFaxNumber());
//        i++;
//       ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI); 
//       
//    }


    @Given("I want to submit group of post calls with (.*) for (.*)")
    public void i_want_submit_group_of_post_calls_with_URL_for_times(String URL, int noOfTimes) throws URISyntaxException {

//        String ExcelPath = ConfigReader.getProperty("testDataFile");
        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        String excelFilePath = file.getAbsolutePath();

        for (int i = 0; i < noOfTimes; i++) {
            System.out.println(":It is iteration time in the loop :" + i);
            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
            response = Load_RestRequestUtils.sendFax_loadTest(ConfigReader.getProperty(URL) + firstLoadTest_TSI,
                    FileReader.randomFileFromFolder(), FileReader.randomFaxNumber());

            ExcelUtility.createExcelAndWrite(excelFilePath, firstLoadTest_TSI);
            System.out.println(firstLoadTest_TSI);
        }

        System.out.println(excelFilePath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");

    }

    @Given("^I want to submit group of post calls with data$")
    public void i_want_to_submit_group_of_post_calls_with_data(DataTable dataTable) throws Exception {
        Map<String, String> data = dataTable.transpose().asMaps().get(0);

//        String ExcelPath = ConfigReader.getProperty("testDataFile");
        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        String excelFilePath = file.getAbsolutePath();
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
            String faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize")));
            requestData.put("FaxNumber", faxNumber);
            requestData.put("url", ConfigReader.getProperty(data.get("url")) + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            response = Load_RestRequestUtils.sendFax_loadTest(requestData);

            System.out.println(response.asString());

            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                assertEquals(201, response.statusCode());
            }

            if (response.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(excelFilePath, firstLoadTest_TSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(excelFilePath, faxNumber);
                //System.out.println("**"+"after successful post call, generated TSI is "+firstLoadTest_TSI);
            }
        }
    }

    @Then("^I validate the status (.*) as expected$")
    public void statusCodeAsExpected(int statusCode) {
        System.out.println("******* Status code:" + response.statusCode());
        assertEquals(statusCode, response.statusCode());
    }

    @Given("I validate of status code is {int}")
    public void i_validate_of_status_code_is(int expectedStatusCode) {
        int acutalStatusCode = response.statusCode();
        response.asString();
        int FaxId = JsonPath.read(response.asString(), "$.FaxInfo[0].FaxId");
        assertEquals(acutalStatusCode, expectedStatusCode);
        System.out.println("**** the new generated FaxId for PostCall is " + "**" + FaxId + "**");
        System.out.println("**** the actual status code is " + "**" + acutalStatusCode + "**");
    }

    @Given("user sends request to retrieve all FaxNumbers")
    public void user_sends_request_to_retrieve_all_FaxNumbers() {
        //Thread.sleep(1000*30);
        response = Load_RestRequestUtils.getRecentFax(
                ConfigReader.getProperty("post_call_Url") + (ConfigReader.getProperty("outboundParamAdmin")));

//          System.out.println(ConfigReader.getProperty("getFaxByID_url"));
//          System.out.println(ConfigReader.getProperty(" outboundParamAdmin"));
    }


    @And("verify fax numbers are as expected for TSI id")
    public void verify_fax_numbers_are_as_expected_for_TSI_id() throws URISyntaxException {
        List<LinkedHashMap<String, String>> data = JsonPath.read(response.asString(), "$.FaxInfo[*]['FaxNumber', 'TSI']");

        List<Map<String, String>> xyz = data.stream().map(map -> {
            List<String> values = new ArrayList<>(map.values());
            return Map.of(values.get(1), values.get(0));
        }).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        String excelFilePath = file.getAbsolutePath();

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


    
    

    
    

    

    

    
   

    

    

    


   
    





   
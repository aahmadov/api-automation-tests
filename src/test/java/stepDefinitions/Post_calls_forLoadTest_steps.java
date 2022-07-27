package stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utils.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

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
    public void i_want_submit_group_of_post_calls_with_URL_for_times(String URL, int noOfTimes) {

        String ExcelPath = ConfigReader.getProperty("testDataFile");
        
        for (int i = 0; i < noOfTimes; i++) {
            System.out.println("it is iteration time in the loop :" + i);
            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
            response = Load_RestRequestUtils.sendFax_loadTest(ConfigReader.getProperty(URL) + firstLoadTest_TSI,
                    FileReader.randomFileFromFolder(), FileReader.randomFaxNumber());

            ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI);
            System.out.println(firstLoadTest_TSI);
        }

        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");

    }

    @Given("^I want to submit group of post calls with data$")	                                                                                 
    public void i_want_to_submit_group_of_post_calls_with_data(DataTable dataTable) throws Exception {
        Map<String, String> data = dataTable.transpose().asMaps().get(0);

        String ExcelPath = ConfigReader.getProperty("testDataFile");
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));
        //String firstLoadTest_TSI2 = "TSI=test20";
        for (int i = 0; i < Integer.parseInt(data.get("times")); i++) {
            System.out.println("it is iteration time in the loop :" + i);
            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();                                                                                                                                                                                                                                                   
                                                                                                                                                                                                    
            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", FileReader.getFileUsingPageSize(data.get("pageSize")));
            requestData.put("FaxNumber", faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size())));
            requestData.put("url", ConfigReader.getProperty(data.get("url")) + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            response = Load_RestRequestUtils.sendFax_loadTest(requestData);
            
            System.out.println(response.asString());
                                                                                                                                                                                                                                        
            if (!Boolean.parseBoolean(data.get("ignoreFail"))) {
                assertEquals(201, response.statusCode());
            }

            if (response.statusCode() == 201) {
                ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI);
                System.out.println("**"+"after successfully post call, generated TSI is "+firstLoadTest_TSI);
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

}
    
    

    
    

    

    

    
   

    

    

    


   
    





   
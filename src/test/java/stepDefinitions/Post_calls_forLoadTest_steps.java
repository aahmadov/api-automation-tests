package stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import utils.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
     
        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**"+"it is iteration time in the loop :" + i);
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
                ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI, faxNumber);
                //ExcelUtility.createExcelAndWrite(ExcelPath, faxNumber);
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
    public void user_sends_request_to_retrieve_all_FaxNumbers() throws InterruptedException {
    	//Thread.sleep(1000*30);
    	  response = Load_RestRequestUtils.getRecentFax(
                  ConfigReader.getProperty("outbound_URl_65") + (ConfigReader.getProperty("OutboundParam_65")));

//          System.out.println(ConfigReader.getProperty("getFaxByID_url"));
//          System.out.println(ConfigReader.getProperty(" outboundParamAdmin"));
    	  List<String> faxNumbers = JsonPath.read(response.asString(), "$.FaxInfo[*].FaxNumber");
        List<String> tsiIds = JsonPath.read(response.asString(), "$.FaxInfo[*].TSI");
        Map<String, String> dataFromResponse = IntStream.range(0, tsiIds.size()).boxed().collect(Collectors.toMap(tsiIds::get, faxNumbers::get));

          //faxNumbers.stream().forEach(faxNumber -> assertEquals());
        		 

        	 
          List<String> tsiIdsFromExcel = ExcelUtility.getColumnData((ConfigReader.getProperty("testDataFile")), 0);
        List<String> faxNumbersFromExcel = ExcelUtility.getColumnData((ConfigReader.getProperty("testDataFile")), 1);

          List<String> newTsiIdsFromExcel = tsiIdsFromExcel.stream()
                  .map(tsi -> tsi.split("=")[1])
                  .collect(Collectors.toList());
          System.out.println("Message: TSI's from excel of the latest Fax's "+newTsiIdsFromExcel);
         
          List<String> missingTsiValues = new ArrayList<>();
          for (String tsi : newTsiIdsFromExcel) {
          	System.out.println("Message: checking for this TSI  in entire response "+ "**"+tsi);
          	
          	//Get all metadata of the TSI from the excel
              JSONArray tsiArray = JsonPath.read(response.asString(), "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");
             System.out.println(tsiArray);
              //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
              if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
                  missingTsiValues.add(tsi);
              }

              //Get the Fax status values of all the TSi from excel
              List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());
              System.out.println("Message: status of the fax under mentioned TSI "+ statuses);
              //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
              assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));
              System.out.println();
              //checking if the last status is recvOk then previous status should be recvIncomplete
              if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
                  assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
                  
                  System.out.println("message: there is not neither recvOk and recvIncomplete");
              }
          }
          
        //Check TSI metadata contains either recvOk status or max of 3 attempts
          if (missingTsiValues.size() > 0) {
              fail(missingTsiValues + "****"+" doesn't have neither recvOk status or 3 attempts"+"**");
          }else {
          	
          	System.out.println("***** "+"each recently generated TSI id's  has valid status and correct RetryCount");
          }
      }
    }


    
    

    
    

    

    

    
   

    

    

    


   
    





   
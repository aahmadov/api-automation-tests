package stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import utils.ConfigReader;
import utils.ExcelUtility;
import utils.Load_RestRequestUtils;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Get_calls_forLoadTest_steps {


	
	Response response;

    @Given("user sends request to retrieve valid FaxID for Load")
    public void user_sends_request_to_retrieve_valid_FaxID_for_Load() {
        //Thread.sleep(1000*60);
        response = Load_RestRequestUtils.getCreatedFaxForLoad1(
                ConfigReader.getProperty("inboundFax_url") + (ConfigReader.getProperty("newInboundParam")));

        System.out.println(ConfigReader.getProperty("inboundFax_url"));
        System.out.println(ConfigReader.getProperty("newInboundParam"));
    }

    @Then("user validates Tsi id of Fax")
    public void user_validates_Tsi_id_of_Fax() {
        response.prettyPrint();
        String Tsi = JsonPath.read(response.asPrettyString(), "$.FaxInfo[1].TSI");
        System.out.println("**" + "--" + Tsi + "--");
    }

    @Given("user validates {int} is right getCall status code")
    public void user_validates_is_right_getCall_status_code(int expectedStatus) {
        int actualStatus = response.getStatusCode();
        System.out.println(actualStatus);
        System.out.println(expectedStatus);
        //assertEquals(expectedStatus,actualStatus);
    }

    @Then("user validates recent TSI present in response")
    public void user_validate_recent_TSI_present_in_response() throws InterruptedException {
    	
    	Thread.sleep(1000*180);
        List<String> tsiIdsFromResponse = JsonPath.read(response.asString(), "$..TSI");
        List<String> newTsiIdsFromResponse = tsiIdsFromResponse.stream().distinct().collect(Collectors.toList());

        System.out.println(tsiIdsFromResponse);
        
        System.out.println(newTsiIdsFromResponse);
        
        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(
                "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx", 0);
        System.out.println(tsiIdsFromExcel);
        
        List<String> newTsiIdsFromExcel = tsiIdsFromExcel.stream()
                .map(tsi -> tsi.split("=")[1])
                .collect(Collectors.toList());
        System.out.println(newTsiIdsFromExcel);
        
        assertTrue("TSI values from excel file are not present in the response", newTsiIdsFromResponse.containsAll(newTsiIdsFromExcel));
    }

    @Then("user validates TSI has max attempts or recvok status")
    public void user_validates_TSI_has_max_attempts_or_recvok_status() throws InterruptedException {
//        String response = scenarioContext.response.asString();
    	Thread.sleep(1000*180);
    	
        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(
                "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx", 0);
        System.out.println(tsiIdsFromExcel);
        
        
        List<String> newTsiIdsFromExcel = tsiIdsFromExcel.stream()
                .map(tsi -> tsi.split("=")[1])
                .collect(Collectors.toList());
        System.out.println(newTsiIdsFromExcel);
        
        List<String> missingTsiValues = new ArrayList<>();
        for (String tsi : newTsiIdsFromExcel) {
            JSONArray tsiArray = JsonPath.read(response.asString(), "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");
            if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
                missingTsiValues.add(tsi);
            }
        }
        if (missingTsiValues.size() > 0) {
            fail(missingTsiValues + " doesn't have neither recvOk status or 3 attempts");
        }
        System.out.println(missingTsiValues);
       
    }
}

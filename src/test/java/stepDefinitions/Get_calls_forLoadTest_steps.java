package stepDefinitions;


import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import utils.ConfigReader;
import utils.ExcelUtility;
import utils.Load_RestRequestUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

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
        response.asPrettyString();
        List<Integer> faxId = JsonPath.read(response.asString(), "$..FaxId");

        List<String> Tsi = JsonPath.read(response.asPrettyString(), "$..TSI");
        System.out.println("**All TSI Ids from response" + "**" + Tsi + "**");
        System.out.println("*** All job Ids from response " + "**" + faxId + "**");
    }

    @Given("user validates {int} is right getCall status code")
    public void user_validates_is_right_getCall_status_code(int expectedStatus) {
        int actualStatus = response.getStatusCode();
        System.out.println("**" + "The actual status code is " + "**" + actualStatus);
        System.out.println("**" + "The expected status code is " + "**" + expectedStatus);
        assertEquals(expectedStatus, actualStatus);
    }

    @Then("user validates recent TSI present in response")
    public void user_validate_recent_TSI_present_in_response() throws InterruptedException, URISyntaxException {

//    	Thread.sleep(1000*180);
        List<String> tsiIdsFromResponse = JsonPath.read(response.asString(), "$..TSI");
        List<String> newTsiIdsFromResponse = tsiIdsFromResponse.stream().distinct().collect(Collectors.toList());

        System.out.println("**TSI ids from response" + tsiIdsFromResponse);
        System.out.println("** TSI ids which collected in excel " + newTsiIdsFromResponse);

        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        String excelFilePath = file.getAbsolutePath();

        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);
        System.out.println(tsiIdsFromExcel);

        List<String> newTsiIdsFromExcel = tsiIdsFromExcel.stream()
                .map(tsi -> tsi.split("=")[1])
                .collect(Collectors.toList());


        assertTrue("TSI values from excel file are not present in the response", newTsiIdsFromResponse.containsAll(newTsiIdsFromExcel));
    }

    @Then("user validates TSI has max attempts or recvok status")
    public void user_validates_TSI_has_max_attempts_or_recvok_status() throws URISyntaxException {
        URL url = getClass().getClassLoader().getResource("dataFile/testData.xlsx");
        File file = Paths.get(url.toURI()).toFile();
        String excelFilePath = file.getAbsolutePath();
        List<String> tsiIdsFromExcel = ExcelUtility.getColumnData(excelFilePath, 0);

        List<String> newTsiIdsFromExcel = tsiIdsFromExcel.stream()
                .map(tsi -> tsi.split("=")[1])
                .collect(Collectors.toList());
        System.out.println("reading new TSI's from excel " + newTsiIdsFromExcel);

        List<String> missingTsiValues = new ArrayList<>();
        for (String tsi : newTsiIdsFromExcel) {
            System.out.println("checking for this TSI  in entire response " + "**" + tsi);
            //Get all metadata of the TSI from the excel
            JSONArray tsiArray = JsonPath.read(response.asString(), "$..FaxInfo[?(@.TSI =~/" + tsi + "/)]");

            //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
            if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
                missingTsiValues.add(tsi);
            }

            //Get the Fax status values of all the TSi from excel
            List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());
            System.out.println(statuses);
            //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
            assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

            //checking if the last status is recvOk then previous status should be recvIncomplete
            if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
                assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));


            }
        }

        //Check TSI metadata contains either recvOk status or max of 3 attempts
        if (missingTsiValues.size() > 0) {
            fail(missingTsiValues + "****" + " doesn't have neither recvOk status or 3 attempts" + "**");
        } else {

            System.out.println("***** " + "each recently generated TSI id's  has valid status and correct RetryCount");
        }
    }
}

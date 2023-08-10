package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GetCall_List_of_area_code_of_specific_city extends TestBase {

    @Test(testName = "List of Area codes for specific city and province", groups = {"smoke"})
    void GetListOfAreaCode() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        String responseElement = data.get("area_code");
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        //System.out.println(responseGetCall.asPrettyString());
        List<String> AreaCodeArrayList = JsonPath.read(responseGetCall.asPrettyString(),"$..area_code");
        for (int i=0; i<AreaCodeArrayList.size();i++){
        if (AreaCodeArrayList.get(i).contains(responseElement)){
            System.out.println(":this "+responseElement+ " area code belongs to city of state that we have entered from json file");
            }
          }
        System.out.println(AreaCodeArrayList);
        assertTrue(responseElement+" area code entered not exist on the mentioned city",AreaCodeArrayList.contains(responseElement));
    }
    @Test(testName = "List of city and province of specific State", groups = {"smoke"})
    void GetListOfAreaCodeofspecificCitywithState() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of areaCodes of specific State", groups = {"smoke"})
    void GetListOfAreaCodeofspecificState() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of users with no FaxNumbers", groups = {"smoke"})
    void GetListOfUsersWithName() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of Departments", groups = {"smoke"})
    void GetListOfDepartments() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of states codes or provinces ", groups = {"smoke"})
    void GetListOfcityCodesOrProvinces() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());

    }
    @Test(testName = "List of All FaxNumbers owned by Org ", groups = {"smoke"})
    void GetListOfAllFaxNumberOwnedOrg() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "Gets details about request to add a fax # ", groups = {"smoke"})
    void GetDetailsAboutRequest() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodes(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of city and province of specific State with new URL", groups = {"smoke"})
    void GetListOfAreaCodeofspecificCitywithState2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of Area codes for specific city and province with new URL", groups = {"smoke"})
    void GetListOfAreaCode2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        String responseElement = data.get("area_code");
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        //System.out.println(responseGetCall.asPrettyString());
        List<String> AreaCodeArrayList = JsonPath.read(responseGetCall.asPrettyString(),"$..area_code");
        for (int i=0; i<AreaCodeArrayList.size();i++){
            if (AreaCodeArrayList.get(i).contains(responseElement)){
                System.out.println(":this "+responseElement+ " area code belongs to city of state that we have entered from json file");
            }
        }
        System.out.println(AreaCodeArrayList);
        assertTrue(responseElement+" area code entered not exist on the mentioned city",AreaCodeArrayList.contains(responseElement));
    }
    @Test(testName = "List of areaCodes of specific State with new URL", groups = {"smoke"})
    void GetListOfAreaCodeofspecificState2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of users with no FaxNumbers with new URL", groups = {"smoke"})
    void GetListOfUsersWithName2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of Departments with new URl", groups = {"smoke"})
    void GetListOfDepartments2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of All FaxNumbers owned by Org with new URL", groups = {"smoke"})
    void GetListOfAllFaxNumberOwnedOrg2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());
    }
    @Test(testName = "List of states codes or provinces with New URL", groups = {"smoke"})
    void GetListOfcityCodesOrProvinces2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());

    }
    @Test(testName = "Request to add FaxId with New URL", groups = {"smoke1"})
    void GetDetailsAboutRequest2() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        Response responseGetCall = RestRequestUtils.getStateCodesNewURL8082(data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseGetCall.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 3);
        System.out.println(responseGetCall.asPrettyString());

    }
}


package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Lookup_Dept_DID_up_to_3_times extends TestBase {

    @Test(priority = 1,testName = "VerifyCallingPartyNumber", groups = {"Regression"})
    void Verify_CallingPartyNumber_is_user_DID() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));

        Thread.sleep(1000*40);
        /*second but short call
         * */
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialOutbound"));
        int JobID = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxId");
        String FAxUSerID = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxUserId");
        System.out.println("FaxuserID is :" +FAxUSerID);
        //Execute first query
        String database=String.format("select CallingPartyNumber from auto1.sendstatus where (JobID='%s')",JobID);
        DataBaseUtility.executeSQLQueryRecvD(database);
        System.out.println(database);

    }

    @Test(priority = 2,testName = "VerifyCallingPartyNumber", groups = {"Regression"})
    void Verify_CallingPartyNumber_is_user_DID_Regression() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));

        Thread.sleep(1000*40);
        /*second but short call
         * */
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialOutbound"));
        int JobID = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxId");
        String FAxUSerID = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxUserId");
        System.out.println("FaxuserID is :" +FAxUSerID);
        //Execute first query
        String database=String.format("select CallingPartyNumber from auto1.sendstatus where (JobID='%s')",JobID);
        DataBaseUtility.executeSQLQueryRecvD(database);
        System.out.println(database);

    }

    @Test(priority = 3,testName = "VerifyCallingPartyNumber", groups = {"Regression"})
    void Verify_CallingPartyNumber_is_user_DID_test1() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));

        Thread.sleep(1000*40);
        /*second but short call
         * */
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialOutbound"));
        int JobID = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxId");
        String FAxUSerID = JsonPath.read(responseReceiveFax2.asPrettyString(),"$.FaxInfo[0].FaxUserId");
        System.out.println("FaxuserID is :" +FAxUSerID);
        //Execute first query
        String database=String.format("select CallingPartyNumber from auto1.sendstatus where (JobID='%s')",JobID);
        DataBaseUtility.executeSQLQueryRecvD(database);
        System.out.println(database);

    }
}

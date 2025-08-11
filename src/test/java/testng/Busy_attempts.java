package testng;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Busy_attempts extends TestBase {


    @Test(testName = "Add minimum busy attempts", groups = {"RegressionFor"})
    void add_minimum_busy_attempts() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete_attempts_min"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete_attempts_max2"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("update_attempts_min"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("update_attempts_max"));
        //Execute second query


        Thread.sleep(1000*10);
        Response response = RestRequestUtils.putScenario(data.get("put_call_Url"));
        Assert.assertEquals(response.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**" + (data.get("put_call_Url")));
        System.out.println(response.asPrettyString());
        System.out.println("------------------------------------------------------------------------");

        String tsi = FileReader.randomNumberFor_TSI();
        System.out.println("hold the execution before submitting");
        Thread.sleep(1000*5);
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSIAPI(data.get("post_call_Url") + tsi,
                FileReader.readfile("100page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("100page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));


        String tsi2 = FileReader.randomNumberFor_TSI();
        System.out.println("hold the execution before submitting.");
        Thread.sleep(1000*30);
        Response responseSubmitFax2 = RestRequestUtils.sendFaxWithNewTSIAPI(data.get("post_call_Url") + tsi2,
                FileReader.readfile("100page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("100page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax2.statusCode()), data.get("statusCode"));

        System.out.println("hold the execution before submitting..");
        Thread.sleep(1000*30);

        DataBaseUtility.executeSQLQueryRecvD(ConfigReader.getProperty("select_sendstatus_by_JObId_last2"));

        Response responseP = RestRequestUtils.putScenario(data.get("put_call_Url_Change"));
        Assert.assertEquals(responseP.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println("**" + (data.get("put_call_Url_Change")));
        System.out.println(response.asPrettyString());
        System.out.println("------------------------------------------------------------------------");

        Thread.sleep(1000*3);
        DataBaseUtility.executeSQLQueryRecvD(ConfigReader.getProperty("select_settings"));
        Thread.sleep(1000*3);
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete_MinbusyTries"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete-attempts_min3"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete_attempts_max3"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("update_attempts_min3"));
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("update_attempts_max3"));


    }
}

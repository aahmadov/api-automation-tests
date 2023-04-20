package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class IgnoreBusyFeature extends TestBase {


    @Test(testName = "IBF - IgnoreBusy feature results in wrong first send delay", groups = {"Regression"})
    void IgnoreBusyFeature() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMinDelete"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMaxDelete"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMinDelete"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMaxDelete"));


        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMin"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMax"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMin"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMax"));


        String tsi = FileReader.randomNumberFor_TSI();
        /*first long call
        * */
        Response responseSubmitFaxLong = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("100page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("100page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*60);
       /*second but short call
       * */
        Response responseSubmitFax2ndAttempt = RestRequestUtils.sendFaxWithNewTSI2(data.get("post_call_Url") + tsi,
                FileReader.readfile("1page"),
                data.get("faxNumber"), data.get("credentialOutbound"));

         Thread.sleep(1000*240);

        DataBaseUtility2.executeSQLQuery2("select ignoredattempts,JobID from auto1.sendstatus order by JobID desc limit 1;");
        /*second short call
         * */
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialOutbound"));
        String Faxstatus = JsonPath.read(responseReceiveFax.asPrettyString(),"$.FaxInfo[1].FaxStatus");
        System.out.println(":Fax status is :"+Faxstatus);

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        String databasequery =String.format("select count(*) from auto1.senddeliverystat where (jobid='%s');",jobId);
        Thread.sleep(1000*30);

         List<Map<String,Object>> results = DataBaseUtility2.executeSQLQuery2(databasequery);
        if (results.size() == 0) {
            fail("***:Not verified that the delay stats has only one entry for this job.");
        }
        System.out.println("verified that there is delay stats has only one entry for this job is "+results);

    }
    @Test(testName = "IBF - IgnoreBusy feature results in wrong first send delay", groups = {"Regression1"})
    void IgnoreBusyFeatureCopy() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMinDelete"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMaxDelete"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMinDelete"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMaxDelete"));


        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMin"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxRetryIntervalMax"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMin"));
        DataBaseUtility2.executeSQLUpdate2(ConfigReader.getProperty("SendFaxMaxAttemptsMax"));


        String tsi = FileReader.randomNumberFor_TSI();
        /*first long call
         * */
        Response responseSubmitFaxLong = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("100page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("100page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFaxLong.statusCode()), data.get("statusCode"));
        Thread.sleep(1000*60);
        /*second but short call
         * */
        Response responseSubmitFax2ndAttempt = RestRequestUtils.sendFaxWithNewTSI2(data.get("post_call_Url") + tsi,
                FileReader.readfile("1page"),
                data.get("faxNumber"), data.get("credentialOutbound"));

        Thread.sleep(1000*240);

        DataBaseUtility2.executeSQLQuery2("select ignoredattempts,JobID from auto1.sendstatus order by JobID desc limit 1;");
        /*second short call
         * */
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialOutbound"));
        String Faxstatus = JsonPath.read(responseReceiveFax.asPrettyString(),"$.FaxInfo[1].FaxStatus");
        System.out.println(":Fax status is :"+Faxstatus);

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        String databasequery =String.format("select count(*) from auto1.senddeliverystat where (jobid='%s');",jobId);
        Thread.sleep(1000*30);

        List<Map<String,Object>> results = DataBaseUtility2.executeSQLQuery2(databasequery);
        if (results.size() == 0) {
            fail("***:Not verified that the delay stats has only one entry for this job.");
        }
        System.out.println("verified that there is delay stats has only one entry for this job is "+results);

    }
}
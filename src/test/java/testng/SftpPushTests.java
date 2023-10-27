package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.*;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SftpPushTests extends TestBase {

    @Test(priority = 1,testName = "SFTP - Setup and Test Simple Authentication", groups = {"Regression1"})
    void testWithSimpleAuthentication() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("delete_ftp_realms1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("delete_ftp_users1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("insert_ftp_realms1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("insert_ftp_users1"));

        //DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("truncate_recvstatus1"));

        //Thread.sleep(1000*30);

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
        Thread.sleep(1000 * 120);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));

//        //int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        List<String> TSI = JsonPath.read(responseReceiveFax.asPrettyString(),"$..TSI");
        //int jobId = -1;
//        for (int i=0; i<TSI.size();i++){
//            if (TSI.get(i).contains(tsi)){
       // jobId = faxInfo.getInt("FaxId");
//                System.out.println(":this "+ jobId+ " area code belongs to city of state that we have entered from json file");
//            }
//        }

        String metadata = responseReceiveFax.prettyPrint();
        System.out.println(metadata);

        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(metadata);

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;
        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=",""))) {
                jobId = faxInfo.getInt("FaxId");
            }
        }
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));




    }
    @Test(priority = 3,testName = "SFTP - Test With Certificate No Passphrase", groups = {"Regression1"})
    void testWithCertificateNoPassphrase() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("rsa_private_key"));

//        Thread.sleep(1000*60);
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
        Thread.sleep(1000*120);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));
        String metadata = responseReceiveFax.prettyPrint();
        System.out.println(metadata);

        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(metadata);

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;
        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=",""))) {
                jobId = faxInfo.getInt("FaxId");
            }
        }
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));

    }

    @Test(priority = 2,testName = "SFTP - Test With Certificate With Passphrase", groups = {"Regression1"})
    void testWithCertificateWithPassphrase() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("rsa_private_key_with_passphrase"));

        //Thread.sleep(60000);

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
        Thread.sleep(1000*120);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));
        String metadata = responseReceiveFax.prettyPrint();
        System.out.println(metadata);

        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(metadata);

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;
        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=",""))) {
                jobId = faxInfo.getInt("FaxId");
            }
        }
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));

    }

    @Test(priority = 1,testName = "SFTP - A-Test Simple Authentication", groups = {"RegressionAb"})
    void testWithSimpleAuthenticationAbbas() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete_ftp_realms"));

        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("delete_ftp_users"));

        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("insert_ftp_realms"));

        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("insert_ftp_users"));

        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("truncate_recvstatus"));

        //Thread.sleep(1000*30);

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
        Thread.sleep(1000*180);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));
        String metadata = responseReceiveFax.prettyPrint();
        System.out.println(metadata);

        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(metadata);

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;
        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=",""))) {
                jobId = faxInfo.getInt("FaxId");
            }
        }
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + "(1).pdf"));

    }
    @Test(priority = 3,testName = "SFTP - C-Test With Certificate No Passphrase", groups = {"RegressionAb"})
    void testWithCertificateNoPassphraseAbbas() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("rsa_private_key"));

//        Thread.sleep(1000*60);
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
        Thread.sleep(1000*180);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));
        String metadata = responseReceiveFax.prettyPrint();
        System.out.println(metadata);

        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(metadata);

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;
        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=",""))) {
                jobId = faxInfo.getInt("FaxId");
            }
        }
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + "(1).pdf"));
    }
    @Test(priority = 2,testName = "SFTP - B-Test With Certificate With Passphrase", groups = {"RegressionAb"})
    void testWithCertificateWithPassphraseAbbas() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;
        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate2(ConfigReader.getProperty("rsa_private_key_with_passphrase"));
        //Thread.sleep(60000);

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
        Thread.sleep(1000*180);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));
        String metadata = responseReceiveFax.prettyPrint();
        System.out.println(metadata);

        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(metadata);

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;
        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=",""))) {
                jobId = faxInfo.getInt("FaxId");
            }
        }
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + "(1).pdf"));
    }
}

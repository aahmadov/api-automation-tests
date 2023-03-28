package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SftpPushTests extends TestBase {

    @Test(testName = "SFTP - Setup and Test Simple Authentication", groups = {"RegressionAnat"})
    void testWithSimpleAuthentication() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
       DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("delete_ftp_realms1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("delete_ftp_users1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("insert_ftp_realms1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("insert_ftp_users1"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("truncate_recvstatus1"));

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
        Thread.sleep(1000*120);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));
     
        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("************ Inbound Fax Job id: " + jobId);

        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));
    }

    @Test(testName = "SFTP - Test With Certificate No Passphrase", groups = {"RegressionAnat"})
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
        Thread.sleep(1000*160);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));
    }

    @Test(testName = "SFTP - Test With Certificate With Passphrase", groups = {"RegressionAnat"})
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

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("************Inbound Fax Job id: " + jobId);

        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));
    }

    @Test(testName = "SFTP - Setup and Test Simple Authentication", groups = {"Regression"})
    void testWithSimpleAuthenticationAbbas() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first query - Delete
        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("delete_ftp_realms"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("delete_ftp_users"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("insert_ftp_realms"));

        DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("insert_ftp_users"));

        //DataBaseUtility.executeSQLUpdate(ConfigReader.getProperty("truncate_recvstatus"));

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
        Thread.sleep(1000*160);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("************ Inbound Fax Job id: " + jobId);

        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));
    }
    @Test(testName = "SFTP - Test With Certificate No Passphrase", groups = {"Regression"})
    void testWithCertificateNoPassphraseAbbas() throws Exception {
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
        Thread.sleep(1000*150);
        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialInbound"));

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("************ Inbound Fax Job id: " + jobId);
        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));
    }
    @Test(testName = "SFTP - Test With Certificate With Passphrase", groups = {"Regression"})
    void testWithCertificateWithPassphraseAbbas() throws Exception {
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

        int jobId = JsonPath.read(responseReceiveFax.asPrettyString(), "$.FaxInfo[0].FaxId");
        System.out.println("************Inbound Fax Job id: " + jobId);

        assertTrue(SftpUtils.checkFileExist(data.get("username"), data.get("password"), data.get("filePath"), jobId + ".pdf"));
    }
}

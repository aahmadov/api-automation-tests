package testng;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class Purge_after_faxSend extends TestBase {


    @Test(testName = "PurgeAfterDownloadedBy to SFTP", groups = {"Regression81Test"})
    public void PurgeAfterDownloadSFTP84() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        DataBaseUtility.executeSQLUpdateRecvD81("DELETE FROM auto1.recvstatus order by  JobID  DESC LIMIT 10");

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Thread.sleep(1000 * 3);
        DataBaseUtility.executeSQLUpdateRecvD81("delete from auto1.settings where sname= 'cleanupd.PurgeAfterDownloadedBy';");
        Thread.sleep(1000 * 3);
        System.out.println("message: Set demo.settings.cleanupd.PurgeAfterDownloadedBy to SFTP.");
        DataBaseUtility.executeSQLUpdateRecvD81("INSERT INTO auto1.settings (sname, svalue) VALUES ('cleanupd.PurgeAfterDownloadedBy', 'SFTP');");
        Thread.sleep(1000 * 3);
        DataBaseUtility.executeSQLQueryAuto181(ConfigReader.getProperty("settings_Auto1"));

        System.out.println("message: Receive 2 or more faxes for your org ");
        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", file);
            requestData.put("FaxNumber", data.get("faxNumber"));
            requestData.put("url", data.get("post_call_Url") + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response response = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentialOutbound"));
            System.out.println(response.asString());
            assertEquals(201, response.statusCode());

            System.out.println(":query to inbound fax");
        }
        Thread.sleep(1000 * 120);
        System.out.println("message: Set one of the demo.recvstatus SftpPushMark to 0 or 1");
        DataBaseUtility.executeSQLUpdateRecvD81("UPDATE auto1.recvstatus SET SftpPushMark = 0 ORDER BY ModifyTime DESC LIMIT 1;");

        Thread.sleep(1000 * 20);
        String host = "10.250.1.84";
        String user = "Administrator";
        String password = "5yeDJH4el!#hW";
        String commandExe = "C:\\Softlinx\\ReplixServer\\bin\\rpxcleanupd";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(commandExe);
            System.out.println();
            channel.connect();

            channel.disconnect();
            session.disconnect();

        } catch (JSchException e) {
            e.printStackTrace();


        }
        Thread.sleep(1000 * 10);
        DataBaseUtility.executeSQLQueryAuto181("SELECT * FROM auto1.recvstatus;");
    }

    @Test(testName = "PurgeAfterDownloadedBy to DM", groups = {"Regression81"})
    public void PurgeAfterDownloadDM84() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        DataBaseUtility.executeSQLUpdateRecvD81("DELETE FROM auto1.recvstatus order by  JobID  DESC LIMIT 10");

        File file = FileReader.getFileUsingPageSize(data.get("Pages"), data.get("fileType"));
        Thread.sleep(1000 * 3);
        DataBaseUtility.executeSQLUpdateRecvD81("delete from auto1.settings where sname= 'cleanupd.PurgeAfterDownloadedBy';");
        Thread.sleep(1000 * 3);
        System.out.println("message: Set demo.settings.cleanupd.PurgeAfterDownloadedBy to DM.");
        DataBaseUtility.executeSQLUpdateRecvD81("INSERT INTO auto1.settings (sname, svalue) VALUES ('cleanupd.PurgeAfterDownloadedBy', 'DM');");
        Thread.sleep(1000 * 3);
        DataBaseUtility.executeSQLQueryAuto181(ConfigReader.getProperty("settings_Auto1"));

        System.out.println("message: Receive 2 or more faxes for your org ");
        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("**" + "it is iteration time in the loop :" + i);

            String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();

            Map<String, Object> requestData = new ConcurrentHashMap<>();
            requestData.put("filename", file);
            requestData.put("FaxNumber", data.get("faxNumber"));
            requestData.put("url", data.get("post_call_Url") + firstLoadTest_TSI);
            requestData.put("coverPageEnabled", data.get("coverPageEnabled"));

            Response response = Load_RestRequestUtils.sendFax_loadTest81(requestData, data.get("credentialOutbound"));
            System.out.println(response.asString());
            assertEquals(201, response.statusCode());

            System.out.println(":query to inbound fax");
        }
        Thread.sleep(1000 * 120);
        System.out.println("message: Set one of the demo.recvstatus DeliveryMgrMark to 1");
        DataBaseUtility.executeSQLUpdateRecvD81("UPDATE auto1.recvstatus SET DeliveryMgrMark = 1 ORDER BY ModifyTime DESC LIMIT 1;");

        Thread.sleep(1000 * 20);
        String host = "10.250.1.84";
        String user = "Administrator";
        String password = "5yeDJH4el!#hW";
        String commandExe = "C:\\Softlinx\\ReplixServer\\bin\\rpxcleanupd";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(commandExe);
            System.out.println();
            channel.connect();

            channel.disconnect();
            session.disconnect();

        } catch (JSchException e) {
            e.printStackTrace();


        }
        Thread.sleep(1000 * 10);
        DataBaseUtility.executeSQLQueryAuto181("SELECT * FROM auto1.recvstatus;");
    }
}


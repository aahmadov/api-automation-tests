package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.testng.Assert.assertEquals;

public class EncyrptFaxFilesRegistrySettingsValueshould_be_1 extends TestBase {

    @Test(priority = 1, testName = "EncryptFaxFiles registry setting default should be 1", groups = {"Regression81"})
    void putScenarioForEncyrptRegistrySettings_0() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        //Execute first registry query
        Response response1 = RestRequestUtils.putScenario81(data.get("put_call_Url_0"));
        Assert.assertEquals(response1.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response1.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url_0")));
        System.out.println("------------------------------------------------------------------------");
        Thread.sleep(1000 * 5);
        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI81(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        org.junit.Assert.assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 80);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax81(data.get("get_call_Url"), data.get("credentialOutbound2"));
        // String metadata = responseReceiveFax.prettyPrint();


        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(responseReceiveFax.prettyPrint());

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;

        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=", ""))) {
                jobId = faxInfo.getInt("FaxId");
                System.out.println("************ Fax Job id: " + jobId);

                String filePath = data.get("filePath");
                try (FileObject dir = VFS.getManager().resolveFile(filePath)) {
                    boolean isTifFileFound = false;

                    if (dir.getType().equals(FileType.FOLDER)) {
                        FileObject[] files = dir.getChildren();
                        for (FileObject file : files) {
                            if (file.getType().equals(FileType.FILE)) {
                                String fileName = file.getName().getBaseName();
                                if (fileName.endsWith(".tif") && fileName.contains(String.valueOf(jobId))) {
                                    System.out.println("Found TIF file: " + fileName);
                                    isTifFileFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    assertTrue("TIF file with jobId not found in the directory.", isTifFileFound);
                } catch (IOException e) {
                    System.out.println("Error while checking for TIF file: " + e.getMessage());
                }

            }

        }
    }
    @Test(priority = 2, testName = "EncryptFaxFiles registry setting default should be 1", groups = {"Regression81"})
    void putScenarioForEncyrptRegistrySettings_1() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        //Execute first registry query
        Response response1 = RestRequestUtils.putScenario81(data.get("put_call_Url_1"));
        Assert.assertEquals(response1.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response1.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url_1")));
        System.out.println("------------------------------------------------------------------------");
        Thread.sleep(1000 * 5);
        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI81(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        org.junit.Assert.assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 80);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax81(data.get("get_call_Url"), data.get("credentialOutbound2"));
        // String metadata = responseReceiveFax.prettyPrint();


        // Parse the metadata string into a JSON object
        JSONObject jsonObject = new JSONObject(responseReceiveFax.prettyPrint());

        // Extract the FaxInfo array
        JSONArray faxInfoArray = jsonObject.getJSONArray("FaxInfo");

        // Search for the FaxInfo object with the desired TSI value
        int jobId = -1;

        for (int i = 0; i < faxInfoArray.length(); i++) {
            JSONObject faxInfo = faxInfoArray.getJSONObject(i);

            if (faxInfo.getString("TSI").equals(tsi.toString().replace("?TSI=", ""))) {
                jobId = faxInfo.getInt("FaxId");
                System.out.println("************ Fax Job id: " + jobId);

                String filePath = data.get("filePath");
                try (FileObject dir = VFS.getManager().resolveFile(filePath)) {
                    boolean isTifFileFound = false;

                    if (dir.getType().equals(FileType.FOLDER)) {
                        FileObject[] files = dir.getChildren();
                        for (FileObject file : files) {
                            if (file.getType().equals(FileType.FILE)) {
                                String fileName = file.getName().getBaseName();
                                if (fileName.endsWith(".tifx") && fileName.contains(String.valueOf(jobId))) {
                                    System.out.println("Found TIFx file: " + fileName);
                                    isTifFileFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    assertTrue("TIFx file with jobId not found in the directory.", isTifFileFound);
                } catch (IOException e) {
                    System.out.println("Error while checking for TIFx file: " + e.getMessage());
                }



            }


        }
    }
}
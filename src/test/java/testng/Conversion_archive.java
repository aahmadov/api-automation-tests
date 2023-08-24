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
import utils.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Conversion_archive extends TestBase {

    @Test(priority = 2, testName = "Conversion archive per realm and per realm/user", groups = {"Regression"})
    void conversion_archive_per_realm_and_per_realm_user() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first registry query

        Response response1 = RestRequestUtils.putScenario(data.get("put_call_Url1"));
        Assert.assertEquals(response1.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response1.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url1")));
        System.out.println("------------------------------------------------------------------------");
         Thread.sleep(1000*5);
        Response response2 = RestRequestUtils.putScenario(data.get("put_call_Url2"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url2")));
        System.out.println("------------------------------------------------------------------------");

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
        Thread.sleep(1000 * 180);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax(data.get("get_call_Url"), data.get("credentialOutbound2"));
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
                try (FileObject dest = VFS.getManager().resolveFile(filePath)) {
                    boolean isFolderFound = false;
                    if (dest.getType().equals(FileType.FOLDER)) {
                        FileObject[] children = dest.getChildren();
                        for (FileObject child : children) {
                            if (child.getType().equals(FileType.FOLDER)) {
                                String folderName = child.getName().getBaseName();
                                System.out.println("Folder Name: " + folderName);

                                if (folderName.contains("000000"+jobId)) {
                                    // Required folder with specific characters found
                                    System.out.println("Required folder exists: " + folderName);
                                    isFolderFound = true;
                                    break;
                                }

                            }
                        }
                    }
                    assertTrue("Specific folder does not exist in the directory.", isFolderFound);
                } catch (IOException exception) {
                    System.out.println("Exception occurred while checking file exist on the remote server. Exception: " + exception.getMessage());

                }


            }


        }
    }





    @Test(priority = 1, testName = "Conversion archive per realm and per realm/user", groups = {"Regression"})
    void conversion_archive_per_realm_and_per_realm_user_66() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first registry query

        Response response1 = RestRequestUtils.putScenario2(data.get("put_call_Url1"));
        Assert.assertEquals(response1.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response1.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url1")));
        System.out.println("------------------------------------------------------------------------");
        Thread.sleep(1000*5);
        Response response2 = RestRequestUtils.putScenario2(data.get("put_call_Url2"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url2")));
        System.out.println("------------------------------------------------------------------------");

        //Thread.sleep(1000*30);

        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI_66(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 180);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax66(data.get("get_call_Url"), data.get("credentialOutbound2"));
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
                try (FileObject dest = VFS.getManager().resolveFile(filePath)) {
                    boolean isFolderFound = false;
                    if (dest.getType().equals(FileType.FOLDER)) {
                        FileObject[] children = dest.getChildren();
                        for (FileObject child : children) {
                            if (child.getType().equals(FileType.FOLDER)) {
                                String folderName = child.getName().getBaseName();
                                System.out.println("Folder Name: " + folderName);

                                if (folderName.contains("000000"+jobId)) {
                                    // Required folder with specific characters found
                                    System.out.println("Required folder exists: " + folderName);
                                    isFolderFound = true;
                                    break;
                                }

                            }
                        }
                    }
                    assertTrue("Specific folder does not exist in the directory.", isFolderFound);
                } catch (IOException exception) {
                    System.out.println("Exception occurred while checking file exist on the remote server. Exception: " + exception.getMessage());

                }


            }


        }
    }

    @Test(priority = 1, testName = "Conversion archive per realm and per realm/user", groups = {"Regression1"})
    void conversion_archive_per_realm_and_per_realm_user_216_Copy() throws Exception {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        //Execute first registry query

        Response response1 = RestRequestUtils.putScenario2(data.get("put_call_Url1"));
        Assert.assertEquals(response1.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response1.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url1")));
        System.out.println("------------------------------------------------------------------------");
        Thread.sleep(1000*5);
        Response response2 = RestRequestUtils.putScenario2(data.get("put_call_Url2"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url2")));
        System.out.println("------------------------------------------------------------------------");

        //Thread.sleep(1000*30);
        String tsi = FileReader.randomNumberFor_TSI();
        Response responseSubmitFax = RestRequestUtils.sendFaxWithNewTSI_66(data.get("post_call_Url") + tsi,
                FileReader.readfile("3page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("3page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(responseSubmitFax.statusCode()), data.get("statusCode"));
        Thread.sleep(1000 * 180);

        Response responseReceiveFax = RestRequestUtils.responseRecieveFax66(data.get("get_call_Url"), data.get("credentialOutbound2"));
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
                try (FileObject dest = VFS.getManager().resolveFile(filePath)) {
                    boolean isFolderFound = false;
                    if (dest.getType().equals(FileType.FOLDER)) {
                        FileObject[] children = dest.getChildren();
                        for (FileObject child : children) {
                            if (child.getType().equals(FileType.FOLDER)) {
                                String folderName = child.getName().getBaseName();
                                System.out.println("Folder Name: " + folderName);

                                if (folderName.contains("000000"+jobId)) {
                                    // Required folder with specific characters found
                                    System.out.println("Required folder exists: " + folderName);
                                    isFolderFound = true;
                                    break;
                                }

                            }
                        }
                    }
                    assertTrue("Specific folder does not exist in the directory.", isFolderFound);
                } catch (IOException exception) {
                    System.out.println("Exception occurred while checking file exist on the remote server. Exception: " + exception.getMessage());

                }

            }

        }
    }
}


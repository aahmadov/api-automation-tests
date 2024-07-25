package testng;

import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;
import utils.Second_RestRequestUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class InboundFaxPageValidation20Test extends TestBase {
    @Test(testName = "validates the number of inbound Fax pages with registry setting (\"20 pages\")", groups = {"Regression81"})
    public void inboundFaxPageValidation20_81() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        Response response2 = RestRequestUtils.putScenario(data.get("put_call_Url"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url")));
        System.out.println("------------------------------------------------------------------------");


        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response response = RestRequestUtils.sendFaxWithNewTSI81(data.get("post_call_Url") + tsi,
                FileReader.readfile("20page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("20page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(response.statusCode()), data.get("statusCode"));

        String faxId = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        System.out.println("***** this is new generated  Fax number of outboundfax " + "**" + faxId + "**");


        Response outbound;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outbound = Second_RestRequestUtils.getOutboundWithCoverPage81(
                    data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
            Assert.assertEquals(200, outbound.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 15);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }

        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));


        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1_81(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from response
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());

        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }


    }

    @Test(testName = "validates the number of inbound Fax pages with registry setting (\"20 pages\")", groups = {"Regression"})
    public void inboundFaxPageValidation20() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Response response2 = RestRequestUtils.putScenario(data.get("put_call_Url"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url")));
        System.out.println("------------------------------------------------------------------------");
        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response response = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("20page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("20page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(response.statusCode()), data.get("statusCode"));

        String faxId = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        System.out.println("***** this is new generated  Fax number of outboundfax " + "**" + faxId + "**");


        Response outbound;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outbound = Second_RestRequestUtils.getOutboundWithCoverPage(
                    data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
            Assert.assertEquals(200, outbound.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 15);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }

        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));


        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from response
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());

        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }
    }

    @Test(testName = "Scale a PDF  ", groups = {"Regression"})
    public void inboundFaxPageValidationPDF() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName(testName);
        assert data != null;

        Response response2 = RestRequestUtils.putScenario(data.get("put_call_Url"));
        Assert.assertEquals(response2.getStatusCode(), 200);
        System.out.println("------------------------------------------------------------------------");
        System.out.println(response2.asPrettyString());
        System.out.println("**" + (data.get("put_call_Url")));
        System.out.println("------------------------------------------------------------------------");
        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response response = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("send-fax-270-legal"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("20page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(response.statusCode()), data.get("statusCode"));

        String faxId = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        System.out.println("***** this is new generated  Fax number of outboundfax " + "**" + faxId + "**");
        Response outbound;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outbound = Second_RestRequestUtils.getOutboundWithCoverPage(
                    data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
            Assert.assertEquals(200, outbound.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 15);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }
        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));
        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage2image(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));


        Thread.sleep(1000 * 5);

        int jobId = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$.FaxInfo.[0].FaxId");
        System.out.println("new generated inboundJob: " + jobId);


        Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from response
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());

        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }
        Response inboundFaxwithCoverPage2 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url2") + "/" + jobId + data.get("newInboundParam2"), data.get("credentialInbound"));
        Assert.assertEquals(inboundFaxwithCoverPage2.getStatusCode(), 200);

        byte[] pdfContent = inboundFaxwithCoverPage2.getBody().asByteArray();

        try {
            // Load the received PDF document
            PDDocument document = PDDocument.load(pdfContent);

            // Get the first page of the PDF
            PDPage page = document.getPage(0);

            // Get the dimensions of the page
            float width = page.getMediaBox().getWidth();//width=612.0 pixel
            float height = page.getMediaBox().getHeight();//height=792.0 pixel

            // Calculate the scaling factors
            double desiredWidthInches = 8.5;
            double desiredHeightInches = 11.00;
            double scaleX = desiredWidthInches / width;
            double scaleY = desiredHeightInches / height;

            // Scale the page dimensions
            double scaledWidth = width * scaleX;
            double scaledHeight = height * scaleY;

            // Print the scaled dimensions
            System.out.println("Original Dimensions: Width=" + width + ", Height=" + height);
            System.out.println("Scaled Dimensions: Width=" + scaledWidth + ", Height=" + scaledHeight);

            // Close the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//            try {
//                // Load the PDF document
//                PDDocument document = PDDocument.load(new File("C:\\Users\\Administrator\\workspace\\fs_test\\src\\test\\resources\\requestBody\\pdf\\8-5by5-5.pdf"));
//
//                // Get the number of pages
//                int pageCount = document.getNumberOfPages();
//
//                // Calculate the resolution to match the standard page size of 8.5x11 inches
//                double desiredWidthInches = 8.5; // Desired width in inches
//                double desiredHeightInches = 11.00; // Desired height in inches
//                int resolution = calculateResolution(document, desiredWidthInches, desiredHeightInches);
//
//                // Iterate through each page
//                for (int i = 0; i < pageCount; i++) {
//                    // Render the page as an image with the calculated resolution
//                    PDFRenderer renderer = new PDFRenderer(document);
//                    BufferedImage image = renderer.renderImageWithDPI(i, resolution);
//
//                    // Get the width and height of the image (page)
//                    int widthPixels = image.getWidth();
//                    int heightPixels = image.getHeight();
//
//                    // Print the size of the page in inches
//                    double widthInches = widthPixels / (double) resolution;
//                    double heightInches = heightPixels / (double) resolution;
//                    System.out.println("Page " + (i + 1) + " size: " + widthInches + "x" + heightInches + " inches");
//                }
//
//                // Close the document
//                document.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        private static int calculateResolution(PDDocument document, double desiredWidthInches, double desiredHeightInches) {
//            // Get the page size of the first page in points (1 inch = 72 points)
//            int pageWidthPoints = (int) document.getPage(0).getMediaBox().getWidth();
//            int pageHeightPoints = (int) document.getPage(0).getMediaBox().getHeight();
//
//            // Calculate the resolution to match the desired page size
//            int resolutionWidth = (int) (pageWidthPoints / desiredWidthInches);
//            int resolutionHeight = (int) (pageHeightPoints / desiredHeightInches);
//
//            // Use the minimum resolution to ensure that the entire page fits within the desired dimensions
//            return Math.min(resolutionWidth, resolutionHeight);
//        }

    @Test(testName = "validates the number of inbound Fax pages with registry setting (\"20 pages\")", groups = {"Regression1"})
    public void inboundFaxPageValidation20Copy() throws InterruptedException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

//        Response response2 = RestRequestUtils.putScenario(data.get("put_call_Url"));
//        Assert.assertEquals(response2.getStatusCode(), 200);
//        System.out.println("------------------------------------------------------------------------");
//        System.out.println(response2.asPrettyString());
//        System.out.println("**" + (data.get("put_call_Url")));
//        System.out.println("------------------------------------------------------------------------");
//
//        System.out.println(": registry settings " + "Abort page at 0");

        String tsi = FileReader.randomNumberFor_TSI();
        String onlyTsi = tsi.split("=")[1];
        Response response = RestRequestUtils.sendFaxWithNewTSI(data.get("post_call_Url") + tsi,
                FileReader.readfile("2page"),
                data.get("faxNumber"), data.get("credentialOutbound"));
        System.out.println("------------------------------------------------------------------------");
        System.out.println("************ " + data.get("post_call_Url"));
        System.out.println("********** " + FileReader.readfile("20page"));
        System.out.println("********* " + data.get("faxNumber"));
        System.out.println("------------------------------------------------------------------------");
        assertEquals(Integer.toString(response.statusCode()), data.get("statusCode"));

        String faxId = JsonPath.read(response.prettyPrint(), "$.FaxInfo[0].FaxNumber");
        System.out.println("***** this is new generated  Fax number of outboundfax " + "**" + faxId + "**");


        Response outbound;
        boolean isNotCompleted = true;
        boolean isFailed = false;
        int times = 0;
        do {
            System.out.println("*** waiting 30 secs to get the fax sending status ***");
            Thread.sleep(1000 * 30);
            outbound = Second_RestRequestUtils.getOutboundWithCoverPage(
                    data.get("post_call_Url") + data.get("newOutboundParam"), data.get("credentialOutbound"));
            Assert.assertEquals(200, outbound.getStatusCode());
            JSONArray tsiArray = JsonPath.read(outbound.asString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
            System.out.println("Outbound response related TSI is: " + tsiArray.toJSONString());
            if (tsiArray.size() > 0 && Arrays.asList("sendFailed", "sent").contains(((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString())) {
                isNotCompleted = false;
                if (((LinkedHashMap) tsiArray.get(0)).get("FaxStatus").toString().equals("sendFailed")) {
                    isFailed = true;
                }
                System.out.println("****** the post call TSI id " + "**" + onlyTsi + "**" + " and "
                        + " total page in attachment is " + "**" + ((LinkedHashMap) tsiArray.get(0)).get("PagesTotal") + "**");
                String errorMessage = JsonPath.read(outbound.asPrettyString(), "$.FaxInfo[0].ErrorText");

                System.out.println("Error message: " + "**" + errorMessage + "**");
            }
            times++;
        } while (isNotCompleted && times < 15);

        if (isFailed) {
            fail("Send failed for TSI id:" + onlyTsi);
        }

        System.out.println("****** " + (data.get("inboundFax_url") + data.get("newInboundParam")));
        Response inboundFaxwithCoverPage1 = Second_RestRequestUtils.getInboundWithCoverPage1(
                data.get("inboundFax_url") + data.get("newInboundParam"), data.get("credentialInbound"));
        Assert.assertEquals(200, inboundFaxwithCoverPage1.getStatusCode());

        System.out.println(":checking for this TSI " + ":" + onlyTsi + ":" + "in entire Inbound Fax response ");
        //Get all metadata of the TSI from the response
        System.out.println("*** INBOUND RESPONSE DATA FOR TSI ***");
        JSONArray tsiArray = JsonPath.read(inboundFaxwithCoverPage1.asPrettyString(), "$..FaxInfo[?(@.TSI =~/" + onlyTsi + "/)]");
        System.out.println("Response related TSI is: " + tsiArray.toJSONString());


        //Adding TSIs to the list if the metadata doesn't contains either recvOk status or max of 3 attempts of those TSI's
        if (tsiArray.stream().noneMatch(op -> ((LinkedHashMap) op).get("FaxStatus").equals("recvOk")) && tsiArray.size() != 3) {
            fail(":" + onlyTsi + ":" + "****" + " doesn't have neither recvOk status nor 3 attempts" + "**");
        }

        //Get the Fax status values of all the TSi from response
        List<String> statuses = tsiArray.stream().map(tsiJson -> ((LinkedHashMap) tsiJson).get("FaxStatus").toString()).collect(Collectors.toList());

        //Assertion all the metadata contains only either recvIncomplete or recvOk Fax status
        assertTrue(statuses.stream().allMatch(status -> status.equals("recvIncomplete") || status.equals("recvOk")));

        //checking if the last status is recvOk then previous status should be recvIncomplete
        if (statuses.size() > 0 && statuses.get(0).equals("recvOk")) {
            assertTrue(statuses.stream().skip(1).allMatch(status -> status.equals("recvIncomplete")));
        }

    }



}

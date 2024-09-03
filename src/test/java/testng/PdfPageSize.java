package testng;

import com.jayway.jsonpath.JsonPath;
import com.testautomationguru.utility.PDFUtil;
import io.restassured.response.Response;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Assert;
import org.testng.annotations.Test;
import utils.FileReader;
import utils.JsonUtils;
import utils.RestRequestUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PdfPageSize extends TestBase {

    @Test(testName = "Scan PDF of new fax", groups = {"Regression81"})
    public void sendFaxAndScanPdf2() throws InterruptedException, IOException {
        System.out.println("Test case name: " + testName);

        // Step 1: Send the fax and receive metadata
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;

        String tsi = FileReader.randomNumberFor_TSI();
        File file = FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType"));
        System.out.println(file);
        Response response = RestRequestUtils.sendFaxWithRecipent_withTiff_81(
                data.get("post_call_Url"), file, data.get("faxNumber"), data.get("credentials"));

        assertEquals(Integer.parseInt(data.get("StatusCode")), response.getStatusCode());
        String resp = response.prettyPrint();

        String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();
        assertTrue(resp.contains(faxId));
        // Wait for the fax to be processed
        Thread.sleep(1000 * 30);

        // Step 2: Receive the fax and save it as a PDF file
        Response responseReceiveFax2 = RestRequestUtils.responseRecieveFaxforTiff_81_new(data.get("get_call_Url") + "/" + faxId + "/image?FaxContentType=pdf",
                data.get("credentialOutbound"));
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("************ " + data.get("get_call_Url") + "/" + faxId + "/image?FaxContentType=pdf");
        System.out.println("------------------------------------------------------------------------");
        Assert.assertEquals(data.get("statusCodeOfGetCall"), Integer.toString(responseReceiveFax2.statusCode()));
        // Get the PDF content as a byte array
        byte[] pdfContent = responseReceiveFax2.asByteArray();
        // Measure the size of the PDF file
        int pdfSize = pdfContent.length;
        System.out.println("PDF File Size: " + pdfSize + " bytes");
        // Load the PDF content using PDFBox
        PDDocument document = PDDocument.load(pdfContent);
        // Get the page count
        int pageCount = document.getNumberOfPages();
        System.out.println("Page count is: " + pageCount);

        // Iterate over all pages to get their dimensions
        for (PDPage page : document.getPages()) {
            PDRectangle mediaBox = page.getMediaBox();
            float widthPoints = mediaBox.getWidth();
            float heightPoints = mediaBox.getHeight();

            // Convert points to inches (1 inch = 72 points)
            float widthInches = widthPoints / 72;
            float heightInches = heightPoints / 72;

            System.out.println("Page size: " + widthInches + " x " + heightInches + " inches");
        }
        // Close the document
        document.close();

    }

        @Test(testName = "Scan PDF of new fax", groups = {"Regression81forTest"})
        public void sendFaxAndScanPdf() throws InterruptedException {
            System.out.println("Test case name: " + testName);

            // Step 1: Send the fax and receive metadata
            Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
            assert data != null;

            String tsi = FileReader.randomNumberFor_TSI();
            File file = FileReader.getFileUsingPageSize(data.get("pageSize"), data.get("fileType"));
            System.out.println(file);
            Response response = RestRequestUtils.sendFaxWithRecipent_withTiff_81(
                    data.get("post_call_Url") + tsi, file, data.get("faxNumber"), data.get("credentials"));

            assertEquals(Integer.parseInt(data.get("expectedStatusCode")), response.getStatusCode());
            String resp = response.prettyPrint();

            String faxId = JsonPath.read(resp, "$.FaxInfo[0].FaxId").toString();
            assertTrue(resp.contains(faxId));

            Thread.sleep(1000 * 60); // Wait for the fax to be processed

            // Step 2: Receive the fax and save it as a PDF file with embedded JSON data
            Response responseReceiveFax2 = RestRequestUtils.responseRecieveFaxforTiff_81(data.get("get_call_Url"), data.get("credentialInbound"));
            int FaxId = JsonPath.read(responseReceiveFax2.asPrettyString(), "$.FaxInfo[0].FaxId");

            // Specifying the directory containing the PDF files
            String username = "Administrator";
            String password = "5yeDJH4el!#hW";
            String remoteFilePath = "//10.250.1.84/c$/Users/Administrator/Downloads/_recv-fax-1496.pdf";

            FileSystemManager fsManager = null;
            FileObject remoteFile = null;
            StaticUserAuthenticator auth = null;
            FileSystemOptions opts = new FileSystemOptions();

            try {
                // Set up the file system manager
                fsManager = VFS.getManager();

                // Set up the authenticator
                auth = new StaticUserAuthenticator(null, username, password);
                DefaultFileSystemConfigBuilder defaultConfigBuilder = DefaultFileSystemConfigBuilder.getInstance();
                defaultConfigBuilder.setUserAuthenticator(opts, auth);

                // Directly use the fully qualified SMB URI
                remoteFile = fsManager.resolveFile(remoteFilePath, opts);


                if (remoteFile.exists()) {
                    try (PDDocument document = PDDocument.load(remoteFile.getContent().getInputStream())) {
                        measurePdfSize(document);
                    }
                } else {
                    System.out.println("The specified file does not exist: " + remoteFilePath);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static void measurePdfSize(PDDocument document) {
            try {
                int pageCount = document.getNumberOfPages();
                System.out.println("Total number of pages: " + pageCount);

                double desiredWidthInches = 8.5;
                double desiredHeightInches = 11.00;
                int resolution = calculateResolution(document, desiredWidthInches, desiredHeightInches);

                for (int i = 0; i < pageCount; i++) {
                    PDFRenderer renderer = new PDFRenderer(document);
                    BufferedImage image = renderer.renderImageWithDPI(i, resolution);

                    int widthPixels = image.getWidth();
                    int heightPixels = image.getHeight();

                    double widthInches = widthPixels / (double) resolution;
                    double heightInches = heightPixels / (double) resolution;
                    System.out.println("Page " + (i + 1) + " size: " + widthInches + "x" + heightInches + " inches");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static int calculateResolution(PDDocument document, double desiredWidthInches, double desiredHeightInches) {
            int pageWidthPoints = (int) document.getPage(0).getMediaBox().getWidth();
            int pageHeightPoints = (int) document.getPage(0).getMediaBox().getHeight();
            int resolutionWidth = (int) (pageWidthPoints / desiredWidthInches);
            int resolutionHeight = (int) (pageHeightPoints / desiredHeightInches);
            return Math.min(resolutionWidth, resolutionHeight);



}}
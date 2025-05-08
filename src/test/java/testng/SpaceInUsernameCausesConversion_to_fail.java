package testng;


import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.JsonUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class SpaceInUsernameCausesConversion_to_fail extends TestBase {

    @Test(testName = "SOAP_Have multiple IMGs configured for the scheduler ", groups = {"Regression81"})
    public void Space_in_username_causes_conversion_to_fail() throws InterruptedException, IOException, SQLException {
        System.out.println("Test case name: " + testName);
        Map<String, String> data = JsonUtils.getDataBasedOnTestCaseName81(testName);
        assert data != null;
//            public static void main(String[] args) {



            String soapBody =
                    "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:rep=\"http://www.softlinx.com/ReplixFax\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <rep:SendFax>\n" +
                            "         <SendFaxInput>\n" +
                            "            <Authentication>\n" +
                            "               <Login>  faxsending </Login>\n" +  // <-- Trailing space here
                            "               <Password>softlinx</Password>\n" +
                            "               <Realm>acme1</Realm>\n" +
                            "            </Authentication>\n" +
                            "            <FaxRecipient>\n" +
                            "               <FaxNumber>12222222222</FaxNumber>\n" +
                            "            </FaxRecipient>\n" +
                            "            <Attachment>\n" +
                            "               <FileName>Pages_1.pdf</FileName>\n" +
                            "               <AttachmentContent>cid:154372974556</AttachmentContent>\n" +
                            "            </Attachment>\n" +
                            "            <CoverPageEnabled>false</CoverPageEnabled>\n" +
                            "            <TSI>TSI122344</TSI>\n" +
                            "         </SendFaxInput>\n" +
                            "      </rep:SendFax>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";

            Response response = given()
                    .header("Content-Type", "text/xml; charset=utf-8")
                    .header("SOAPAction", data.get("sendFaxSoapAction"))  // Required SOAPAction header
                    .body(soapBody).log().all()
                    .when()
                    .post(data.get("soapEndpoint"));

            System.out.println("Status Code: " + response.statusCode());
        System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("--------------------------------------------------------------------------------------");
            // Check for expected failure (adjust message accordingly)
            if (response.getBody().asString().contains("ConversionError") || response.getBody().asString().toLowerCase().contains("error")) {
                System.out.println("Test failed:SendFax failed due to trailing space in username.");
            } else {
                System.out.println("Test passed: SendFax succeeded as expected with trailing space in username");
            }
        }
    }



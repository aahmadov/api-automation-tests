package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;

import java.io.File;

public class RestRequestUtils {

    public static Response response;

    /*
     *@getWithRegistryBlankSetting
     * */
    public static Response getFaxWithRegistryBlankSetting(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialNewOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return request.contentType("multipart/form-data").when().get(url);
    }

    /*
     *@getWithValidFaxID
     * */
    public static Response getFax(String url, String credentials) {
        return createRequest(credentials).contentType("multipart/form-data").when().get(url);
    }

    public static Response getFax(String url) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return request.contentType("multipart/form-data").when().get(url);
    }

    public static Response getFaxsTSINewRestApi(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialNewInbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }

    public static Response getFaxsTSINewRestApi(String url, String credentials) {

        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }

    public static Response getStateCodes(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("Token");
       // byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
       // String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization " ,"Bearer "+ credentilas);
        return response = request.contentType("application/json").when().get(url);
    }


    public static Response getFaxsTSINewRestApi2(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialNewInbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);
    }
//new
    public static Response getFaxsTSINewRestApi2(String url, String credentials) {

        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);
    }

    public static Response getFaxsafterAllattempts(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialNewInbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }

    public static Response responseRecieveFax(String url,String credentials) {

        RequestSpecification request = RestAssured.given();

        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }


    /*
     *@getFaxStatusWithSendFailed
     * */
    public static Response getSendFailed_fax(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }

    /*
     *@getFaxStatusWithSendFailed
     * */
    public static Response getImage(String url, String pdf) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").param("FaxContentType", pdf).when().get(url);


    }

    /*
     *@getFaxwithImage
     * */
    public static Response get_fax_WithEMail(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }

    /*
     *@getFaxAfterSend
     * */
    public static Response get_FaxAfterResend(String url) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data").when().get(url);

    }

    /*
     *@getFaxNewCreatedFax
     * */
    public static Response getRecentCreatedFax(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutboundAnatoly");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return request.contentType("multipart/form-data").when().get(url);
    }

    public static Response getRecentCreatedFax(String url, String credentials) {
        return createRequest(credentials).contentType("multipart/form-data").when().get(url);
    }

    /*
     *@sendFaxwithNum+attach
     * */
    public static Response createFaxSingleNum(String url, File file, String faxNumber) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutboundAnatoly");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", faxNumber)
                .when()
                .post(url);

    }

    /*
     *@sendFaxwithNum+attach
     * */
    public static Response createFaxSingleNum(String url, File file, String faxNumber, String credentials) {
        return createRequest(credentials).contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", faxNumber)
                .when()
                .post(url);

    }

    /*
     *@createFaxwithMultipleElements
     * */
    public static Response createFaxmultipRecip(String url, File file, File file2, String recip1, String recip2) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file2)
                .multiPart("filename", file)
                .queryParam("CoverPageEnabled", true)
                .queryParam("CoverPageName", "cover.pdf")
                .queryParam("FaxRecipient", recip1)
                .queryParam("FaxRecipient", recip2)
                .when()
                .post(url);

    }

    /*
     *@sendFaxwithNoAttachement
     * */
    public static Response createFaxNoattach(String url, String faxNumber) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .queryParam("", "")
                .queryParam("FaxNumber", faxNumber)
                .post(url);
    }

    /*
     *@createFaxwithMultipleNum+attach
     * */
    public static Response FaxwithTwoNumtwoAttach(String url, File file, File file2, String faxNumber,
                                                  String faxNumber2) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file2)
                .multiPart("filename", file)
                .queryParam("FaxNumber", faxNumber)
                .queryParam("FaxNumber", faxNumber2)
                .when().post(url);
    }

    public static Response FaxNo_Attachment(String url, String faxNumber) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .queryParam("FaxNumber", faxNumber)

                .when().post(url);

    }

    /*
     *@sendRequestWithNoNumber
     * */
    public static Response faxWithNoNumber(String url, File file, String emptyFaxNumber) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutboundAnatoly");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);

        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", emptyFaxNumber)
                .when().post(url);
    }

    /*
     *@sendRequestWithNoNumber
     * */
    public static Response faxWithNoNumber(String url, File file, String emptyFaxNumber, String credentials) {
        return createRequest(credentials).contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", emptyFaxNumber)
                .when().post(url);
    }


    public static Response createFaxSingleNum3(String url, File file, String faxNumber, String credentials) {
        return createRequest(credentials).contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", faxNumber)
                .when()
                .post(url);

    }


    public static Response sendFaxWithRecipent_details(String url, File file, String faxRecipientD) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);

        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxRecipient", faxRecipientD)
                .when().post(url);

    }
    public static Response sendFaxWithSwagger(String url, File file, String data) {
        RequestSpecification request = RestAssured.given();
        String credentials = ConfigReader.getProperty("Token");
        request.header("Authorization ", "Bearer " + credentials);

        return response = request.contentType("multipart/form-data")
                .multiPart("loaFile", file)
                .multiPart("billFile", file)
                .queryParam("data",  data)
                .when().post(url);

    }
    public static Response sendFaxWithRecipent_details(String url, File file, String faxRecipientD, String credentials) {
        return createRequest(credentials).contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxRecipient", faxRecipientD)
                .when().post(url);
    }

    public static Response submitFaxWithMulRecip(String url, String faxD, String FaxD2, File file, File file2) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("creadentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("RecipientName", faxD)
                .multiPart("filename", file2)
                .multiPart("RecipientName", FaxD2)
                .when().post(url);

    }

    public static Response recendaFax(String url, File file, String faxNumber) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", faxNumber)
                .when()
                .post(url);


    }

    public static Response sendSimpleFax(String url, File file, String number) {
        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);
        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return response = request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", number)
                .when()
                .post(url);

    }

    /*
     *@sendRequestWithNoTSI
     * */
    public static Response sendFaxWithTSI(String url, File readfile, String faxnumb) {
        return createRequest(ConfigReader.getProperty("credentialNewOutbound"))
                .contentType("multipart/form-data")
                .multiPart("filename", readfile)
                .queryParam("FaxNumber", faxnumb)
                .when()
                .post(url);

    }

    public static Response sendFaxWithNewTSI(String url, File filePage, String faxnumb) {
        return createRequest(ConfigReader.getProperty("credentialOutbound"))
                .contentType("multipart/form-data")
                .multiPart("filename", filePage)
                .queryParam("FaxNumber", faxnumb)
                .when()
                .post(url);
    }
    public static Response sendFaxWithNewTSI(String url, File file20Page, String faxnumb, String credentials) {
        return createRequest(credentials)
                .contentType("multipart/form-data")
                .multiPart("filename", file20Page)
                .queryParam("FaxNumber", faxnumb)
                .when()
                .post(url);
    }
        public static Response PostCalltoCreateLOA(String url, String body) {
            RequestSpecification request = RestAssured.given();
            String credentials = ConfigReader.getProperty("Token");
            request.header("Authorization ", "Bearer " + credentials);
            return response = request
                    .contentType("application/json")
                    .body(body)
                    .when()
                    .post(url).andReturn();
    }
    public static Response sendFaxWithNewTSI2(String url, File file20Page, String faxnumb, String credentials) {
        return createRequest(credentials)
                .contentType("multipart/form-data")
                .multiPart("filename", file20Page)
                .queryParam("FaxNumber", faxnumb)
                .when()
                .post(url);
    }
    public static Response submitFaxwithBlankRegistry(String url, File randomFile, String faxnumb) {
        return response = createRequest(ConfigReader.getProperty("credentialNewOutbound"))
                .contentType("multipart/form-data")
                .multiPart("filename", randomFile)
                .queryParam("FaxNumber", faxnumb)
                .when().log().all()
                .post(url);
    }

    public static Response putScenario(String url) {
        return response = createRequest()
                .when().log().all()
                .put(url);
    }

    private static RequestSpecification createRequest(String credentials) {
        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialsFaxsending = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialsFaxsending);
        return request;
    }

    private static RequestSpecification createRequest() {
        RequestSpecification request = RestAssured.given();
        return request;
    }
}
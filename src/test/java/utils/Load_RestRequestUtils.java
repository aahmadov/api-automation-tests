package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.util.Map;

public class Load_RestRequestUtils {

    public static Response sendFax_loadTest(String url, File file, String number) {
        return createRequest(ConfigReader.getProperty("credentialOutbound"))
        		.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", number)
                .when()
                .post(url);

    }

    public static Response sendFax_loadTest(Map<String, Object> data) {
        return createRequest(ConfigReader.getProperty("credentialOutbound"))
        		.contentType("multipart/form-data")
                .multiPart("filename", (File)data.get("filename"))
                .queryParam("FaxNumber", data.get("FaxNumber").toString())
                .queryParam("CoverPageEnabled", Boolean.parseBoolean(data.get("coverPageEnabled").toString()))
                .when().log().all()
                .post(data.get("url").toString());
    }

    public static Response send_more_Fax_loadTest(String url, File file, String number) {

    	 return createRequest(ConfigReader.getProperty("credentialOutbound"))
                .contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", number)
                .when()
                .post(url);
    }

    public static Response sendthree_Fax_loadTest(String url, File file, String number) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialOutbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAcme = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAcme);
        return request.contentType("multipart/form-data")
                .multiPart("filename", file)
                .queryParam("FaxNumber", number)
                .when()
                .post(url);
    }

    public static Response getCreatedFaxForLoad1(String url) {

        RequestSpecification request = RestAssured.given();
        String credentilas = ConfigReader.getProperty("credentialNewInbound");
        byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
        String encodedCreadentialForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCreadentialForAdmin);
        return request.contentType("multipart/form-data").when().get(url);
    }

    private static RequestSpecification createRequest(String credentials) {
        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialsForAdmin = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialsForAdmin);
        return request;
    }
}


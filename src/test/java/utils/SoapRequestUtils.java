package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;

public class SoapRequestUtils {

    public static Response soapInboundFaxWithCoverPage(String url, String body, String credentials, String soapAction) {
        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAcme = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAcme);
        request.header("SOAPAction", soapAction);

        return request.contentType("text/xml")
                .body(body)
                .when()
                .post(url).then().extract().response();
    }
    public static Response soapInboundFaxWithCoverPage81(String url, String body, String credentials, String soapAction) {
        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAcme = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAcme);
        request.header("SOAPAction", soapAction);

        return request.contentType("text/xml")
                .body(body)
                .when()
                .post(url).then().extract().response();
    }
    public static Response soapInboundFaxWithCoverPage46(String url, String body, String credentials, String soapAction) {
        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAcme = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAcme);
        request.header("SOAPAction", soapAction);

        return request.contentType("text/xml")
                .body(body)
                .when()
                .post(url).then().extract().response();
    }
    public static Response soapInboundFaxWithCoverPageIMG(String url, String body, String credentials, String soapAction) {
        RequestSpecification request = RestAssured.given();
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes());
        String encodedCredentialForAcme = new String(encodedCredentials);

        request.header("Authorization ", "Basic " + encodedCredentialForAcme);
        request.header("SOAPAction", soapAction);

        return request.contentType("text/xml")
                .body(body)
                .when()
                .post(url).then().extract().response();
    }

}

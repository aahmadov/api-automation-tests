package utils;

import java.io.File;

import org.apache.commons.codec.binary.Base64;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Second_RestRequestUtils {
	
	
	public static Response response;
	public static final Second_RestRequestUtils shared = new Second_RestRequestUtils();
	
	
	public static Response inbound_FaxwithCoverPage(String url,File file,String number) {

		RequestSpecification request = RestAssured.given();	
		String credentilas = ConfigReader.getProperty("credentialNewOutbound");
		byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.multiPart("filename", file)
						.queryParam("FaxNumber",number)
						.queryParam("CoverPageEnabled", true)
						.when()
						.post(url);

	}
	public static Response getInboundWithCoverPage(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialNewInbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
	}
}

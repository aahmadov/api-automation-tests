package utils;

import java.io.File;

import org.apache.commons.codec.binary.Base64;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Load_RestRequestUtils {
	
	public static Response response;
	public static final Load_RestRequestUtils shared = new Load_RestRequestUtils();
	
	public static Response sendFax_loadTest(String url,File file, String number) {

		RequestSpecification request = RestAssured.given();	
		String credentilas = ConfigReader.getProperty("credentialOutbound");
		byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.multiPart("filename", file)
						.queryParam("FaxNumber",number)		
						.when()
						.post(url);
	
	}
	public static Response send_more_Fax_loadTest(String url,File file, String number) {

		RequestSpecification request = RestAssured.given();	
		String credentilas = ConfigReader.getProperty("credentialOutbound");
		byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.multiPart("filename", file)
						.queryParam("FaxNumber",number)		
						.when()
						.post(url);
	

}
	
	public static Response sendthree_Fax_loadTest(String url,File file, String number) {

		RequestSpecification request = RestAssured.given();	
		String credentilas = ConfigReader.getProperty("credentialOutbound");
		byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.multiPart("filename", file)
						.queryParam("FaxNumber",number)		
						.when()
						.post(url);
}
	

}


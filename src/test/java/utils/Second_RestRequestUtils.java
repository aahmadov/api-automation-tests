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
	public static Response getInboundWithCoverPage1(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialNewInbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
	}
	public static Response getOutboundWithCoverPage(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialNewOutbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
	
}
	public static Response faxWith50Pages(String url,File file,String number) {

		RequestSpecification request = RestAssured.given();	
		String credentilas = ConfigReader.getProperty("credentialNewOutbound");
		byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.multiPart("filename", file)
						.queryParam("FaxNumber",number)		
						.when()
						.post(url);
	
	}
	public static Response Outbound_getCall50Page(String url) {

		RequestSpecification request = RestAssured.given();	
		String outboundCredantials = ConfigReader.getProperty("credentialNewOutbound");
		byte[] encodedCredentials =Base64.encodeBase64(outboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
}
	public static Response getInbound50Page(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialNewInbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
}
	
	public static Response faxWith100Pages(String url,File file,String number) {

		RequestSpecification request = RestAssured.given();	
		String credentilas = ConfigReader.getProperty("credentialNewOutbound");
		byte[] encodedCredentials = Base64.encodeBase64(credentilas.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.multiPart("filename", file)
						.queryParam("FaxNumber",number)		
						.when()
						.post(url);
	
	}
	
	public static Response outbound100PageValidation(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialNewOutbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
}
	public static Response Inbound100PageValidation(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialNewInbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
}
	public static Response clumsyOutbound_Fax(String url,File file,String number) {

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
	
	public static Response getCall_clumsy_65Validation(String url) {

		RequestSpecification request = RestAssured.given();	
		String inboundCredantials = ConfigReader.getProperty("credentialOutbound");
		byte[] encodedCredentials =Base64.encodeBase64(inboundCredantials.getBytes());
	    String encodedCreadentialForAcme =new String (encodedCredentials);
		
		 request.header("Authorization ", "Basic "+encodedCreadentialForAcme);
				return response=request.contentType("multipart/form-data")
						.when()
						.get(url);
}
	
	
	public static Response Inbound_getCall_clumsy_65Validation(String url) {

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

package Post_call;

import org.apache.http.HttpStatus;

import org.junit.Test;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import RequestBuilder.FaxRequestBuilder;
import io.restassured.response.Response;
import org.mindrot.jbcrypt.BCrypt;
import utils.ConfigReader;
import utils.FileReader;
import utils.RestRequestUtils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

public class Send_simpleFax {
	

	//@Test	
	public void FaxwithNum() throws JacksonException {
		
    Response response= RestRequestUtils.createFaxSingleNum(ConfigReader.getProperty("post_call_Url"),FileReader.readfile("Pages"),"446875558");
			
	assertEquals(response.statusCode(),HttpStatus.SC_CREATED);
		 
	}
	

	@Test
	public void Fax_no_attachment() {

		Response response = RestRequestUtils.createFaxNoattach(ConfigReader.getProperty("post_call_Url"),
				"12-33-44-55");
		assertEquals(response.getStatusCode(), 201);
		response.prettyPrint();
	}
	
	
	
	//@Test
	   public void submitFaxWithMulRecip() throws IOException  {
	
		   Response resp = RestRequestUtils.createFaxmultipRecip(ConfigReader.getProperty("post_call_Url"),
				   FileReader.readfile("Pages"), FileReader.readfile("Pages_1"), 
				   ConfigReader.getProperty("Recipent_data1"), ConfigReader.getProperty("Recipent_data2"));
		   resp.asPrettyString();
		  
		   assertEquals(resp.getStatusCode(),HttpStatus.SC_CREATED); 	   
	   }
	
	
	
	//@Test
	   public void submitFaxtwoNumtwoAttach() throws IOException  {
		  
		Response response=RestRequestUtils.FaxwithTwoNumtwoAttach(ConfigReader.getProperty("post_call_Url"),FileReader.readfile("Pages"),FileReader.readfile("Pages_1"),"13-56-78-89","24-35-37-67");
	     response.prettyPrint();
		assertEquals(response.getStatusCode(),201);
		
		}



		@Test
	public void pinTest(){

			// Hashing a password
			String password = "faxsending@smoke:Softlinx1!";
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			System.out.println(hashedPassword);
// Checking a password

			//$2a$10$wbJHCyRAc5HeU0vsOXQqJuqC70PNNpm29AmJQEpc0mbqdSo3wkLyq
           //'$pbkdf2sha512$4000$h0nv/vGtm1wr$xccvhMtBGVlDM9QAjWEKgv47BLfQ1xWZWiqJKVkcx/Jx9kB2FAYUuVmjZuHjbMRHI137AzUJyjHFpekU8t4qmA=='

			String inputPassword = "";
			if (BCrypt.checkpw(inputPassword, hashedPassword)) {
				System.out.println("Password is correct");
			} else {
				System.out.println("Password is incorrect");
			}
		}
	
	}


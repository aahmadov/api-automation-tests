package Get_Call_Suit;

import static org.junit.Assert.assertEquals;

import org.apache.http.HttpStatus;
import org.junit.Ignore;
import org.junit.Test;

import io.restassured.response.Response;
import utils.ConfigReader;
import utils.RestRequestUtils;

public class Get_byFaxNumber {
    @Ignore
	@Test
	public void faxById() {
		
		Response resp = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url")+ConfigReader.getProperty("FaxNumber"));
		resp.prettyPrint();
         	assertEquals(resp.statusCode(),HttpStatus.SC_OK);	
		 String dc= resp.then().extract().path("FaxInfo[0].FaxStatus");
		 assertEquals(dc,"sent");
		
	}
	@Ignore
	@Test
	public void GetBy_ValidID() {

		Response resp = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url")+ConfigReader.getProperty("valid_ID"));
		resp.prettyPrint();
         	assertEquals(resp.statusCode(),HttpStatus.SC_OK);	
		 String dc= resp.then().extract().path("FaxInfo[0].FaxStatus");
		 assertEquals(dc,"sent");
	
		
	}
	
	@Test
	public void GetBy_InvalidID() {

		Response resp = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url")+ConfigReader.getProperty("invalid_ID"));
		resp.prettyPrint();
         	assertEquals(resp.statusCode(),HttpStatus.SC_OK);	
		 String dc= resp.then().extract().path("FaxInfo[0].FaxStatus");
		 assertEquals(dc,"sendFailed");
	
		
	}
	@Ignore
	@Test
	public void GetBy_sentStatus() {

		Response resp = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url")+ConfigReader.getProperty("successfull_faxes"));
		resp.prettyPrint();
         	assertEquals(resp.statusCode(),HttpStatus.SC_OK);	
		 String dc= resp.then().extract().path("FaxInfo[0].FaxStatus");
		 assertEquals(dc,"sent");
	
		
	}
	@Ignore
	@Test
	public void GetBy_sentFailed() {

		Response resp = RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url")+ConfigReader.getProperty("unsuccessful"));
		resp.prettyPrint();
         	assertEquals(resp.statusCode(),HttpStatus.SC_OK);	
		 String dc= resp.then().extract().path("FaxInfo[0].FaxStatus");
		 assertEquals(dc,"sendFailed");
	
}
	
}



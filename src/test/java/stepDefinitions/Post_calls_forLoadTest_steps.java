
	package stepDefinitions;

	import static org.junit.Assert.assertEquals;
	import com.jayway.jsonpath.JsonPath;

	import io.cucumber.java.en.*;
	import io.restassured.response.Response;
	import utils.ConfigReader;
	import utils.ExcelUtility;
	import utils.FileReader;
	import utils.Load_RestRequestUtils;
import utils.TSI_dataFor_load;

	public class Post_calls_forLoadTest_steps {

		Response response;
		
		@Given("I want submit new post call with one page")
		public void i_want_submit_new_post_call_with_one_page() {
			
			String firstLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendFax_loadTest(ConfigReader.getProperty("outbound_URl_65")+firstLoadTest_TSI,FileReader.readfile("Pages_1")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite( ExcelPath,firstLoadTest_TSI,8,8);
		   
		   System.out.println(firstLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");

		}

		@Given("I validate of status code is {int}")
		public void i_validate_of_status_code_is(int expectedStatusCode) {
		 int acutalStatusCode= response.statusCode();
		 response.asString();
		 int FaxId=JsonPath.read(response.asString(),"$.FaxInfo[0].FaxId");
		 assertEquals(acutalStatusCode,expectedStatusCode);
		 System.out.println("**** the new generated FaxId for PostCall is "+"**"+FaxId+"**");
		 System.out.println("**** the actual status code is "+"**"+acutalStatusCode+"**");
		}

		@Given("I want submit new post call with multiple pages")
		public void i_want_submit_new_post_call_with_multiple_pages() {
			String second_LoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
			response=Load_RestRequestUtils.send_more_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+second_LoadTest_TSI,FileReader.readfile("Pages")
					   ,ConfigReader.getProperty("FaxN"));
			
			 ExcelUtility.createExcelAndWrite( ExcelPath,second_LoadTest_TSI,7,7);
			   
			   System.out.println(second_LoadTest_TSI);
			   System.out.println(ExcelPath);
			   System.out.println("------------------------------------------------------------------------");
				
				System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
				System.out.println("******* "+(FileReader.readfile("Pages_1")));
				System.out.println("******* "+ConfigReader.getProperty("FaxN"));
				System.out.println("------------------------------------------------------------------------");
		}
		
		@Given("submit new request with new page")
		public void submit_new_request_with_new_page() {
			String thirdLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+thirdLoadTest_TSI,FileReader.readfile("Pages3")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite( ExcelPath,thirdLoadTest_TSI,6,6);
		   
		   System.out.println(thirdLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");

		}
		
		@Given("submit new request with five page")
		public void submit_new_request_with_five_page() {
			
			String fourthLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+fourthLoadTest_TSI,FileReader.readfile("Pages")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite( ExcelPath,fourthLoadTest_TSI,5,5);
		   
		   System.out.println(fourthLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");
		}
		
		@Given("submit new request with six page")
		public void submit_new_request_with_six_page() {
			String fifthLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+fifthLoadTest_TSI,FileReader.readfile("Pages_5")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite( ExcelPath,fifthLoadTest_TSI,4,4);
		   
		   System.out.println(fifthLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");
		}
		
		@Given("submit new request with seven page")
		public void submit_new_request_with_seven_page() {
			String sixthLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+sixthLoadTest_TSI,FileReader.readfile("Pages_6")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite5( ExcelPath,sixthLoadTest_TSI,4,4);
		   
		   System.out.println(sixthLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------"); 
		}
		@Given("submit new request with eight page")
		public void submit_new_request_with_eight_page() {
			String seventhLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+seventhLoadTest_TSI,FileReader.readfile("Pages_7")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite4( ExcelPath,seventhLoadTest_TSI,3,3);
		   
		   System.out.println(seventhLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");
		}
		
		@Given("submit new request with nine page")
		public void submit_new_request_with_nine_page() {
			String ninethLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String ExcelPath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+ninethLoadTest_TSI,FileReader.readfile("Pages_8")
				   ,ConfigReader.getProperty("FaxN"));
		   
		   ExcelUtility.createExcelAndWrite3( ExcelPath,ninethLoadTest_TSI,2,2);
		   
		   System.out.println(ninethLoadTest_TSI);
		   System.out.println(ExcelPath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");
		}
		
		@Given("submit new request with ten page")
		public void submit_new_request_with_ten_page() throws Exception {
			String tenthLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String FilePath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\responseBody\\writeData.Json";
			
		   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+tenthLoadTest_TSI,FileReader.readfile("Pages_8")
				   ,ConfigReader.getProperty("FaxN"));
		   
		  // ExcelUtility.createExcelAndWrite2( ExcelPath,tenthLoadTest_TSI,1,1);
		   TSI_dataFor_load.writeTofile(tenthLoadTest_TSI,FilePath);
		   
		   System.out.println(tenthLoadTest_TSI);
		   System.out.println(FilePath);
		   System.out.println("------------------------------------------------------------------------");
			
			System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
			System.out.println("******* "+(FileReader.readfile("Pages_1")));
			System.out.println("******* "+ConfigReader.getProperty("FaxN"));
			System.out.println("------------------------------------------------------------------------");
		}
		
		@Given("submit new request with different page count")
		public void submit_new_request_with_different_page_count() throws Exception {
			String elevenLoadTest_TSI=FileReader.randomNumberFor_TSI();
			String FilePath="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\responseBody\\writeData.Json";
			
			   response=Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65")+elevenLoadTest_TSI,FileReader.readfile("Pages_8")
					   ,ConfigReader.getProperty("FaxN"));
			
			   //ExcelUtility.createExcelAndWrite( ExcelPath,elevenLoadTest_TSI,0,0);
			   TSI_dataFor_load.writeTofile(elevenLoadTest_TSI,FilePath);
			   
			   System.out.println(elevenLoadTest_TSI);
			   System.out.println(FilePath);
			   System.out.println("------------------------------------------------------------------------");
				
				System.out.println("******* "+ConfigReader.getProperty("outbound_URl_65"));
				System.out.println("******* "+(FileReader.readfile("Pages_1")));
				System.out.println("******* "+ConfigReader.getProperty("FaxN"));
				System.out.println("------------------------------------------------------------------------");
		}
		
	
}
	



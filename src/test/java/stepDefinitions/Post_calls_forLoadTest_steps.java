package stepDefinitions;

import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import utils.ConfigReader;
import utils.ExcelUtility;
import utils.FileReader;
import utils.Load_RestRequestUtils;

import static org.junit.Assert.assertEquals;

public class Post_calls_forLoadTest_steps {

    Response response;

    @Given("I want submit new post call with one page")
    public void i_want_submit_new_post_call_with_one_page() {

        String firstLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendFax_loadTest(ConfigReader.getProperty("outbound_URl_65") + firstLoadTest_TSI, FileReader.readfile("Pages_1")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, firstLoadTest_TSI);

        System.out.println(firstLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");

    }

    @Given("I validate of status code is {int}")
    public void i_validate_of_status_code_is(int expectedStatusCode) {
        int acutalStatusCode = response.statusCode();
        response.asString();
        int FaxId = JsonPath.read(response.asString(), "$.FaxInfo[0].FaxId");
        assertEquals(acutalStatusCode, expectedStatusCode);
        System.out.println("**** the new generated FaxId for PostCall is " + "**" + FaxId + "**");
        System.out.println("**** the actual status code is " + "**" + acutalStatusCode + "**");
    }

    @Given("I want submit new post call with multiple pages")
    public void i_want_submit_new_post_call_with_multiple_pages() {
        String second_LoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
        response = Load_RestRequestUtils.send_more_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + second_LoadTest_TSI, FileReader.readfile("Pages")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, second_LoadTest_TSI);

        System.out.println(second_LoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }

    @Given("submit new request with new page")
    public void submit_new_request_with_new_page() {
        String thirdLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";
        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + thirdLoadTest_TSI, FileReader.readfile("Pages3")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, thirdLoadTest_TSI);

        System.out.println(thirdLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");

    }

    @Given("submit new request with five page")
    public void submit_new_request_with_five_page() {
        String fourthLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + fourthLoadTest_TSI, FileReader.readfile("Pages")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, fourthLoadTest_TSI);

        System.out.println(fourthLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }

    @Given("submit new request with six page")
    public void submit_new_request_with_six_page() {
        String fifthLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + fifthLoadTest_TSI, FileReader.readfile("Pages_5")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, fifthLoadTest_TSI);

        System.out.println(fifthLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }

    @Given("submit new request with seven page")
    public void submit_new_request_with_seven_page() {
        String sixthLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + sixthLoadTest_TSI, FileReader.readfile("Pages_6")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, sixthLoadTest_TSI);

        System.out.println(sixthLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }

    @Given("submit new request with eight page")
    public void submit_new_request_with_eight_page() {
        String seventhLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + seventhLoadTest_TSI, FileReader.readfile("Pages_7")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, seventhLoadTest_TSI);

        System.out.println(seventhLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }

    @Given("submit new request with nine page")
    public void submit_new_request_with_nine_page() {
        String ninethLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + ninethLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, ninethLoadTest_TSI);

        System.out.println(ninethLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    /*
  	 *@submittenthPostCall
  	 * */
    @Given("submit new request with ten page")
    public void submit_new_request_with_ten_page() {
        String tenthLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + tenthLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, tenthLoadTest_TSI);

        System.out.println(tenthLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }

    @Given("submit new request with different page count")
    public void submit_new_request_with_different_page_count() {
        String elevenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + elevenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, elevenLoadTest_TSI);

        System.out.println(elevenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    
    @Given("I will submit new post call")
    public void i_will_submit_new_post_call() {
    	String twelveLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twelveLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twelveLoadTest_TSI);

        System.out.println(twelveLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    
    @Given("I will submit new post call with new attachment")
    public void i_will_submit_new_post_call_with_new_attachment() {
    	String thirteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + thirteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, thirteenLoadTest_TSI);

        System.out.println(thirteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    
    /*
	 *@submitfourteenPostCall
	 * */
    @Given("I will submit new post call with new attachment3")
    public void i_will_submit_new_post_call_with_new_attachment3() {
    	String fourteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + fourteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, fourteenLoadTest_TSI);

        System.out.println(fourteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    @Given("I will submit new post call with new attachment4")
    public void i_will_submit_new_post_call_with_new_attachment4() {
    	String fifteenteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + fifteenteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, fifteenteenLoadTest_TSI);

        System.out.println(fifteenteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");

}
    @Given("I will submit new post call with new attachment5")
    public void i_will_submit_new_post_call_with_new_attachment5() {
    	String fifteenteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + fifteenteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, fifteenteenLoadTest_TSI);

        System.out.println(fifteenteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------"); 
    
}
    
    @Given("I will submit new post call with new attachment6")
    public void i_will_submit_new_post_call_with_new_attachment6() {
    	String sixteenteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + sixteenteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, sixteenteenLoadTest_TSI);

        System.out.println(sixteenteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------"); 
    
}
    @Given("I will submit new post call with new attachment7")
    public void i_will_submit_new_post_call_with_new_attachment7() {
    	String seventeenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + seventeenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, seventeenLoadTest_TSI);

        System.out.println(seventeenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------"); 
    
} 
    @Given("I will submit new post call with new attachment8")
    public void i_will_submit_new_post_call_with_new_attachment8() {
    	String eighteenteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + eighteenteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, eighteenteenLoadTest_TSI);

        System.out.println(eighteenteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------"); 
    
}
    @Given("I will submit new post call with new attachment9")
    public void i_will_submit_new_post_call_with_new_attachment9() {
    	String nineteenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + nineteenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, nineteenLoadTest_TSI);

        System.out.println(nineteenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    
}
    /*
   	 *@submittwentyPostCall
   	 * */
    @Given("I will submit new post call with new attachment10")
    public void i_will_submit_new_post_call_with_new_attachment10() {
    	String twentyLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentyLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentyLoadTest_TSI);

        System.out.println(twentyLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    
} 
   
    
    @Given("I will submit post call with new parameters1")
    public void i_will_submit_post_call_with_new_parameters1() {
    	String twentyoneLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentyoneLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentyoneLoadTest_TSI);

        System.out.println(twentyoneLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    @Given("I will submit post call with new parameters2")
    public void i_will_submit_post_call_with_new_parameters2() {
    	String twentytwoLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentytwoLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentytwoLoadTest_TSI);

        System.out.println(twentytwoLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
}
    @Given("I will submit post call with new parameters3")
    public void i_will_submit_post_call_with_new_parameters3() {
    	String twentythreeLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentythreeLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentythreeLoadTest_TSI);

        System.out.println(twentythreeLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");  
}
    @Given("I will submit post call with new parameters4")
    public void i_will_submit_post_call_with_new_parameters4() {
    	String twentyfourLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentyfourLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentyfourLoadTest_TSI);

        System.out.println(twentyfourLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");  
}
    @Given("I will submit post call with new parameters5")
    public void i_will_submit_post_call_with_new_parameters5() {
    	String twentyfiveLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentyfiveLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentyfiveLoadTest_TSI);

        System.out.println(twentyfiveLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    @Given("I will submit post call with new parameters6")
    public void i_will_submit_post_call_with_new_parameters6() {
    	String twentysixLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentysixLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentysixLoadTest_TSI);

        System.out.println(twentysixLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
        
}
    @Given("I will submit post call with new parameters7")
    public void i_will_submit_post_call_with_new_parameters7() {
    	String twentysevenLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentysevenLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentysevenLoadTest_TSI);

        System.out.println(twentysevenLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
    }
    @Given("I will submit post call with new parameters8")
    public void i_will_submit_post_call_with_new_parameters8() {
    	String twentyeightLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentyeightLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentyeightLoadTest_TSI);

        System.out.println(twentyeightLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
}
    @Given("I will submit post call with new parameters9")
    public void i_will_submit_post_call_with_new_parameters9() {
    	String twentynineLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + twentynineLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, twentynineLoadTest_TSI);

        System.out.println(twentynineLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");
}
    /*
  	 *@submitthirtyPostCall
  	 * */
    @Given("I will submit post call with new parameters10")
    public void i_will_submit_post_call_with_new_parameters10() {
    	String thirtyLoadTest_TSI = FileReader.randomNumberFor_TSI();
        String ExcelPath = "C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\dataFile\\testData.xlsx";

        response = Load_RestRequestUtils.sendthree_Fax_loadTest(ConfigReader.getProperty("outbound_URl_65") + thirtyLoadTest_TSI, FileReader.readfile("Pages_8")
                , ConfigReader.getProperty("FaxN"));

        ExcelUtility.createExcelAndWrite(ExcelPath, thirtyLoadTest_TSI);

        System.out.println(thirtyLoadTest_TSI);
        System.out.println(ExcelPath);
        System.out.println("------------------------------------------------------------------------");

        System.out.println("******* " + ConfigReader.getProperty("outbound_URl_65"));
        System.out.println("******* " + (FileReader.readfile("Pages_1")));
        System.out.println("******* " + ConfigReader.getProperty("FaxN"));
        System.out.println("------------------------------------------------------------------------");  
    }
}
//package stepDefinitions;
//
//import java.io.File;
//
//import io.cucumber.java.en.*;
//import utils.ConfigReader;
//import utils.FileReader;
//import utils.ReceiveEmail;
//import utils.SendEmail;
//
//public class EmailToFax {
//	   String host = "imap.gmail.com";
//	    String mailStoreType = "imap";
//	    String username = "Auto@softlinx.com";
//	    String password = "softlinx";
//	    String from = "no-reply@rpxqa.com";
//	    String successfulSubject = "Inbound Fax to 11111111111, Unassigned Faxes (Received)";
//	
// String bodyMessage =ConfigReader.getProperty("bodyMessage");
// File attach =FileReader.readfile("1page");
// File attachment = FileReader.randomFileFromFolder();
// String to = FileReader.randomFaxNumberEmailToFax();
// String to = "11111111111@auto1.rpxqa.com";
//
// //String to2 = "John Smith:Acme:Manager:15554569876:15556789900"+""+"15554569876@demo.rpxfax.com"; 
//
//
//  
//	@Given("I want to send an EmailToFax message")
//	public void i_want_to_send_an_EmailToFax_message() {
//		
//		
//		SendEmail.sendFromGMail(to, bodyMessage,attachment);
//	}
//
//
//@Then("I validate receive email")
//public void i_validate_receive_email() throws InterruptedException {
// 
//    String failedSubject = "Inbound Fax to 11111111111, Unassigned Faxes (Receive Failed)";
//    String sendFailedSubject = "Outgoing fax to 11111111111 (Send Failed)";
//	
//	ReceiveEmail.receiveEmail(host, mailStoreType, username, password, from, successfulSubject);
//   
//}
//
//
//}


package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import utils.*;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class EmailToFaxSteps {
    String bodyMessage = ConfigReader.getProperty("bodyMessage");
    String from = "no-reply@rpxqa.com";
    
    //String to2 = "John Smith:Acme:Manager:15554569876:15556789900"+""+"15554569876@demo.rpxfax.com";


    @Given("I want to send an EmailToFax message and verify")
    public void i_want_to_send_an_EmailToFax_message(DataTable dataTable) throws Exception {
        Map<String, String> data = dataTable.transpose().asMaps().get(0);
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        // loop to send email based on of times provided in the scenario
        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("Iteration time of sendmail in the loop :" + i);
            
            String faxNumber;
            String to;
            //Checkin whether to contains faxnumber in scenario,,,,,,,
            if (data.get("to").contains("@")) {
                //Get faxnumber if already exist in the 'to' field
                faxNumber = data.get("to").split("@")[0];
                to = data.get("to");
            } else {
                //If faxnumber is blank, get the fax number from file randomly and generate 'to' address
            	faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));
            	to = faxNumber + "@" + data.get("to"); // result: 1234567891@auto1.rpxqa.com
            }

            //Read the subject string and add the faxnumber into the string
            String subject = String.format(ConfigReader.getProperty(data.get("subject")), faxNumber); // result: Inbound Fax to 11111111111, Unassigned Faxes (Received)
            File file =  FileReader.getFileUsingPageSize(data.get("pageSize"));
            System.out.println("to: " + to);
            System.out.println("file: " + file.getAbsolutePath());

            SendEmail.sendFromGMail(to, bodyMessage, file);
            Boolean result = ReceiveEmail.receiveEmail(from, subject);
            assertTrue(result);
        }
    }
}
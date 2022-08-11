package stepDefinitions;

import java.io.File;

import io.cucumber.java.en.Given;
import utils.ConfigReader;
import utils.FileReader;
import utils.Mailing;

public class EmailToFax {
 String bodyMessage =ConfigReader.getProperty("bodyMessage");
 File fileLocation = FileReader.randomFileFromFolder();
 String to = FileReader.randomFaxNumberEmailToFax();
	
	@Given("I want to send an EmailToFax message")
	public void i_want_to_send_an_EmailToFax_message() {
		
		
		Mailing.sendFromGMail(to, bodyMessage, fileLocation);
	}



}

package stepDefinitions;

import java.io.File;

import io.cucumber.java.en.Given;
import utils.ConfigReader;
import utils.FileReader;
import utils.SendEmail;

public class EmailToFax {
 String bodyMessage =ConfigReader.getProperty("bodyMessage");
 //File attach =FileReader.readfile("1page");
 File attachment = FileReader.randomFileFromFolder();
 //String to = FileReader.randomFaxNumberEmailToFax();
 String to = "11111111111@auto1.rpxqa.com";
 //String to2 = "John Smith:Acme:Manager:15554569876:15556789900"+""+"15554569876@demo.rpxfax.com"; 


  
	@Given("I want to send an EmailToFax message")
	public void i_want_to_send_an_EmailToFax_message() {
		
		
		SendEmail.sendFromGMail(to, bodyMessage,attachment);
	}



}

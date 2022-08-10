package stepDefinitions;

import io.cucumber.java.en.Given;
import utils.Mailing;

public class EmailToFax {
	 String bodyMesage= "Hello ,Good Day! \n"
             +"\n"
             +"All Emails have been sent.Please find the attached fax for your reference .\n"
             +"\n"
             +"Thanks,\n"
             +"Abbas Aydinoglu";
 String fileLocation ="C:\\Users\\Administrator\\git\\fs_test2\\src\\test\\resources\\requestBody\\1page.pdf";
 String to = "13392120277@demo.rpxtest.com";
	
	@Given("I want to send an EmailToFax message")
	public void i_want_to_send_an_EmailToFax_message() {
		
		
		Mailing.sendFromGMail(to, bodyMesage, fileLocation);
	}



}

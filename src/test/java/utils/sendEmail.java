package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import cucumber.api.java.it.Date;

public class sendEmail {

//      private static String USER_NAME = "abbasaydinoglu7";  // GMail user name (just the part before "@gmail.com")
//	    private static String PASSWORD = "devcxtbghskekjkc"; // GMail password
//	    private static String RECIPIENT = "abbas@softlinx.com";

	static String key, data, from, to, password,host;
	static String[] AllToAddress;

	public void getData() throws IOException {

		String filepPath = "/src/test/resources/config/apiCreadentials.properties";
		File file = new File(filepPath);
		FileInputStream Input = new FileInputStream(file);
		Properties props= new Properties();
		props.load(Input);

		Enumeration<Object> value = props.keys();

		while (value.hasMoreElements()) {

			key = (String) value.nextElement();
			data = props.getProperty(key);
			if (key.equals("to")) {
				to = data;
			}
			if (key.equals("from")) {

				from = data;
			}

			if (key.equals("password")) {

				password = data;
			}
		}

		AllToAddress = to.split(",");

	}

	public void sendFromGMail() throws IOException {
		getData();

		Properties props = System.getProperties();
		
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[AllToAddress.length];

			// To get the array of addresses
			for (int i = 0; i < AllToAddress.length; i++) {
				toAddress[i] = new InternetAddress(AllToAddress[i]);
			}

			for (int j = 0; j < AllToAddress.length; j++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[j]);
			}
             BodyPart messageBodyPart =new MimeBodyPart();
             messageBodyPart.setText("Hello ,Good Day! \n"
             +"\n"
             +"All scenarios have been excuted.Please find the attached report of the execution .\n"
             +"\n"
             +"Thanks,\n"
             +"Abbas Ahmadov");
             Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(messageBodyPart);
             
             File file2 =new File("C:\\Users\\Administrator\\git\\fs_test2\\target\\cucumber\\cucumber.json");
             messageBodyPart=new MimeBodyPart();
             DataSource source = new FileDataSource(file2.getAbsolutePath());
             messageBodyPart.setDataHandler(new DataHandler(source));
             multipart.addBodyPart(messageBodyPart);
             message.setContent(multipart);
			
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
}

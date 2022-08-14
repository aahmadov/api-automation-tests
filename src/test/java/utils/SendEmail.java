package utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	private static String USER_NAME = "abbasaydinoglu7@gmail.com"; // GMail user name (just the part before// "@gmail.com")
	private static String PASSWORD = "oezjekkypifsyfvs"; // GMail password
//	private static String RECIPIENT = "15551234567@acme.rpxfax.com";
	private static String SUBJECT = "Java send mail example";


	public static void sendFromGMail(String to, String body, File attachment ) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USER_NAME, PASSWORD);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USER_NAME));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(SUBJECT);
			
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(body);
            
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);
            
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachmentPart);
            multipart.addBodyPart(bodyPart);

            message.setContent(multipart);

			Transport.send(message);

			System.out.println("message sent successfully!");

		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
}
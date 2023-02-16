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

	private static String USER_NAME = "auto@softlinx.com";//System.getenv("mail.username");
	
	private static String PASSWORD ="softlinx";//System.getenv("mail.password");


	private static String SUBJECT = "Java send mail example";


	public static void sendFromGMail(String to, String body, File attachment, boolean sendBody) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "10.250.1.27");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "25");
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
			
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);
            
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(attachmentPart);
			
			if(sendBody) {
				MimeBodyPart bodyPart = new MimeBodyPart();
				bodyPart.setText(body);
	            multipart.addBodyPart(bodyPart);
			}

            message.setContent(multipart);

			Transport.send(message);

			System.out.println("message:  message sent successfully!");

		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		}
	}
}
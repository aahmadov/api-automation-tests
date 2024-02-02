package utils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

	private static String USER_NAME = "auto@softlinx.com";//System.getenv("mail.username");
	
	private static String PASSWORD ="softlinx";//System.getenv("mail.password");


	private static String SUBJECT = "Java send Email_To_Fax with HTMLHeader example";


	public static void sendFromGMail(String to, String body, File attachment, boolean sendBody) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "10.250.1.87");
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
	public static void sendFromGMail_81(String to81, String body81, File attachment81,
										boolean withHtmlHeader,
										boolean withBody,
										boolean withSubject,
										boolean withAttachment
										) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "10.250.1.87");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.auth",true);
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
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to81));

             //Subjects
//			if (withSubject ) {
//				message.setSubject(SUBJECT);
//			}

			Multipart multipart = new MimeMultipart();

             //Attachments
			if (withAttachment && attachment81 != null) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(attachment81);
				multipart.addBodyPart(attachmentPart);
			}
             //Text Body
			if (withBody) {
				MimeBodyPart bodyPart = new MimeBodyPart();
				bodyPart.setText(body81);
				multipart.addBodyPart(bodyPart);
			}

			if (withSubject) {
				MimeBodyPart subjectPart = new MimeBodyPart();
				subjectPart.setText(SUBJECT);
				multipart.addBodyPart(subjectPart);
			}

			MimeBodyPart htmlBody = new MimeBodyPart();
			if (withHtmlHeader) {
				// If recvUseHtmlHeader is true, insert the HTML header content into the database

				String deleteHTML = "DELETE FROM `auto3`.`settings` WHERE `sname` = 'Smtpd.EmailHeader';";
				DataBaseUtility.executeSQLUpdateRecvD81(deleteHTML);
				Thread.sleep(1000*3);
				String insertHTML = String.format("INSERT INTO `auto3`.`settings` (`sname`, `svalue`) VALUES ('Smtpd.EmailHeader', 'emailheader.html');");
				DataBaseUtility.executeSQLUpdateRecvD81(insertHTML);
				String defaultContent = "HTML header included.";
				htmlBody.setContent(defaultContent, "text/html");
			} else {
				// If recvUseHtmlHeader is false, delete the HTML header from the database
				String deleteQuery = "DELETE FROM `auto3`.`settings` WHERE `sname` = 'Smtpd.EmailHeader';";
				DataBaseUtility.executeSQLUpdateRecvD81(deleteQuery);
				// You can also set some default content if needed
				String defaultContent = "No HTML header included.";
				htmlBody.setContent(defaultContent, "text/html");
			}

			multipart.addBodyPart(htmlBody);
			message.setContent(multipart);

			Transport.send(message);

			System.out.println("message: message sent successfully!");

		} catch (MessagingException | IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}


}
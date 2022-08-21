package utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import javax.mail.search.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ReceiveEmail {

	private static String host = "imap.gmail.com";
	private static String mailStoreType = "imap";
	private static String username = System.getenv("mail.username");
	private static String password = System.getenv("mail.password");

	public static Boolean receiveEmail(String mailFrom, String subjectFilter) throws InterruptedException {

		Boolean result = false;
		try {

			// 1) create the session
			Properties props = new Properties();
			props.setProperty("mail.imap.ssl.enable", "true");
			Session session = Session.getDefaultInstance(props, null);

			// connect to the email//
			Store emailStore = session.getStore(mailStoreType);
			emailStore.connect(host, username, password);

			// 3) create the folder object and open it with read and write permissions
			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);

			// 4) Search conditions
			SearchTerm from = new FromTerm(new InternetAddress(mailFrom)); // from email filter (no-reply@rpxqa.com)
			
			SearchTerm unreadEmails = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // only unread emails filter
			SearchTerm subject = new SubjectTerm(subjectFilter); //filter by subject string
			SearchTerm todayDate = new ReceivedDateTerm(ComparisonTerm.EQ,
					DateUtils.truncate(new java.util.Date(), java.util.Calendar.DATE)); // filter the email received email
			SearchTerm condition = new AndTerm(new SearchTerm[] { from,todayDate,unreadEmails});

			Message[] messages = emailFolder.search(condition);
			int noOfTimes = 0;
			while (messages.length == 0 && noOfTimes < 15) {
				Thread.sleep(1000 * 20);
				noOfTimes++;
				emailFolder.close(false);
				emailFolder.open(Folder.READ_WRITE);
				messages = emailFolder.search(condition);
			}

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("Text: " + message.getContent().toString());
				message.setFlag(Flags.Flag.SEEN, true); // opening the email - marking it as read

				long diff = getMessageTimeDiff(message);
				System.out.println("Difference between mailNotif received and local time by minutes: " + diff);
				int numOfPages = getNumberOfPages(message);
				System.out.println("Total pages in a attachment: " + numOfPages);
				
				if (diff < 5 && result == false) {
					result = true;
				}
			}

			// 5) close the store and folder objects
			emailFolder.close(false);
			emailStore.close();

		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

		return result;
		
		
	}

	public static long getMessageTimeDiff(Message message) throws MessagingException, IOException, ParseException {
		String body = IOUtils.toString(MimeUtility.decode(message.getInputStream(), "quoted-printable"),
				StandardCharsets.UTF_8);
		try {
			String dateTime = StringUtils.substringBetween(body, "Time received: ", "\r\nNumber of pages");
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, MMMM dd, yyyy hh:mm aaa");
			Date date = dateFormat.parse(dateTime);
			
			//System.out.println("-----------"+date);
			
			long diffInMillies = Math.abs(Calendar.getInstance().getTime().getTime() - date.getTime());
			return TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
		} catch (Exception e) {
			return 11111;
		}
	}

	public static int getNumberOfPages(Message message) throws MessagingException, IOException {
		String body = IOUtils.toString(MimeUtility.decode(message.getInputStream(), "quoted-printable"),
				StandardCharsets.UTF_8);
		try {
			String numOfPages = StringUtils.substringBetween(body, "Number of pages received: ",
					"\r\nSender fax machine ID:");
			return Integer.parseInt(numOfPages);
		} catch (Exception e) {
			return 11111;
		}
	}
	
}

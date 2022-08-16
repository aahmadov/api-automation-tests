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

    public static void main(String[] args) {

        String host = "imap.gmail.com";
        String mailStoreType = "imap";
        String username = "Abbas@softlinx.com";
        String password = "Aydin1234!";
        String from = "no-reply@rpxqa.com";
        String successfulSubject = "Inbound Fax to %s, Unassigned Faxes (Received)";
        String failedSubject = "Inbound Fax to %s, Unassigned Faxes (Receive Failed)";
        String sendFailedSubject = "Outgoing fax to 11111111111 (Send Failed)";

//        receiveEmail(host, mailStoreType, username, password, from, String.format(successfulSubject, "11111111111"));
        receiveEmail(host, mailStoreType, username, password, from);
    }

    public static void receiveEmail(String host, String storeType, String user, String password, String mailFrom) {
        try {

        	//1) create the session
            Properties props = new Properties();
            props.setProperty("mail.imap.ssl.enable", "true");
            Session session = Session.getDefaultInstance(props, null);

            //connect to the email
            Store emailStore = session.getStore(storeType);
            emailStore.connect(host, user, password);

            //3) create the folder object and open it with read and write permissions
            Folder emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            //4) Search conditions
            SearchTerm from = new FromTerm(new InternetAddress(mailFrom)); //from email filter
            SearchTerm seen = new FlagTerm(new Flags(Flags.Flag.SEEN), false); // only unread emails filter
//            SearchTerm subject = new SubjectTerm(mailSubject); //matching the subject string filter
            SearchTerm newerThan = new ReceivedDateTerm(ComparisonTerm.EQ, DateUtils.truncate(new java.util.Date(), java.util.Calendar.DATE)); // filter the email received email
            SearchTerm condition = new AndTerm(new SearchTerm[]{from, seen, newerThan});
            
            
            Message[] messages = emailFolder.search(condition);
            if (messages.length == 0) {
                Thread.sleep(60000);
                messages = emailFolder.search(condition);
            }
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
                message.setFlag(Flags.Flag.SEEN, true);
                long diff = getMessageTimeDiff(message);
                int noOfPages = getNumberOfPages(message);
                System.out.println(diff);
            }

            //5) close the store and folder objects
            emailFolder.close(false);
            emailStore.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getMessageTimeDiff(Message message) throws MessagingException, IOException, ParseException {
        String body = IOUtils.toString(
                MimeUtility.decode(message.getInputStream(), "quoted-printable"),
                StandardCharsets.UTF_8
        );
        String dateTime = StringUtils.substringBetween(body, "Time received: ", "\r\nNumber of pages");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE, MMMM dd, yyyy hh:mm aaa");
        Date date = dateFormat.parse(dateTime);
        long diffInMillies = Math.abs(Calendar.getInstance().getTime().getTime() - date.getTime());
        return TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
    }
    
    private static int getNumberOfPages(Message message) throws MessagingException, IOException {
        String body = IOUtils.toString(
                MimeUtility.decode(message.getInputStream(), "quoted-printable"),
                StandardCharsets.UTF_8
        );
        String noOfPages = StringUtils.substringBetween(body, "Number of pages received: ", "\r\nSender fax machine ID:");
        return Integer.parseInt(noOfPages);
    } 
	}  


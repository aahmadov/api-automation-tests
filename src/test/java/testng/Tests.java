package testng;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Tests {

    String bodyMessage = ConfigReader.getProperty("bodyMessage");
    String from = "no-reply@rpxqa.com";

    @Test(testName = "Send mail to Fax", groups = {"emailToFax"})
    @Parameters({"to", "times", "faxNumFileLoc", "pageSize", "subject", "sendBody", "uploadedFile"})
    void sendMailToFax1(String to, int times,
                       @Optional String faxNumFileLoc,
                       String pageSize, String subject,
                       boolean sendBody,
                       @Optional String uploadedFaxNumbersFileLoc) throws Exception {
        System.out.println("Values: " + to + ":" + times + ":" + faxNumFileLoc + ":" + pageSize + ":" + subject + ":" + sendBody + ":" + uploadedFaxNumbersFileLoc);
        String finalFaxNumberFileLoc = StringUtils.isBlank(faxNumFileLoc) ? uploadedFaxNumbersFileLoc : faxNumFileLoc;
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(finalFaxNumberFileLoc)));

        // loop to send email based on of times provided in the scenario
        for (int i = 1; i <= times; i++) {
            System.out.println("Iteration time of sendmail in the loop :" + i);

            String faxNumber;
            String toEmail;
            //Checking whether to contains FaxNumber in scenario,
            if (to.contains("@")) {
                //Get FaxNumber if already exist in the 'to' field+
                faxNumber = to.split("@")[0];
                toEmail = to;
            } else {
                //If FaxNumber is blank, get the Fax number from file randomly and generate 'to' address
                faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));
                toEmail = faxNumber + "@" + to; // result: 1234567891@auto1.rpxqa.com
            }

            //Read the subject string and add the FaxNumber into the string
            String emailSubject = String.format(ConfigReader.getProperty(subject), faxNumber); // result: Inbound Fax to 11111111111, Unassigned Faxes (Received)
            File file = FileReader.getFileUsingPageSize(pageSize);
            System.out.println("to: " + toEmail);
            System.out.println("file: " + file.getAbsolutePath());

            Date startTime = Calendar.getInstance().getTime();

//            SendEmail.sendFromGMail(toEmail, bodyMessage, file, sendBody);
            Boolean result = ReceiveEmail.receiveEmail(from, emailSubject);
//            Boolean result = true;


            if (!result) {

                System.out.println("*** after 5 min iteration, there is not a expected notification");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTimeString = formatter.format(startTime);
                Date endTime = Calendar.getInstance().getTime();
                String endTimeString = formatter.format(endTime);

                String EmailToFaxQuery = String.format("select JobStatus,FaxNumber from auto1.sendstatus where (CreateTime between '%s' and '%s')  order by jobid desc limit 1;", startTimeString, endTimeString);

                List<Map<String, Object>> results = DataBaseUtility.executeSQLQuery(EmailToFaxQuery);
                if (results.size() == 0) {
                    fail("No record present in the Database for the fax email sent");
                }
                System.out.println(results);
                assertTrue(results.get(0).get("JobStatus").equals("Sent"));
            }
        }
    }
}

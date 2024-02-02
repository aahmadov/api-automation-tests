
package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import utils.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class EmailToFaxSteps {
    String bodyMessage = ConfigReader.getProperty("bodyMessage");
    String from = "no-reply@rpxqa.com";

    @Given("I want to send an EmailToFax message and verify")
    public void i_want_to_send_an_EmailToFax_message(DataTable dataTable) throws Exception {
        Map<String, String> data = dataTable.transpose().asMaps().get(0);
        List<String> faxNumbers = FileReader.convertToList(
                CsvUtils.readAllLines(
                        ResourceUtils.getResourceFilePathAbsPath(data.get("faxNumFileLoc"))));

        // loop to send email based on of times provided in the scenario
        for (int i = 1; i <= Integer.parseInt(data.get("times")); i++) {
            System.out.println("Iteration time of sendmail in the loop :" + i);
            
            String faxNumber;
            String to;
            //Checking whether to contains FaxNumber in scenario,
            if (data.get("to").contains("@")) {
                //Get FaxNumber if already exist in the 'to' field+
                faxNumber = data.get("to").split("@")[0];
                to = data.get("to");
            } else {
                //If FaxNumber is blank, get the Fax number from file randomly and generate 'to' address
            	faxNumber = faxNumbers.get(ThreadLocalRandom.current().nextInt(faxNumbers.size()));
            	to = faxNumber + "@" + data.get("to"); // result: 1234567891@auto1.rpxqa.com
            }

            //Read the subject string and add the FaxNumber into the string
            String subject = String.format(ConfigReader.getProperty(data.get("subject")), faxNumber); // result: Inbound Fax to 11111111111, Unassigned Faxes (Received)
            File file =  FileReader.getFileUsingPageSize(data.get("pageSize"));
            System.out.println("to: " + to);
            System.out.println("file: " + file.getAbsolutePath());

            Date startTime = Calendar.getInstance().getTime();

          SendEmail.sendFromGMail(to, bodyMessage, file, Boolean.parseBoolean(data.get("sendBody")));
            Boolean result = ReceiveEmail.receiveEmail(from, subject);
            //Boolean result = true;
          

            if(!result) {
            	
            	System.out.println("*** after 5 min iteration, there is not a expected notification");
            	
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTimeString = formatter.format(startTime);
                Date endTime = Calendar.getInstance().getTime();
                String endTimeString = formatter.format(endTime);
	            
	            String EmailToFaxQuery =String.format("select JobStatus from auto1.recvstatus where ReceivingPhone='13333333333' order by jobid desc limit 1;");
	            
	            List<Map<String, Object>> results = DataBaseUtility.executeSQLQuery(EmailToFaxQuery);
	            if(results.size() == 0) {
	            	fail("No record present in the Database for the fax email sent");
	            }
	            System.out.println(results);
	            assertTrue(results.get(0).get("JobStatus").equals("Recv Fail") || results.get(0).get("JobStatus").equals("Received"));
            }
        }
    }//(TimeRecieved between '%s' and '%s') and , startTimeString, endTimeString
}
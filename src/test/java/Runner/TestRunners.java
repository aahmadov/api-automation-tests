package Runner;


import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.*;
import utils.sendMailing;

import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "./src/test/resources/features", 
        glue = {"stepDefinitions" },		
        dryRun =false, 
        monochrome = true, 
        tags = {"@Loadtest"},
        plugin = { "pretty",
				"html:target/cucumber", "json:target/cucumber.json",
				"junit:target/cucxml/cucumber.xml", "hooks.CucumberHooks" })
public class TestRunners {

	@AfterClass
	public static void sendingMail() {
		Runtime run = Runtime.getRuntime();
		run.addShutdownHook(new Thread());
	}

	public void run() {

		sendMailing sm = new sendMailing();
		try {
			sm.sendFromGMail();
			System.out.println("Report has been sent");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
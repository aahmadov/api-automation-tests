package Runner;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.*;
import utils.sendMailing;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "./src/test/resources/features", 
        glue = {"stepDefinitions" },		
        dryRun = false, 
        monochrome = true, 
        tags = { "@smoke" },
        plugin = { "pretty",
				"html:target/cucumber-html-report", "json:target/cucumber/cucumber.json",
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
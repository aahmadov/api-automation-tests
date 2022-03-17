package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.*;

    @RunWith(Cucumber.class)
	@CucumberOptions(
	        features = "./src/test/resources/features",
	        glue={"stepDefinitions"},
	        dryRun =false,
	        monochrome = true,
	        tags = {"@registryEditor"},
	        plugin = { "pretty", "html:target/cucumber-html-report", "json:target/cucumber/cucumber.json" ,"junit:target/cucxml/cucumber.xml"}
	)
	public class TestRunners {
    	

}



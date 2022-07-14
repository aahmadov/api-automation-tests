package API;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import org.junit.Test;

public class writeData {

	@Test

	public void writeTofile() throws IOException {

		String filePathtowrite = "./src/test/resources/responseBody/writeData.Json";

		File file = new File(filePathtowrite);

		String data = "{\"FaxNumber\": \"1-555-1212\", \r\n"
				+ "           \"RcptName\": \"Alice Doherty\"\r\n"
				+ "                t}";

		FileUtils.writeStringToFile(file, data);

	}

	
	
	
}

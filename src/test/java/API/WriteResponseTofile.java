package API;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import io.restassured.response.Response;
import utils.ConfigReader;
import utils.RestRequestUtils;

public class WriteResponseTofile {

	@Test
	public void DataFromResponse() throws IOException {
		
		Response resp=RestRequestUtils.getFax(ConfigReader.getProperty("getFaxByID_url")+ConfigReader.getProperty("valid_ID"));
		
		String data=resp.prettyPrint();
		
		String path ="src/test/resources/responseBody/writeData.Json";
		File file=new File(path);
		
		FileUtils.writeStringToFile(file, data);
	}
}

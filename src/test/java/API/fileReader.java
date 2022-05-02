package API;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class fileReader {
	@Test
	public void fileRead() throws IOException {
	
	String filePath = "/src/test/resources/responseBody/writeData";

	File file=new File(filePath);
	
	String data=FileUtils.readFileToString(file);
	
	System.out.println(data);
	

	
}

}
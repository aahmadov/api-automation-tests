package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TSI_dataFor_load {
	
	public static void writeTofile(String data,String filePathtowrite) throws IOException {

		File file = new File(filePathtowrite);

		FileUtils.writeStringToFile(file, data);

	}

}

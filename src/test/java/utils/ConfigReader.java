package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	 private static Properties prop;
	 
	static {
	        try {
	 
	            String path ="./src/test/resources/config/apiConfig.properties";
	            FileInputStream stream = new FileInputStream(path);
	            prop = new Properties();
	            prop.load(stream);
	            stream.close();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	      }
	        

  public static String getProperty(String key) {
	return prop.getProperty(key);
	
	}
	
  public static void setProperty(String key,String value) {
	  prop.setProperty(key,value);
		
		}
}


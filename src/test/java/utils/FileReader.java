package utils;

import java.io.File;
import java.util.Random;

public class FileReader {

			public static File readfile(String filename) {
	
			 String filePath="src/test/resources/requestBody/";
			  String pdfFile= filePath+filename+".pdf";
			  		
	return new File(pdfFile);
		
		}
			
			public static String randomFileFromFolder() {
				File folder = new File("C:\\Users\\faxes");
				File[] listOfFiles = folder.listFiles();
				for (int i = 3; i < listOfFiles.length; i++) {
				  if (listOfFiles[i].isFile()) {
				    System.out.println(listOfFiles[i].getName());
				  } else if (listOfFiles[i].isDirectory()) {
				    System.out.println("Directory " + listOfFiles[i].getName());
				  }
				 
				}
				
				return "Randomly selected file: " + listOfFiles[(int)(Math.random()*listOfFiles.length)].getName();
			}

			public static String randomNumberFor_TSI() {
				
				Random TSINumber = new Random();
				
				int random_Num=TSINumber.nextInt(100);
				
				String TSI = "?TSI=Test"+random_Num;
				
				return TSI;

			}
	}

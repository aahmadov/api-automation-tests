package stepDefinitions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ConfigReader;

import java.util.*;
import java.awt.Desktop;
import java.io.*;


public class RegistryEditor_steps {
	
	
@Given("I want to write a step to change registryEditor")
public void i_want_to_write_a_step_to_change_registryEditor() throws IOException, InterruptedException  {

	Process process = new ProcessBuilder(new String[] {"cmd.exe", "/c","start","regedit","/s","C:/Users/Administrator/Desktop/18.reg"}).start();
    

     process.getInputStream();
     int exitCode=process.waitFor();
     System.out.println(exitCode);
     
     
	//Process newProcess = builder.start();
	
	//"cmd.exe", "/c","start","cd/ "C:\Windows\regedit.exe", "-r:http://10.250.1.99:8080", "-u:abbas", "-p:Softlinx1!", "c:/Users/abbas/Desktop/12.reg"

//	String[] cmd ={"C:/Users/abbas/Desktop/6.reg","C:\>cmdkey /generic:10.250.1.99:8080 /user:abbas /pass:Softlinx1!",
//	"C:\>mstsc.exe /v:10.250.1.99"};
	
//	Process p;
//	try {
//		Runtime rt = Runtime.getRuntime();
//		p = rt.exec(cmd);

//		int exitVal = p.waitFor();            
//        System.out.println("Process exitValue: " + exitVal);
//		
//	} catch (IOException | InterruptedException e) {
//		
//		e.printStackTrace();
//	}
	
}
}

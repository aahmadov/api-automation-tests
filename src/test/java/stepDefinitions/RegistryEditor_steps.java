package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import java.util.*;
import java.io.*;


public class RegistryEditor_steps {

@Given("I want to write a step to change registryEditor")
public void i_want_to_write_a_step_to_change_registryEditor() throws IOException, InterruptedException  {
	
	ProcessBuilder builder = new ProcessBuilder(new String[] { "cmd", "/c","C:/>cmdkey /generic:10.250.1.99:8080 /user:abbas /pass:Softlinx1!",
			"C:/>mstsc.exe /v:10.250.1.99","C:/Users/abbas/Desktop/10.reg"});
	
	Process newProcess = builder.start();
	newProcess.waitFor();
	System.out.println(newProcess);
	
	
	// new updates///
	
	
//	String[] cmd ={"regedit.exe","C:/Users/abbas/Desktop/6.reg","C:\>cmdkey /generic:10.250.1.99:8080 /user:abbas /pass:Softlinx1!",
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

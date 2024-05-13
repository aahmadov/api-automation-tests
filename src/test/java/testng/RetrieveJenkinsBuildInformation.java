package testng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class RetrieveJenkinsBuildInformation {


        public static void main(String[] args) {
            // Define the Jenkins API endpoint URL
            String jenkinsUrl = "http://10.250.1.67:8080/job/Smoke_Test_TestNG/lastBuild/api/json";

            // Define your Jenkins username and password
            String username = "admin";
            String password = "Auto*1234!";

            try {
                // Create a URL object
                URL url = new URL(jenkinsUrl);

                // Create a HttpURLConnection object
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                // Set the request method to GET
                conn.setRequestMethod("GET");

                // Set Basic Authentication header
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
                String authHeaderValue = "Basic " + new String(encodedAuth);
                conn.setRequestProperty("Authorization", authHeaderValue);

                // Get the response code
                int responseCode = conn.getResponseCode();

                // If the response code is 200 (OK), read the response
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Create a BufferedReader to read the response
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    // Read the response line by line
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Print the JSON response

                    System.out.println("Response:");
                    String lines = response+"\n";

                    System.out.println(lines);
                } else {
                    // If the response code is not 200, print an error message
                    System.out.println("Failed to retrieve build information. Response code: " + responseCode);
                }
            } catch (Exception e) {
                // If an exception occurs, print the stack trace
                e.printStackTrace();
            }
        }
    }




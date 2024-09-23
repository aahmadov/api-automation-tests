package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static File readJsonFile(final String path) {
        String filePath = ResourceUtils.getResourceFilePathAbsPath(path);
        return new File(filePath);
    }

    public static Map<String, String> getDataBasedOnTestCaseName(final String testcaseName) {
        try {
            File file = readJsonFile("uploadedTestData.json");
            File finalFile = file.exists() ? file : readJsonFile("testData.json");
            Map<String, HashMap<String, String>> testCases = mapper.readValue(finalFile, new TypeReference<>() {
            });
            return testCases.get(testcaseName);
        } catch (Exception exception) {
            System.out.println("Exception while reading data from json file. Exception: " + exception.getMessage());
        }
        return null;
    }
    public static Map<String, String> getDataBasedOnTestCaseName81(final String testcaseName) {
        try {
            File file = readJsonFile("uploadedTestData.json");
            File finalFile = file.exists() ? file : readJsonFile("testData81.json");
            Map<String, HashMap<String, String>> testCases = mapper.readValue(finalFile, new TypeReference<>() {
            });
            return testCases.get(testcaseName);
        } catch (Exception exception) {
            System.out.println("Exception while reading data from json file. Exception: " + exception.getMessage());
        }
        return null;
    }
    public static Map<String, String> getDataBasedOnTestCaseName46(final String testcaseName) {
        try {
            File file = readJsonFile("uploadedTestData.json");
            File finalFile = file.exists() ? file : readJsonFile("testData46.json");
            Map<String, HashMap<String, String>> testCases = mapper.readValue(finalFile, new TypeReference<>() {
            });
            return testCases.get(testcaseName);
        } catch (Exception exception) {
            System.out.println("Exception while reading data from json file. Exception: " + exception.getMessage());
        }
        return null;
    }
}

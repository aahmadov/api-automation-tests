package utils;

import com.opencsv.CSVReader;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvUtils {

    public static List<String[]> readAllLines(String filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}

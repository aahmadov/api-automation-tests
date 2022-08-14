package utils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FileReader {

    public static File readfile(String filename) {
        String filePath = "src/test/resources/requestBody/";
        String pdfFile = filePath + filename + ".pdf";

        return new File(pdfFile);
    }

    public static File randomFileFromFolder() {
        File folder = new File("C:\\Users\\faxes");
        File[] listOfFiles = folder.listFiles((d, name) -> name.endsWith(".pdf"));
        String filePath = Objects.requireNonNull(listOfFiles)[(int) (Math.random() * listOfFiles.length)].getAbsolutePath();
        return new File(filePath);
    }

    public static File getFileUsingPageSize(final String pageSize) {
        try {
            File folder = Paths.get(ClassLoader.getSystemResource("requestBody").toURI()).toFile();
            File[] listOfFiles = folder.listFiles((d, name) -> name.endsWith(".pdf"));
            Optional<File> fileOptional = Arrays.stream(Objects.requireNonNull(listOfFiles))
                    .filter(file -> file.getName().matches("[^0-9]*"+pageSize+"[^0-9]*"))
                    .findAny();
            String filePath = fileOptional
                    .map(File::getAbsolutePath)
                    .orElseGet(() -> Objects.requireNonNull(listOfFiles)[(int) (Math.random() * listOfFiles.length)].getAbsolutePath());
            return new File(filePath);
        } catch (URISyntaxException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public static String randomNumberFor_TSI() {

        String uuid = UUID.randomUUID().toString();

//        uuid.substring(0, Math.min(uuid.length(), 15))
        return "?TSI=Test" + uuid.substring(0, Math.min(uuid.length(), 10));
    }
    
    public static String randomFaxNumberforTSI() {

       
        Random TSINumber = new Random();
        int random_Num = TSINumber.nextInt(100);
        return "?TSI=Test"+ random_Num ; 
    }
    

    public static String randomFaxNumberEmailToFax() {

        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);
        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df1 = new DecimalFormat("0000");

        return String.format("%1$s-%2$s-%3$s", df.format(num1), df.format(num2), df1.format(num3)+"@demo.rpxtest.com");
    }

    public static String randomFaxNumber() {

        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);
        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df1 = new DecimalFormat("0000");

        return String.format("%1$s-%2$s-%3$s", df.format(num1), df.format(num2), df1.format(num3));
    }
    
    
    
    
    
    public static List<String> convertToList(final List<String[]> values) {
        return values.stream().map(value -> value[0]).collect(Collectors.toList());
    }
}

package utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class FileReader {

    public static File readfile(String filename) {
        String filePath = "src/test/resources/requestBody/pdf/";
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
            File folder = Paths.get(ClassLoader.getSystemResource("requestBody/pdf").toURI()).toFile();
            File[] listOfFiles = folder.listFiles((d, name) -> name.endsWith(".pdf"));
            Optional<File> fileOptional = Arrays.stream(Objects.requireNonNull(listOfFiles))
                    .filter(file -> file.getName().matches("[^0-9]*" + pageSize + "[^0-9]*"))
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

    public static File getFileUsingPageSize(final String pageSize, final String fileType) {
        try {
            File folder = Paths.get(ClassLoader.getSystemResource("requestBody/" + fileType).toURI()).toFile();
            File[] listOfFiles = folder.listFiles((d, name) -> name.endsWith(fileType.toLowerCase()));
            Optional<File> fileOptional = Arrays.stream(Objects.requireNonNull(listOfFiles))
                    .filter(file -> file.getName().matches("[^0-9]*" + pageSize + "[^0-9]*"))
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
        return "?TSI=Test" + uuid.substring(0, Math.min(uuid.length(), 8));
    }

    public static String randomFaxNumberforTSI() {


        Random TSINumber = new Random();
        int random_Num = TSINumber.nextInt(100);
        return "?TSI=Test" + random_Num;
    }

    public static String randomTsi() {
        String uuid = UUID.randomUUID().toString();
        return "Test" + uuid.substring(0, Math.min(uuid.length(), 8));
    }


    public static String randomFaxNumberEmailToFax() {

        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);
        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df1 = new DecimalFormat("0000");

        return String.format("%1$s-%2$s-%3$s", df.format(num1), df.format(num2), df1.format(num3) + "@demo.rpxtest.com");
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

    public static String fileToByteString(final String filePath) throws IOException {
        byte[] rawData = Files.readAllBytes(Paths.get(filePath));
        byte[] encoded = Base64.encodeBase64(rawData);
        return new String(encoded);
    }

    public static String getFileName(String filePath) {
        return FilenameUtils.getName(filePath);
    }

    public static String getContentTypeForFile(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension.toLowerCase()) {
            case "txt": {
                return "text/plain";
            }
            case "json": {
                return "application/json";
            }
            default: {
                return "application/pdf";
            }
        }
    }
}

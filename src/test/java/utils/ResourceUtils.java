package utils;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class ResourceUtils {

    public static String getResourceFilePathAbsPath(final String relativePath) {
        try {
            return Paths.get(ClassLoader.getSystemResource(relativePath).toURI()).toString();
        } catch (Exception exception) {
            System.out.println("Exception while trying to find the file " + relativePath + ". " + exception.getMessage());
        }
        return "";
    }
}

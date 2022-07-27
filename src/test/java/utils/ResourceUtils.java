package utils;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class ResourceUtils {

    public static String getResourceFilePathAbsPath(final String relativePath) {
        try {
            return Paths.get(ClassLoader.getSystemResource(relativePath).toURI()).toString();
        } catch (URISyntaxException exception) {
            System.out.println(exception.getMessage());
        }
        return "";
    }
}

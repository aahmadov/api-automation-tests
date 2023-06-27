package utils;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;

import java.io.IOException;

public class SftpUtils {


    public static boolean checkFileExist(String username, String password, String path, String fileName) {
        try {
            String filePath = path + fileName;
            UserAuthenticator auth = new StaticUserAuthenticator("", username, password);
            FileSystemOptions opts = new FileSystemOptions();
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
            try (FileObject dest = VFS.getManager().resolveFile(filePath, opts)) {
                boolean fileExist = dest.exists();
                System.out.println("******** '" + filePath + "' exist: " + fileExist);
                return fileExist;
            }
        } catch (IOException exception) {
            System.out.println("Exception occurred while checking file exist on the remote server. Exception: " + exception.getMessage());
            return false;
        }
    }
    public static boolean checkFileExistarchive(String path, String fileName) {
//        try {


        String filePath = path;
        try (FileObject dest = VFS.getManager().resolveFile(filePath)) {

            if (dest.getType().equals(FileType.FOLDER)) {
                FileObject[] children = dest.getChildren();
                for (FileObject child : children) {
                    if (child.getType().equals(FileType.FOLDER)) {
                        String folderName = child.getName().getBaseName();
                        System.out.println("Folder Name: " + folderName);


                    }
                }
            }
//                boolean fileExist = dest.exists();
//                System.out.println("******** '" + filePath + "' exist: " + fileExist);
            // return FileType;

        } catch (IOException exception) {
            System.out.println("Exception occurred while checking file exist on the remote server. Exception: " + exception.getMessage());

              }return false;
    }
}












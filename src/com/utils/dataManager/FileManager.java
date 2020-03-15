package com.utils.dataManager;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public enum FileManagerVariants{
        NEW_FILE_IF_NOT_EXISTS,
        FILE_ALREADY_EXISTS
    }

    public static File createNewFile(String filePath, String fileName, FileManagerVariants variant) throws IOException {

        File file = new File(filePath + fileName);
        File fileFolders = new File(filePath);

        if(variant == FileManagerVariants.FILE_ALREADY_EXISTS){
            return file;
        }

        if (!filePath.isEmpty()) {
            if (!fileFolders.exists()) {
                if (!fileFolders.mkdirs()) {
                    throw new IOException("Failed to create path for : " + filePath);
                }
            }
        }
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Failed to create new file :" + fileName);
            }
        }
        return file;
    }
}

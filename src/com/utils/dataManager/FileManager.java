package com.utils.dataManager;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static File createNewFile(String filePath, String fileName) throws IOException {

        File file = new File(filePath + fileName);
        File fileFolders = new File(filePath);

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

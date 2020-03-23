package com.utils.dataManager;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public enum FileManagerVariants {
        NEW_FILE_IF_NOT_EXISTS,
        FILE_ALREADY_EXISTS
    }

    /**
     * Creates new file from a given file name and file path with checking all possible exception.
     *
     * @param filePath Specifies where file is saved / should be saved
     * @param fileName Name of file
     * @param variant  Specifies if the file already exist / don't exist.
     * @return The new file created with fileName and filePath specified
     * @throws IOException If could not create the file
     */
    public static File createNewFile(String filePath, String fileName, FileManagerVariants variant) throws IOException {

        File file = new File(filePath + fileName);
        File fileFolders = new File(filePath);

        if (variant == FileManagerVariants.FILE_ALREADY_EXISTS) {
            if (!file.exists()) {
                throw new IOException("File not not found : " + filePath + fileName);
            }
        }

        // Check if file path is not empty ( could be already written-in fileName )
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

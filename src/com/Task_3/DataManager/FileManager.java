package com.Task_3.DataManager;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static File createNewFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        if (!file.exists()) {
            new File(filePath).mkdirs();
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}

package com.task_3.dataManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class DataProvider {

    public static void initiateRandomNumbersToFile(File writeFile, int count, int maxValue) {
        try (PrintWriter writer = new PrintWriter(writeFile)) {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                writer.println(random.nextInt(maxValue));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

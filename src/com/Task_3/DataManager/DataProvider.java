package com.Task_3.DataManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class DataProvider {

    public static void initiateRandomNumbersToFile(File writeFile, int count) {
        try (PrintWriter writer = new PrintWriter(writeFile)) {
            Random random = new Random();
            for (int i = 0; i < count; i++) {
                writer.println(random.nextInt(1000));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

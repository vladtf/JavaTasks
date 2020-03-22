package com.task_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> fileNames = Arrays.asList("A", "M", "N", "T", "L");

        List<String> tokens;

        try (Scanner reader = new Scanner(System.in)) {
            tokens = new ArrayList<>();

            while (true) {
                System.out.print("Introduceti numele (n for exit): ");
                String name = reader.nextLine();

                if (name.equals("n"))
                    break;

                // check if list don't already contains the name

                // not case sensitive
                if (!tokens.stream().map(String::toUpperCase).collect(Collectors.toList()).contains(name.toUpperCase()))
                    tokens.add(name);

                // case sensitive
//                if (!tokens.stream().map(String::toUpperCase).collect(Collectors.toList()).contains(name))
//                    tokens.add(name);
            }
        }

        fileNames.forEach(firstLetter -> {
            List<String> names = tokens.stream().filter(name -> name.toUpperCase().startsWith(firstLetter)).collect(Collectors.toList());

            try {
                writeToFile(firstLetter, names);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private static void writeToFile(String firstLetter, List<String> names) throws IOException {
        String fileName = "numeCu" + firstLetter + ".txt";
//        String filePath = "src/com/task_1/data/" + fileName;
        String filePath = java.nio.file.Paths.get(".").toAbsolutePath() + File.separator + fileName;

        File file = createNewFileFromPath(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            names.forEach(writer::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static File createNewFileFromPath(String filePath) throws IOException {
        File file;
        file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }
}

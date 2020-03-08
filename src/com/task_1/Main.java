package com.task_1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;

public class Main {

    private static List<String> fileNames;

    public static void main(String[] args) {

        fileNames = Arrays.asList("A", "M", "N", "T", "L");

        List<String> tokens;

        try (Scanner reader = new Scanner(System.in)) {
            tokens = new ArrayList<>();

            while (true) {
                System.out.print("Introduceti numele (n for exit): ");
                String name = reader.nextLine();

                if (name.equals("n"))
                    break;

                // check if list don't already contains the name
                if (!tokens.stream().map(String::toUpperCase).collect(Collectors.toList()).contains(name.toUpperCase()))
                    tokens.add(name);
            }
        }

        fileNames.forEach(firstLetter -> {
            List<String> names = tokens.stream().filter(name -> name.toUpperCase().startsWith(firstLetter)).collect(Collectors.toList());

            writeToFile(firstLetter, names);
        });

    }

    private static void writeToFile(String firstLetter, List<String> names) {
        String fileName = "numeCu" + firstLetter;
        String filePath = "src/com/task_1/data/" + fileName;

        try (PrintWriter writer = new PrintWriter(new File(filePath))) {
            names.forEach(writer::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

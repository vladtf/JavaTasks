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

        Scanner reader = new Scanner(System.in);
        List<String> tokens = new ArrayList<>();

        while (true) {
            System.out.print("Introduceti numele (n for exit): ");
            String name = reader.nextLine();

            if (name.equals("n"))
                break;
            tokens.add(name);
        }

        fileNames.forEach(firstLetter -> {
            List<String> names = tokens.stream().filter(name -> name.toUpperCase().startsWith(firstLetter)).collect(Collectors.toList());

            writeToFile(firstLetter, names);
        });

    }

    private static void writeToFile(String firstLetter, List<String> names) {
        String fileName = "numeCu" + firstLetter;

        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            names.forEach(writer::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

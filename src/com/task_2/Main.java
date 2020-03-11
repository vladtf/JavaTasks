package com.task_2;

import java.io.*;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "file.txt";
        String filePath = java.nio.file.Paths.get(".").toAbsolutePath() + File.separator + fileName;
        File file = createNewFileFromPath(filePath);

        try (Scanner scanner = new Scanner(System.in)) {
            Set<Integer> numbers = new TreeSet<>();
            while (true) {
                System.out.print("Citeste numarul : ");
                String string = scanner.next();
                try {
                    int number = parseInt(string);

                    // if number already exists returns true
                    if (!numbers.add(number)) {
                        break;
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Not a number!");
                }
            }

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(numbers.stream().map(Object::toString).collect(Collectors.joining(" , ")) + " .");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println(reader.readLine());
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

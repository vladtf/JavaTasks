package com.task_2;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.*;
import static java.util.Comparator.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "\\file.txt";
        String filePath = System.getProperty("user.dir") + fileName;
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

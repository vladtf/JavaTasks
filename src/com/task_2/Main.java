package com.task_2;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Integer.*;
import static java.util.Comparator.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "file.txt";
        String filePath = "src/com/task_2/data/" + fileName;

        try (Scanner scanner = new Scanner(System.in)) {
            List<Integer> numbers = new ArrayList<>();
            while (true) {
                System.out.print("Citeste numarul : ");
                String string = scanner.next();
                try {
                    int number = parseInt(string);
                    if (numbers.contains(number)) {
                        break;
                    }
                    numbers.add(number);

                } catch (NumberFormatException e) {
                    System.out.println("Not a number!");
                }
            }

            numbers.sort(naturalOrder());

            try (PrintWriter writer = new PrintWriter(filePath)) {
                writer.println(numbers.stream().map(Object::toString).collect(Collectors.joining(" , ")) +" .");
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println(reader.readLine());
        }
    }
}

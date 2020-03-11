package com.Task_3;

//"Se citeste dintr-un fisier (pe fiecare rand sunt numere < 3 cifre - fisierul poate avea si un milion de row-uri). " +
//        "Fisierul trebuie procesat astfel incat fiecare numar sufera cateva modificari (daca e mai mic de 100 este " +
//        "incrementat cu 1, daca e mai mare de 100 e decrementat cu 1). Noile valori trebuie salvate intr-un alt fisier." +
//        "As vrea sa folositi Producer and Consumer."

import com.Task_3.DataManager.DataProvider;
import com.Task_3.DataManager.FileManager;
import com.Task_3.ProducerConsumer.Consumer;
import com.Task_3.ProducerConsumer.Producer;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) {
        final int NUMBER_COUNT = 1_000_000;
        final int MAX_VALUE = 1_000;

        String filePath = System.getProperty("user.dir") + File.separator;

        String readFileName = "in.txt";
        String writeFileName = "out.txt";

        File readFile = FileManager.createNewFile(filePath, readFileName);
        File writeFile = FileManager.createNewFile(filePath, writeFileName);


        // Initiate readFile with 1 mil. numbers
        DataProvider.initiateRandomNumbersToFile(readFile, NUMBER_COUNT, MAX_VALUE);

        // Boolean to check when reading is done
        AtomicBoolean isDone = new AtomicBoolean(false);

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(NUMBER_COUNT);

        Thread producer = new Thread(new Producer(queue, readFile, isDone));
        Thread consumer = new Thread(new Consumer(queue, writeFile, isDone));

        producer.start();
        consumer.start();
    }

}

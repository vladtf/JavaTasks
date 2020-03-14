package com.task_3;

//"Se citeste dintr-un fisier (pe fiecare rand sunt numere < 3 cifre - fisierul poate avea si un milion de row-uri). " +
//        "Fisierul trebuie procesat astfel incat fiecare numar sufera cateva modificari (daca e mai mic de 100 este " +
//        "incrementat cu 1, daca e mai mare de 100 e decrementat cu 1). Noile valori trebuie salvate intr-un alt fisier." +
//        "As vrea sa folositi Producer and Consumer."

import com.task_3.dataManager.DataProvider;
import com.task_3.dataManager.FileManager;
import com.task_3.producerConsumer.Consumer;
import com.task_3.producerConsumer.Producer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {

        int NUMBER_COUNT = 1_000_000;
        final int MAX_VALUE = 1_000;

        String filePath = java.nio.file.Paths.get(".").toAbsolutePath() + File.separator + "data" + File.separator;


        String readFileName = "in.txt";
        String writeFileName = "out.txt";

        //TODO - very good that you created a file but I want that the name of the file to be given like an argument when the application is call,
        // and the file should exists in the same folder with this class;

        // Checks if there are at least 2 arguments when starting the app ( possible file names )
        if (args.length > 1) {
            readFileName = args[0];
            writeFileName = args[1];

            // If are given custom files then the path don't matter
            filePath = "";
        }

        File readFile;
        File writeFile;

        //TODO - what is happening if the IOException is thrown? What value can have readFile or writeFile in case the exception occurs?
        // I recommend to put all the steps which are using readFile and writeFile, inside the try block
        // Solved : return; ( finish the app when an error occurs )

        try {
            readFile = FileManager.createNewFile(filePath, readFileName);
            writeFile = FileManager.createNewFile(filePath, writeFileName);
        } catch (IOException e) { //TODO - ok - you already removed the exception FileNotFound!!!
            System.out.println("An error occurred while creating initializing read / write file! Cause :");
            e.printStackTrace();
            return;
        }

        // very nice that you generated the file, we can let like that because is important now how you implemented the threads
        // Please ask if a requirement is not clear, or not completely defined. In a real context, the client never knows what exactly he needs,
        // and to offer him what he wants we address a lot of questions :D
        // The communication is very important for both parts satisfaction
        // Please put more comments in the code

        // There was an issue with package name - Task_3 vs task_3

        // Checks whether the user wrote as arguments the file name or not ( generating new file only if the reading file is default )
        if (!filePath.equals("")) {
            // Initiate readFile with 1 mil. numbers
            DataProvider.initiateRandomNumbersToFile(readFile, NUMBER_COUNT, MAX_VALUE);

        }

        // Boolean to check when reading is done
        AtomicBoolean isDone = new AtomicBoolean(false);

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(NUMBER_COUNT);

        Thread producer = new Thread(new Producer(queue, readFile, isDone));
        Thread consumer = new Thread(new Consumer(queue, writeFile, isDone));

        producer.start();
        consumer.start();

        System.out.println("Reading from " + readFile.getAbsolutePath());
        System.out.println("Writing to " + writeFile.getAbsolutePath());

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully finished the task.");
    }
}

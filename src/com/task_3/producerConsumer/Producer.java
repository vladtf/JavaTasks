package com.task_3.producerConsumer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Producer implements Runnable {

    private BlockingQueue<Integer> queue;
    private File readFile;
    private AtomicBoolean isDone;

    public Producer(BlockingQueue<Integer> queue, File readFile, AtomicBoolean isDone) {
        this.queue = queue;
        this.readFile = readFile;
        this.isDone = isDone;
    }

    @Override
    public void run() {
        //TODO - is very good, but please don't let the FileReader unclosed;
        // you can put in the same try a new variable of FileReader file and both Scanner and FileReader will be closed;

        try (Scanner reader = new Scanner(new FileReader(readFile))) {
            while (reader.hasNext()) {
                String value = reader.next();
                int number;

                // Checking whether the values is convertible to an integer
                try {
                    number = Integer.parseInt(value);
                } catch (Exception e) {
                    System.out.println("Found not a number value : "+value);
                    continue;
                }

                if (number == 100) {
                    queue.add(number);
                } else {
                    queue.add(number < 100 ? number + 1 : number - 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // when task ( reading from file ) is done return true
        isDone.set(true);
    }

}

package com.utils.producerConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class NumberProducer implements Runnable {

    private BlockingQueue<Integer> queue;
    private File readFile;
    private AtomicBoolean isDone;
    private CountDownLatch latch;

    public NumberProducer(BlockingQueue<Integer> queue, File readFile, AtomicBoolean isDone, CountDownLatch latch) {
        this.queue = queue;
        this.readFile = readFile;
        this.isDone = isDone;
        this.latch = latch;
    }

    @Override
    public void run() {
        //TODO - is very good, but please don't let the FileReader unclosed;
        // you can put in the same try a new variable of FileReader file and both Scanner and FileReader will be closed;


        try (FileReader source = new FileReader(readFile)) {
            try (Scanner reader = new Scanner(source)) {
                while (reader.hasNext()) {
                    String value = reader.next();
                    int number;

                    // Checking whether the values is convertible to an integer
                    try {
                        number = Integer.parseInt(value);
                    } catch (Exception e) {
                        System.out.println("Found not a number value : " + value);
                        continue;
                    }

                    queue.add(number);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // when task ( reading from file ) is done return true
        isDone.set(true);

        if(latch!=null){
            // Decrement the number of running threads by 1
            latch.countDown();
        }
    }

}

package com.utils.producerConsumer;

import java.io.File;
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

    /**
     * @param queue    The queue where numbers will be saved
     * @param readFile File to read from
     * @param isDone   A boolean to check if reading is done
     * @param latch    Counter of threads opened ( countdown when reading has finished )
     */
    public NumberProducer(BlockingQueue<Integer> queue, File readFile, AtomicBoolean isDone, CountDownLatch latch) {
        this.queue = queue;
        this.readFile = readFile;
        this.isDone = isDone;
        this.latch = latch;
    }

    @Override
    public void run() {
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
        isDone.getAndSet(true);

        if(latch!=null){
            // Decrement the number of running threads by 1
            latch.countDown();
        }
    }

}

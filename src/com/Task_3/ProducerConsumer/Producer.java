package com.Task_3.ProducerConsumer;

import javax.print.attribute.standard.RequestingUserName;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
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
        try (Scanner reader = new Scanner(new FileReader(readFile))) {
            while (reader.hasNext()) {
                int value = reader.nextInt();

                if (value == 100) {
                    queue.add(value);
                } else {
                    queue.add(value < 100 ? value + 1 : value - 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // when task ( reading from file ) is done return true
        isDone.set(true);
    }

}

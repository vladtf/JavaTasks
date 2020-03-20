package com.utils.producerConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class FileNumberConsumer implements Runnable {

    private BlockingQueue<Integer> queue;
    private File readFile;
    private AtomicBoolean isDone;

    public FileNumberConsumer(BlockingQueue<Integer> queue, File readFile, AtomicBoolean isDone) {
        this.queue = queue;
        this.readFile = readFile;
        this.isDone = isDone;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(readFile)) {
            while (!isDone.get() || !queue.isEmpty()) {
                Integer value = queue.poll(10, MILLISECONDS);
                if (value != null) {

                    if (value == 100) {
                        writer.println(value);
                    } else {
                        writer.println(value < 100 ? value + 1 : value - 1);
                    }
                }
            }
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

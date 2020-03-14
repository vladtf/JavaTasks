package com.task_3.producerConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Consumer implements Runnable {

    private BlockingQueue<Integer> queue;
    private File readFile;
    private AtomicBoolean isDone;

    public Consumer(BlockingQueue<Integer> queue, File readFile, AtomicBoolean isDone) {
        this.queue = queue;
        this.readFile = readFile;
        this.isDone = isDone;
    }

    @Override
    public void run() {
        try (PrintWriter writer = new PrintWriter(readFile)) {
            while (!isDone.get()) {
                Integer value = queue.poll(10, MILLISECONDS);
                if (value != null) {
                    writer.println(value);
                }
            }
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

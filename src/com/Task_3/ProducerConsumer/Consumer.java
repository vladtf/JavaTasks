package com.Task_3.ProducerConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

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
                try {
                    writer.println(queue.poll(10, TimeUnit.MILLISECONDS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

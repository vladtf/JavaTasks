package com.utils.producerConsumer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseNumberConsumer implements Runnable {

    private BlockingQueue<Integer> queue;
    private AtomicBoolean isDone;
    private CountDownLatch latch;

    private Connection connection;
    private String fileName;

    public DataBaseNumberConsumer(BlockingQueue<Integer> queue,
                                  AtomicBoolean isDone,
                                  Connection connection,
                                  String fileName,
                                  CountDownLatch latch) {
        this.queue = queue;
        this.isDone = isDone;
        this.latch = latch;
        this.connection = connection;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        // Set sum of all numbers to 0
        AtomicInteger sum = new AtomicInteger();
        while (!queue.isEmpty() || !isDone.get()){
            try {
                Integer value = queue.poll(10, TimeUnit.MILLISECONDS);
                if(value!=null){
                    sum.addAndGet(value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Sql statement for insert into FilesStats file name and sum of numbers in file
        try (PreparedStatement statement = connection.prepareStatement("insert into FilesStats (FileName, Sum) values (?, ?)")) {

            // Add parameters to statement
            statement.setString(1,fileName);
            statement.setInt(2, sum.get());

            statement.execute();

            System.out.println("Finished task for file : "+fileName);

            // Decrement the number of running threads by 1
            latch.countDown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
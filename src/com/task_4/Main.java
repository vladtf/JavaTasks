package com.task_4;

import com.utils.dataManager.FileManager;
import com.utils.producerConsumer.DataBaseNumberConsumer;
import com.utils.producerConsumer.NumberProducer;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    //region DataBase configuration data
    private static final String URL = "jdbc:mysql://localhost/";
    private static final String DATABASE_NAME = "NUMBER_DATA";
    private static final String TABLE_NAME = "FilesStats";
    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "";
    //endregion

    public static void main(String[] args) {

        // Check if there is any file name given as argument at app start
        if (args.length == 0) {
            System.out.println("No file arguments given.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER_NAME, USER_PASSWORD)) {
            truncateTable(connection, TABLE_NAME);

            // CountDown for tracking when all supposed thread ( args*2 : 2 threads for each file )
            CountDownLatch latch = new CountDownLatch(args.length * 2);

            for (int i = 0; i < args.length; i++) {

                String filePath = "";
                String fileName = args[i];
                File file = FileManager.createNewFile(filePath, fileName);

                BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
                AtomicBoolean isDone = new AtomicBoolean(false);

                Thread producer = new Thread(new NumberProducer(queue, file, isDone, latch));
                Thread consumer = new Thread(new DataBaseNumberConsumer(queue, isDone, connection, fileName, latch));

                producer.start();
                consumer.start();
            }

            // Wait until all threads are done ( until the count == zero )
            latch.await();
            System.out.println("Finished all tasks!");

        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void truncateTable(Connection connection, String tableName) throws SQLException {
        connection.createStatement().execute("truncate table " + tableName);
    }
}

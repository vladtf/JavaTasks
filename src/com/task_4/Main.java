package com.task_4;

import com.utils.dataManager.FileManager;
import com.utils.dataManager.FileManager.FileManagerVariants;
import com.utils.producerConsumer.DataBaseNumberConsumer;
import com.utils.producerConsumer.NumberProducer;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
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
            System.exit(-1);
        }

        try (Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER_NAME, USER_PASSWORD)) {
            truncateTable(connection, TABLE_NAME);

            // CountDown for tracking when all supposed thread ( args*2 : 2 threads for each file )
            CountDownLatch latch = new CountDownLatch(args.length * 2);

            String filePath = "";
            for (int i = 0; i < args.length; i++) {

                String fileName = args[i];
                File file = FileManager.createNewFile(filePath, fileName, FileManagerVariants.FILE_ALREADY_EXISTS);

                BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
                AtomicBoolean isDone = new AtomicBoolean(false);

                Thread producer = new Thread(new NumberProducer(queue, file, isDone, latch));
                Thread consumer = new Thread(new DataBaseNumberConsumer(queue, isDone, connection, fileName, TABLE_NAME, latch));

                producer.start();
                consumer.start();
            }

            // Wait until all threads are done ( until the count == zero or passed 10 seconds)
            if (latch.await(10, TimeUnit.SECONDS) == false) {
                System.out.println("Finished " + latch.getCount() + " out of " + args.length * 2 + ".");
                System.out.println("Process timed out. Restart the application.");
            }
            System.out.println("Finished all tasks!");

            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from " + TABLE_NAME)) {
                    displayDataFromTableInConsole(resultSet);

                    resultSet.beforeFirst();

                    MainView mainView = new MainView(resultSet);
                    mainView.start();
                }
            }

        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void displayDataFromTableInConsole(ResultSet resultSet) throws SQLException {
        System.out.println("\n\n Table results : \n");
        System.out.println("FileName | Sum");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("FileName") + " | " + resultSet.getString("Sum"));
        }
    }


    private static ResultSet getAllData(Connection connection) throws SQLException {
        // Not closeable inside a method
        Statement statement = connection.createStatement();
        return statement.executeQuery("select * from " + TABLE_NAME);
    }

    private static void truncateTable(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("truncate table " + tableName);
        }
    }
}

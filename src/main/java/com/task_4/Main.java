package com.task_4;

import com.models.FileModel;
import com.utils.dao.FileSqlDAO;
import com.utils.dao.JavaTasksDAO;
import com.utils.dataManager.FileManager;
import com.utils.dataManager.FileManager.FileManagerVariants;
import com.utils.producerConsumer.DataBaseNumberConsumer;
import com.utils.producerConsumer.NumberProducer;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
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

        String filePath;
        // Check if there is any property "path"
        if ((filePath = System.getProperty("path")) != null) {
            filePath += File.separator;
        } else {
            filePath = "";
        }

        try (Connection connection = DriverManager.getConnection(URL + DATABASE_NAME, USER_NAME, USER_PASSWORD)) {
            truncateTable(connection, TABLE_NAME);

            JavaTasksDAO<FileModel> fileSqlDAO = new FileSqlDAO(connection);

            // CountDown for tracking when all supposed thread ( args*2 : 2 threads for each file )
            CountDownLatch latch = new CountDownLatch(args.length * 2);

            for (int i = 0; i < args.length; i++) {
                String fileName = args[i];
                try {
                    File readFile = FileManager.createNewFile(filePath, fileName, FileManagerVariants.FILE_ALREADY_EXISTS);

                    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
                    AtomicBoolean isDone = new AtomicBoolean(false);

                    Thread producer = new Thread(new NumberProducer(queue, readFile, isDone, latch));
                    Thread consumer = new Thread(new DataBaseNumberConsumer(queue, isDone, latch, fileSqlDAO, fileName));

                    producer.start();
                    consumer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }

            // Wait until all threads are done ( until the count == zero or passed 10 seconds)
            if (!latch.await(10, TimeUnit.SECONDS)) {
                System.out.println("Finished " + latch.getCount() + " out of " + args.length * 2 + ".");
                System.out.println("Process timed out. Restart the application.");
            }
            System.out.println("Finished all tasks!");

            List<FileModel> files = fileSqlDAO.findAll();
            files.sort((model, otherFileModel) -> {
                return otherFileModel.compareTo(model);
            });
            displayDataFromTableInConsole(files);

            MainView mainView = new MainView(files);
            mainView.start();

            System.gc();
//            try (Statement statement = connection.createStatement()) {
//                try (ResultSet resultSet = statement.executeQuery("select * from " + TABLE_NAME)) {
//                    displayDataFromTableInConsole(resultSet);
//
//                    resultSet.beforeFirst();
//
//                    MainView mainView = new MainView(resultSet);
//                    mainView.start();
//                }
//            }

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void displayDataFromTableInConsole(List<FileModel> files) {
        System.out.println("\n\n Table results : \n");
        for (FileModel fileModel : files) {
            System.out.println(fileModel);
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
        try (PreparedStatement statement = connection.prepareStatement("truncate table " + tableName)) {
            statement.execute();
            System.out.println("Truncated table : " + tableName);
        }
    }
}

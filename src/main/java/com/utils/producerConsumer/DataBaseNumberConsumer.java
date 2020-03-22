package com.utils.producerConsumer;

import com.task_4.models.FileModel;
import com.utils.dao.JavaTasksDAO;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataBaseNumberConsumer implements Runnable {

    private BlockingQueue<Integer> queue;
    private AtomicBoolean isDone;
    private CountDownLatch latch;
    private JavaTasksDAO<FileModel> javaTasksDAO;
    private String fileName;

    public DataBaseNumberConsumer(BlockingQueue<Integer> queue,
                                  AtomicBoolean isDone,
                                  CountDownLatch latch,
                                  JavaTasksDAO<FileModel> javaTasksDAO,
                                  String fileName) {
        this.queue = queue;
        this.isDone = isDone;
        this.latch = latch;
        this.javaTasksDAO = javaTasksDAO;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        int sum = 0;
        while (!queue.isEmpty() || !isDone.get()){
            try {
                Integer value = queue.poll(10, TimeUnit.MILLISECONDS);
                if (value != null) {
                    sum += value;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FileModel fileModel = new FileModel();

        fileModel.sum.setValue(sum);
        fileModel.fileName.setValue(fileName);

        if (javaTasksDAO.create(fileModel)) {
            System.out.println("Finished task for file : " + fileName);
        } else {
            System.out.println("Could not insert : " + fileName);
        }

        // Decrement the number of running threads by 1
        latch.countDown();

//        // Sql statement for insert into FilesStats file name and sum of numbers in file
//        try (PreparedStatement statement = connection.prepareStatement("insert into " + tableName + " (FileName, Sum) values (?, ?)")) {
//
//            // Add parameters to statement
//            statement.setString(1, fileModel.);
//            statement.setInt(2, sum);
//
//            statement.execute();
//
//            System.out.println("Finished task for file : " + fileName);
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//        }
    }
}

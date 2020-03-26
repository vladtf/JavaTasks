package com.utils.dao;

import com.models.FileModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileSqlDAO implements JavaTasksDAO<FileModel> {

    private static final String TABLE_NAME = "FilesStats";
    private Connection connection;

    public FileSqlDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(FileModel model) {
        int rowsInserted = 0;
        try (PreparedStatement insertStatement = connection.prepareStatement("insert into " + TABLE_NAME + " (FileName, Sum) values (?, ?) ;")) {
            insertStatement.setString(1, model.getFileName());
            insertStatement.setInt(2, model.getSum());
            rowsInserted = insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsInserted == 1;
    }

    @Override
    public FileModel findById(int id) {
        FileModel foundFile = null;
        try (PreparedStatement selectStatement = connection.prepareStatement("select * from " + TABLE_NAME + " where id = ? ;")) {
            selectStatement.setInt(1, id);

            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundFile = new FileModel();

                    foundFile.setFileId(resultSet.getInt("FileId"));
                    foundFile.setFileName(resultSet.getString("FileName"));
                    foundFile.setSum(resultSet.getInt("Sum"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundFile;
    }

    @Override
    public List<FileModel> findAll() {
        List<FileModel> filesFound = new ArrayList<>();
        try (PreparedStatement selectStatement = connection.prepareStatement("select * from " + TABLE_NAME + " ;")) {
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    FileModel file = new FileModel();

                    file.setFileId(resultSet.getInt("FileId"));
                    file.setFileName(resultSet.getString("FileName"));
                    file.setSum(resultSet.getInt("Sum"));

                    filesFound.add(file);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filesFound;
    }

    @Override
    public boolean update(FileModel model) {
        int rowsUpdated = 0;

        try (PreparedStatement updateStatement = connection.prepareStatement("update " + TABLE_NAME + " set FileName = ? , Sum = ? where FileId = ? ;")) {
            updateStatement.setString(1, model.getFileName());
            updateStatement.setInt(2, model.getSum());
            updateStatement.setInt(3, model.getFileId());

            rowsUpdated = updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsUpdated == 1;
    }

    @Override
    public boolean delete(FileModel model) {
        int rowsDeleted = 0;

        try (PreparedStatement deleteStatement = connection.prepareStatement("delete  from " + TABLE_NAME + " where FileId = ?")) {
            deleteStatement.setInt(1, model.getFileId());

            rowsDeleted = deleteStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsDeleted == 1;
    }
}

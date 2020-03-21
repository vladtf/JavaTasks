package com.utils.dataManager;

import com.task_4.models.FileModel;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class TableModelManager {

    public static TableModel resultSetToTableModel(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector<Object> columnNames = new Vector<>();

            // Get Columns name
            for (int i = 0; i < numberOfColumns; i++) {
                columnNames.addElement(metaData.getColumnLabel(i + 1));
            }

            // Get all rows
            Vector<Vector<Object>> rows = new Vector<>();

            while (resultSet.next()) {
                Vector<Object> newRow = new Vector<>();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(resultSet.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (SQLException e) {
            e.printStackTrace();

            return new DefaultTableModel();
        }
    }

    public static TableModel listToTableModel(List<FileModel> items, String[] columnsNames) {
        Object[][] data = items.stream().map(file -> {
            Object[] row = new Object[columnsNames.length];
            row[0] = file.getFileId();
            row[1] = file.getFileName();
            row[2] = file.getSum();

            return row;
        }).toArray(Object[][]::new);

        return new DefaultTableModel(data, columnsNames);
    }
}

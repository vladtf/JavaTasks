package com.utils.dataManager;

import com.task_4.models.FileModel;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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

    // TODO: 21-Mar-20
    //  Make method to be more generic using List<T>
    //  or using an interface IModel
    public static TableModel listToTableModel(List<FileModel> items) {
        String[] propertiesNames = items.get(0).getPropertiesNames().toArray(String[]::new);

        Object[][] data = new Object[items.size()][propertiesNames.length];
        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> properties = items.get(i).getProperties();
            for (int j = 0; j < propertiesNames.length; j++) {
                data[i][j] = properties.get(propertiesNames[j]);
            }
        }
        return new DefaultTableModel(data, propertiesNames);
//        Object[][] data = items.stream().map(IModel::getProperties).toArray(Object[][]::new);
//        String[] columnsNames = items.get(0).getPropertiesNames();
//        return new DefaultTableModel(data, columnsNames);

    }

    private static <T> Object getFieldValue(T item, Field field) {
        try {
            field.setAccessible(true);
            return field.get(item);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

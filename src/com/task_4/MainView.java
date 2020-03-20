package com.task_4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class MainView extends JFrame {

    private GridLayout root;
    private ResultSet resultSet;

    public MainView(ResultSet resultSet) {
        super("Result Data");
        this.resultSet = resultSet;
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setupLayout();
        addHeader();
        addTable();
    }

    private static TableModel resultSetToTableModel(ResultSet resultSet) {
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

    private void addHeader() {
        JLabel label = new JLabel("Result");
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        label.setHorizontalAlignment(JLabel.CENTER);

        add(label);
    }

    private void setupLayout() {
        root = new GridLayout(2, 1);
        setLayout(root);
    }

    private void addTable() {
        JTable table = new JTable(resultSetToTableModel(resultSet));
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane pane = new JScrollPane(table);
        add(pane);
    }

    public void start() {
        setVisible(true);
    }
}

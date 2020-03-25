package com.tasks.task_4;

import com.models.FileModel;
import com.utils.dataManager.TableModelManager;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.List;

public class MainView extends JFrame {

    private GridLayout root;
    private ResultSet resultSet;
    private List<FileModel> files;

    public MainView(ResultSet resultSet) {
        super("Result Data");
        this.resultSet = resultSet;

        initializeComponents();
    }

    public MainView(List<FileModel> files) {
        super("Result Data");
        this.files = files;

        initializeComponents();
    }

    private void initializeComponents() {
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setupLayout();
        addHeader();
        addTable();
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
//        JTable table = new JTable(resultSetToTableModel(resultSet));
        JTable table = new JTable(TableModelManager.listToTableModel(files));
        table.setFont(new Font("Serif", Font.PLAIN, 20));

        JScrollPane pane = new JScrollPane(table);
        add(pane);
    }

    public void start() {
        setVisible(true);
    }
}

package com.task_4.models;

import java.text.MessageFormat;

public class FileModel {
    private int fileId;
    private String fileName;
    private int sum;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return MessageFormat.format("File : {0} - {1}  , sum : {2}", fileId, fileName, sum);
    }
}

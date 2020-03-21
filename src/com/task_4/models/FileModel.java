package com.task_4.models;

import java.text.MessageFormat;

public class FileModel extends ModelBase {
    private String fileName;
    private int sum;

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
        return MessageFormat.format("File : {0} - {1}  , sum : {2}", getId(), getFileName(), getSum());
    }

    // Used reflection instead
//    @Override
//    public String[] getPropertiesNames() {
//        return new String[]{"Id", "FileName", "Sum"};
//    }
//
//    public Object[] getProperties() {
//        return new Object[]{fileId, fileName, sum};
//    }
}

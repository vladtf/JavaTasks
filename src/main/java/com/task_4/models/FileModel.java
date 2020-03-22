package com.task_4.models;

import java.text.MessageFormat;

public class FileModel extends ModelBase {
    public Property<Integer> fileId;
    public Property<String> fileName;
    public Property<Integer> sum;

    public FileModel() {
        fileId = new Property<>(0, "FileId");
        fileName = new Property<>("", "FileName");
        sum = new Property<>(0, "Sum");
    }
    //    public int getFileId() {
//        return fileId;
//    }
//
//    public void setFileId(Integer fileId) {
//        if (!fileId.equals(this.fileId)) {
//            this.fileId = fileId;
//            onPropertyChanged("FileId", fileId);
//        }
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        if (!fileName.equals(this.fileName)) {
//            this.fileName = fileName;
//            onPropertyChanged("FileName", fileName);
//        }
//    }
//
//    public int getSum() {
//        return sum;
//    }
//
//    public void setSum(Integer sum) {
//        if (!sum.equals(this.sum)) {
//            this.sum = sum;
//            onPropertyChanged("Sum", sum);
//        }
//    }

    @Override
    public String toString() {
        return MessageFormat.format("File : {0} - {1}  , sum : {2}",
                fileId.getValue(), fileName.getValue(), sum.getValue());
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

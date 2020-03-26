package com.models;

import com.property.Property;

import java.text.MessageFormat;
public class FileModel extends ModelBase implements Comparable<FileModel> {
    private Property<Integer> fileId;
    private Property<String> fileName;
    private Property<Integer> sum;

    public FileModel() {
        this(0, "", 0);
    }

    //TODO: 27-Mar-20 self-instantiable ( using fields names from declare fields that are type of Property
    // or by adding name when calling get properties )
    public FileModel(Integer fileId, String fileName, Integer sum) {
        this.fileId = new Property<>(fileId, "FileId");
        this.fileName = new Property<>(fileName, "FileName");
        this.sum = new Property<>(sum, "Sum");
    }

    public Integer getFileId() {
        return fileId.getValue();
    }

    public void setFileId(Integer fileId) {
        this.fileId.setValue(fileId);
    }

    public String getFileName() {
        return fileName.getValue();
    }

    public void setFileName(String fileName) {
        this.fileName.setValue(fileName);
    }

    public Integer getSum() {
        return sum.getValue();
    }

    public void setSum(Integer sum) {
        this.sum.setValue(sum);
    }

    @Override
    public String toString() {
        return MessageFormat.format("File : id : {0} - {1}  , sum : {2}",
                fileId.getValue(), fileName.getValue(), sum.getValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileModel) {
            FileModel otherModel = ((FileModel) obj);
            return otherModel.fileId.getValue().equals(this.fileId.getValue());
        }
        return false;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("Item was deleted : " + this.toString());
        super.finalize();
    }

    @Override
    public int compareTo(FileModel otherFileModel) {
        return this.sum.getValue().compareTo(otherFileModel.sum.getValue());
    }

    @Override
    public int hashCode() {
        return fileId.getValue();
    }

}

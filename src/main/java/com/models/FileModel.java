package com.models;

import com.models.property.Property;

import java.text.MessageFormat;
public class FileModel extends ModelBase implements Comparable<FileModel> {
    public Property<Integer> fileId;
    public Property<String> fileName;
    public Property<Integer> sum;

    public FileModel() {
        this(0, "", 0);
    }

    public FileModel(Integer fileId, String fileName, Integer sum) {
        this.fileId = new Property<>(fileId, "FileId");
        this.fileName = new Property<>(fileName, "FileName");
        this.sum = new Property<>(sum, "Sum");
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

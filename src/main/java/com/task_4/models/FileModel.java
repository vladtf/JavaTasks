package com.task_4.models;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;

public class FileModel extends ModelBase implements Comparable<FileModel> {
    public Property<Integer> fileId;
    public Property<String> fileName;
    public Property<Integer> sum;

    // TODO: 22-Mar-20 self-instantiable constructor
    public FileModel() {
        fileId = new Property<>(0, "FileId");
        fileName = new Property<>("", "FileName");
        sum = new Property<>(0, "Sum");

        fileId.addPropertyChangedListener(eventArgs -> System.out.println("Property was changed : " + fileId.getPropertyName()));
        fileName.addPropertyChangedListener(eventArgs -> System.out.println("Property was changed : " + fileName.getPropertyName()));
        sum.addPropertyChangedListener(eventArgs -> System.out.println("Property was changed : " + sum.getPropertyName()));

        fileId.addPropertyChangedListener(eventArgs -> notifyOfPropertyChanged(fileId.getPropertyName(), eventArgs));
        fileName.addPropertyChangedListener(eventArgs -> notifyOfPropertyChanged(fileName.getPropertyName(), eventArgs));
        sum.addPropertyChangedListener(eventArgs -> notifyOfPropertyChanged(sum.getPropertyName(), eventArgs));
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
    public int compareTo(@NotNull FileModel otherFileModel) {
        return this.sum.getValue().compareTo(otherFileModel.sum.getValue());
    }

    @Override
    public int hashCode() {
        return fileId.getValue();
    }

}

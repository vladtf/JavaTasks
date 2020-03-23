package com.task_4.models;


import java.util.EventListener;

public class Property<T> {
    private T value;
    private String propertyName;

    private EventListener onPropertyChanged;

    public Property(T value, String propertyName) {
        this.value = value;
        this.propertyName = propertyName;
    }

    public boolean setValue(T value) {
        if (value.equals(this.value)) {
            return false;
        }

        this.value = value;
        return true;
    }

    public T getValue() {
        return value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void addPropertyChangedListener() {
    }

    private void notifyOfPropertyChanged() {
        if (onPropertyChanged != null) {
//            onPropertyChanged.invoke();
        }
    }
}

package com.task_4.models;


import org.springframework.beans.factory.annotation.Autowired;

public class Property<T> {
    @Autowired
    private T value;

    private String propertyName;

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
}

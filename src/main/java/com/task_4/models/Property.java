package com.task_4.models;


import java.util.EventListener;

/**
 * A class to work with fields as properties ( equivalent to c# properties )
 *
 * @param <T> Type of value vad is stored
 */
public class Property<T> {
    private T value;
    private String propertyName;

    private EventListener onPropertyChanged;

    /**
     * @param value        Initial value
     * @param propertyName The name of property
     */
    public Property(T value, String propertyName) {
        this.value = value;
        this.propertyName = propertyName;
    }

    /**
     * @param value New value
     * @return Return <code>true</code> if new value was set; <code>false</code> if new value == current value
     */
    public boolean setValue(T value) {
        if (value.equals(this.value)) {
            return false;
        }

        this.value = value;
        return true;
    }

    /**
     * @return return the current value
     */
    public T getValue() {
        return value;
    }

    /**
     * @return The name of current property
     */
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

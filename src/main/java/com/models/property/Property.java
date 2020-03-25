package com.models.property;


import com.models.listener.PropertyChangedEvent;

import java.util.function.Consumer;

/**
 * A class to work with fields as properties ( equivalent to c# properties )
 *
 * @param <T> Type of value vad is stored
 */
public class Property<T> {
    private T value;
    private String propertyName;

    private PropertyChangedEvent<T> onPropertyChanged;

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
        notifyOfPropertyChanged(this);

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


    public void addPropertyChangedListener(Consumer<Property<T>> listener) {
        if (onPropertyChanged == null) {
            onPropertyChanged = new PropertyChangedEvent<>();
        }
        onPropertyChanged.addListener(listener);
    }

    private void notifyOfPropertyChanged(Property<T> value) {
        if (onPropertyChanged != null) {
            onPropertyChanged.broadcast(this);
        }
    }
}

package com.property;


import com.observer.Observable;

import java.text.MessageFormat;
import java.util.function.Consumer;

/**
 * A class to work with fields as properties ( equivalent to c# properties )
 *
 * @param <T> Type of value that is stored
 */
public class Property<T> implements Observable<T> {
    /**
     * Value stored
     */
    private T value;

    /**
     * Name of property
     */
    private String propertyName;

    /**
     * Events called if a property has changed
     */
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
     * Set value of property and notifies if the property has changed
     *
     * @param value New value
     * @return Return <code>true</code> if new value was set; <code>false</code> if new value == current value
     */
    public T setValue(T value) {
        T oldValue = this.value;

        if (!value.equals(this.value)) {
            this.value = value;
            notifyOfPropertyChanged(this);
        }

        return oldValue;
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

    @Override
    public boolean addPropertyChangedListener(Consumer<Property<T>> listener) {
        if (onPropertyChanged == null) {
            onPropertyChanged = new PropertyChangedEvent<>();
        }
        return onPropertyChanged.addListener(listener);
    }

    @Override
    public void notifyOfPropertyChanged(Property<T> value) {
        if (onPropertyChanged != null) {
            onPropertyChanged.broadcast(this);
        }
    }

    @Override
    public String toString() {
        return MessageFormat.format("Property : {0}, with value = {1}", propertyName, value);
    }
}

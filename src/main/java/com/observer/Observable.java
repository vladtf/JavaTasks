package com.observer;

import com.property.Property;

import java.util.function.Consumer;


/**
 * An interface to work with objects that could change their properties
 * and notifies about changing by calling an listener
 *
 * @param <T> Type of object
 */
public interface Observable<T> {

    /**
     * Called when a property has changed ( could be called on a setter method )
     *
     * @param property Property that has changed
     */
    void notifyOfPropertyChanged(Property<T> property);


    /**
     * @param listener Listener that would be called inside the notifying method
     * @return <code>true</code> if successfully added listener; <code>false</code> if couldn't add listener
     */
    boolean addPropertyChangedListener(Consumer<Property<T>> listener);

    /**
     * Remove all the listeners.
     */
    void removeAlPropertyChangedListeners();
}

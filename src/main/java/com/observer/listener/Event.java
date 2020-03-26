package com.observer.listener;

import java.util.function.Consumer;

/**
 * An interface to work with events ( inspired from C# events / delegates )
 *
 * @param <T> Type of arguments
 */
public interface Event<T> {
    /**
     * Add an listener to list of listeners
     *
     * @param listener Action to add ( working with lambda : addListener( listener -> SomeMethod() );
     * @return Return <code>true</code> if successfully added listener; <code>false</code> if couldn't add listener
     */
    boolean addListener(Consumer<T> listener);

    /**
     * Call all the listeners from a list
     *
     * @param args Arguments to pass on calling an event
     */
    void broadcast(T args) throws EventException;
}

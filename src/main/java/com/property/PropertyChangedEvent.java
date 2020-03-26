package com.property;


import com.observer.listener.Event;
import com.observer.listener.EventException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class PropertyChangedEvent<T> implements Event<Property<T>> {

    private Set<Consumer<Property<T>>> listeners = new HashSet<>();

    @Override
    public boolean addListener(Consumer<Property<T>> listener) {
        return listeners.add(listener);
    }

    @Override
    public void broadcast(Property<T> args) throws EventException {
        for (Consumer<Property<T>> listener : listeners) {
            try {
                listener.accept(args);
            } catch (Exception e) {
                throw new EventException("Failed to perform action : " + listener.toString());
            }
        }
    }
}

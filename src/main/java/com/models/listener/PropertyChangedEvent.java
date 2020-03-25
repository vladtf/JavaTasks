package com.models.listener;


import com.models.property.Property;

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
    public void broadcast(Property<T> args) {
        listeners.forEach(x -> x.accept(args));

    }
}

package com.models.observer;

import com.models.listener.PropertyChangedEvent;
import com.models.property.Property;

import java.util.HashMap;
import java.util.function.Consumer;

public class ObservableHashMap<T> extends HashMap<String, T> implements Observable<T> {
    PropertyChangedEvent<T> onPropertyChanged;

    public T put(Property<T> property) throws ObservableMapException {
        if (property.addPropertyChangedListener(this::notifyOfPropertyChanged)) {
            return super.put(property.getPropertyName(), property.getValue());
        }

        throw new ObservableMapException("Failed to put property <" + property + "> in map.");
    }

    @Override
    public void notifyOfPropertyChanged(Property<T> property) {
        super.put(property.getPropertyName(), property.getValue());
    }

    @Override
    public boolean addPropertyChangedListener(Consumer<Property<T>> listener) {
        if (onPropertyChanged == null) {
            onPropertyChanged = new PropertyChangedEvent<>();
        }
        return onPropertyChanged.addListener(listener);
    }
}

package com.models.property;

import java.util.HashMap;

public class ObservableHashMap<T> extends HashMap<String, T> {
    public T put(Property<T> property) throws ObservableMapException {
        if (property.addPropertyChangedListener(this::notifyOfPropertyChanged)) {
            return super.put(property.getPropertyName(), property.getValue());
        }

        throw new ObservableMapException("Failed to put property <" + property + "> in map.");
    }

    public void notifyOfPropertyChanged(Property<T> property) {
        super.put(property.getPropertyName(), property.getValue());
    }
}

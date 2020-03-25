package com.models;

import com.models.property.ObservableHashMap;
import com.models.property.Property;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ModelBase {
    private Map<String, Object> properties;

    // Not realizable without reference parameter
    protected <T> boolean SetProperty(AtomicReference<T> property, T value) {
        if (property.get() != null && property.get().equals(value)) {
            return false;
        }

        property.set(value);
        return true;
    }




    /**
     * Get properties withing a model class
     *
     * @return The map of all properties
     */
    public Map<String, Object> getProperties() {
        if (properties != null) {
            return properties;
        }

        properties = new ObservableHashMap<>();

        Class<?> currentClass = getClass();
        do {
            Arrays.stream(currentClass.getDeclaredFields())
                    .filter(field -> Property.class.isAssignableFrom(field.getType()))
                    .forEach(field -> {
                        try {
                            String propertyName = field.getName();
                            if (field.get(this) instanceof Property) {
                                Property<Object> property = ((Property) field.get(this));

                                properties.put(property.getPropertyName(), property.getValue());
                            }
                        } catch (IllegalAccessException | ClassCastException e) {
                            e.printStackTrace();
                        }
                    });

        } while ((currentClass = currentClass.getSuperclass()) != null);

        return properties;
    }

    public Set<String> getPropertiesNames() {
        return getProperties().keySet();
    }

}

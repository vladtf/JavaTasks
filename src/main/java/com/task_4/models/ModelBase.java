package com.task_4.models;

import com.task_4.events.EventArgs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ModelBase {
    private Map<String, Object> properties;

    // Not realizable without reference parameter
    protected <T> boolean SetProperty(AtomicReference<T> property, T value) {
        if (property.get() != null && property.get().equals(value)) {
            return false;
        }

        property.set(value);
        return true;
    }

    // TODO: 22-Mar-20 Event NotifyOfPropertyChanged
    protected void notifyOfPropertyChanged(String propertyName, EventArgs eventArgs) {
        if (properties != null) {
            properties.put(propertyName, eventArgs.getValue());
        }
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

        properties = new HashMap<>();

        Class<?> currentClass = getClass();
        do {
            Arrays.stream(currentClass.getDeclaredFields())
                    .filter(field -> Property.class.isAssignableFrom(field.getType()))
                    .forEach(field -> {
                        try {
                            String propertyName = field.getName();
                            Property<Object> property = ((Property) field.get(this));

                            properties.put(propertyName, property.getValue());
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

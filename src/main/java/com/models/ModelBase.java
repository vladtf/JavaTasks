package com.models;

import com.observer.ObservableHashMap;
import com.property.Property;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public abstract class ModelBase {

    /**
     * Map of all properties inside the model
     */
    private ObservableHashMap<Object> properties;

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
                                Property property = ((Property) field.get(this));

                                properties.put(property.getPropertyName(), property.getValue());
                            }
                        } catch (IllegalAccessException | ClassCastException e) {
                            e.printStackTrace();
                        }
                    });

        } while ((currentClass = currentClass.getSuperclass()) != null);

        return properties;
    }


    /**
     * Call getProperties to instantiate properties map and get .keySet() - name of al properties;
     *
     * @return Name of all properties from a model
     */
    public Set<String> getPropertiesNames() {
        return getProperties().keySet();
    }

}

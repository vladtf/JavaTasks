package com.task_4.models;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    protected void onPropertyChanged(String propertyName, Object newValue) {
        if (properties != null) {
            properties.put(propertyName, newValue);
        }
    }

    public Map<String, Object> getProperties() {
        if (properties != null) {
            return properties;
        }

        properties = new HashMap<>();

        Class<?> currentClass = getClass();

        while (true) {
            // fields excepting 'properties'
            Field[] ownedFields = Arrays.stream(currentClass.getDeclaredFields())
                    .filter(field -> !field.getName().equals("properties"))
                    .toArray(Field[]::new);

            for (Field field : ownedFields) {
                try {
                    String propertyName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    String getterName = "get" + propertyName;
                    Method fieldGetter = currentClass.getDeclaredMethod(getterName);
                    Object propertyValue = fieldGetter.invoke(this);

                    properties.put(propertyName, propertyValue);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if ((currentClass = currentClass.getSuperclass()) == null) {
                break;
            }
        }
        return properties;
    }

    public Set<String> getPropertiesNames() {
        return getProperties().keySet();
    }

}

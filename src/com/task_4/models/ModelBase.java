package com.task_4.models;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModelBase {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<>();

        Class<?> currentClass = getClass();

        while (true) {
            Field[] ownedFields = currentClass.getDeclaredFields();

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

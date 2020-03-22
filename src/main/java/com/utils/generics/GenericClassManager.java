package com.utils.generics;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericClassManager {
    public static <T> Object[] getProperties(T instance) {
        List<Method> getters = Arrays.stream(instance.getClass().getMethods())
                .filter(method -> method.getName().startsWith("get") && !method.getReturnType().equals(Void.class) && !method.getName().equals("getClass"))
                .collect(Collectors.toList());

        return null;
    }
}

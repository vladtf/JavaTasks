package com.property;

import com.observer.listener.EventException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.function.Consumer;

class PropertyTest {


    private Property<Integer> myProperty;

    @Test
    void setValue() {
        myProperty = new Property<>(1, "myProperty");

        Assertions.assertEquals((long) myProperty.setValue(1), 1);
        Assertions.assertEquals((long) myProperty.setValue(0), 1);
        Assertions.assertEquals((long) myProperty.setValue(-1), 0);
        Assertions.assertEquals((long) myProperty.setValue(Integer.MAX_VALUE), -1);
        Assertions.assertEquals((long) myProperty.setValue(Integer.MIN_VALUE), Integer.MAX_VALUE);
    }

    @Test
    void getValue() {
        int actualValue = 1;
        myProperty = new Property<>(actualValue, "myProperty");

        Assertions.assertEquals((long) myProperty.getValue(), actualValue);

        actualValue = 5;
        myProperty.setValue(actualValue);
        Assertions.assertEquals((long) myProperty.getValue(), actualValue);

        actualValue = Integer.MAX_VALUE;
        myProperty.setValue(actualValue);
        Assertions.assertEquals((long) myProperty.getValue(), actualValue);

        actualValue = 0;
        myProperty.setValue(actualValue);
        Assertions.assertEquals((long) myProperty.getValue(), actualValue);
    }

    @Test
    void getPropertyName() {
        int defaultIntValue = 0;

        String actualValue = "propertyName1";
        myProperty = new Property<>(defaultIntValue, "propertyName1");
        Assertions.assertEquals(myProperty.getPropertyName(), actualValue);

        actualValue = "name 1 property";
        myProperty = new Property<>(defaultIntValue, actualValue);
        Assertions.assertEquals(myProperty.getPropertyName(), actualValue);

        actualValue = "randomName";
        myProperty = new Property<>(defaultIntValue, "notActual");
        Assertions.assertNotEquals(myProperty.getPropertyName(), actualValue);
    }

    @Test
    void addPropertyChangedListener() {
        myProperty = new Property<>(0, "propertyName");
        Consumer<Property<Integer>> listener = integerProperty -> System.out.println("Something");

        Assertions.assertTrue(myProperty.addPropertyChangedListener(listener));
        Assertions.assertFalse(myProperty.addPropertyChangedListener(listener));

        listener = integerProperty -> System.out.println("Another thing");

        Assertions.assertTrue(myProperty.addPropertyChangedListener(listener));
        Assertions.assertFalse(myProperty.addPropertyChangedListener(listener));
    }

    @Test
    void notifyOfPropertyChanged() {
        myProperty = new Property<>(0, "propertyName");
        myProperty.addPropertyChangedListener(integerProperty -> {
            throw new RuntimeException();
        });

        String expectedMessage = "Failed to perform action : ";

        Exception exception = Assertions.assertThrows(EventException.class, () -> myProperty.notifyOfPropertyChanged(myProperty));
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));

        myProperty.removeAlPropertyChangedListeners();
        myProperty.addPropertyChangedListener(integerProperty -> System.out.println("Something"));
        Assertions.assertDoesNotThrow(() -> myProperty.notifyOfPropertyChanged(myProperty));
    }

    @Test
    void testToString() {
        String responseFormat = "Property : {0}, with value = {1}";

        int intValue = 0;
        String propertyName = "myProperty";
        String actualValue = MessageFormat.format(responseFormat, propertyName, intValue);
        myProperty = new Property<>(intValue, propertyName);
        Assertions.assertEquals(myProperty.toString(), actualValue);

        intValue = Integer.MAX_VALUE;
        propertyName = "myPropertyName2";
        actualValue = MessageFormat.format(responseFormat, propertyName, intValue);
        myProperty = new Property<>(intValue, propertyName);
        Assertions.assertEquals(myProperty.toString(), actualValue);

        intValue = -1;
        propertyName = "Name of property 123451234 ASD";
        actualValue = MessageFormat.format(responseFormat, propertyName, intValue);
        myProperty = new Property<>(intValue, propertyName);
        Assertions.assertEquals(myProperty.toString(), actualValue);
    }
}
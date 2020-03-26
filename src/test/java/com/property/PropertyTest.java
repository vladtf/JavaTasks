package com.property;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.function.Consumer;

class PropertyTest {


    private Property<Integer> myProperty;

    @Test
    void setValue() {
        myProperty = new Property<>(1, "myProperty");

        Assert.assertEquals((long) myProperty.setValue(1), 1);
        Assert.assertEquals((long) myProperty.setValue(0), 1);
        Assert.assertEquals((long) myProperty.setValue(-1), 0);
        Assert.assertEquals((long) myProperty.setValue(Integer.MAX_VALUE), -1);
        Assert.assertEquals((long) myProperty.setValue(Integer.MIN_VALUE), Integer.MAX_VALUE);
    }

    @Test
    void getValue() {
        int actualValue = 1;
        myProperty = new Property<>(actualValue, "myProperty");

        Assert.assertEquals((long) myProperty.getValue(), actualValue);

        actualValue = 5;
        myProperty.setValue(actualValue);
        Assert.assertEquals((long) myProperty.getValue(), actualValue);

        actualValue = Integer.MAX_VALUE;
        myProperty.setValue(actualValue);
        Assert.assertEquals((long) myProperty.getValue(), actualValue);

        actualValue = 0;
        myProperty.setValue(actualValue);
        Assert.assertEquals((long) myProperty.getValue(), actualValue);
    }

    @Test
    void getPropertyName() {
        int defaultIntValue = 0;

        String actualValue = "propertyName1";
        myProperty = new Property<>(defaultIntValue, "propertyName1");
        Assert.assertEquals(myProperty.getPropertyName(), actualValue);

        actualValue = "name 1 property";
        myProperty = new Property<>(defaultIntValue, actualValue);
        Assert.assertEquals(myProperty.getPropertyName(), actualValue);

        actualValue = "randomName";
        myProperty = new Property<>(defaultIntValue, "notActual");
        Assert.assertNotEquals(myProperty.getPropertyName(), actualValue);
    }

    @Test
    void addPropertyChangedListener() {
        myProperty = new Property<>(0, "propertyName");
        Consumer<Property<Integer>> listener = integerProperty -> System.out.println("Something");

        Assert.assertTrue(myProperty.addPropertyChangedListener(listener));
        Assert.assertFalse(myProperty.addPropertyChangedListener(listener));

        listener = integerProperty -> System.out.println("Another thing");

        Assert.assertTrue(myProperty.addPropertyChangedListener(listener));
        Assert.assertFalse(myProperty.addPropertyChangedListener(listener));
    }

    @Test
    void notifyOfPropertyChanged() {
        // TODO: 26-Mar-20 Test on thros excpetions 
    }

    @Test
    void testToString() {
        String responseFormat = "Property : {0}, with value = {1}";

        int intValue = 0;
        String propertyName = "myProperty";
        String actualValue = MessageFormat.format(responseFormat, propertyName, intValue);
        myProperty = new Property<>(intValue, propertyName);
        Assert.assertEquals(myProperty.toString(), actualValue);

        intValue = Integer.MAX_VALUE;
        propertyName = "myPropertyName2";
        actualValue = MessageFormat.format(responseFormat, propertyName, intValue);
        myProperty = new Property<>(intValue, propertyName);
        Assert.assertEquals(myProperty.toString(), actualValue);

        intValue = -1;
        propertyName = "Name of property 123451234 ASD";
        actualValue = MessageFormat.format(responseFormat, propertyName, intValue);
        myProperty = new Property<>(intValue, propertyName);
        Assert.assertEquals(myProperty.toString(), actualValue);


    }
}
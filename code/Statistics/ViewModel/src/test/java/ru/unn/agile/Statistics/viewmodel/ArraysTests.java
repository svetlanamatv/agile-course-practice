package ru.unn.agile.Statistics.viewmodel;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArraysTests extends Core {
    @Test
    public void canSetAndGetValueUsingIndex() {
        int length = TEST_VALUES.length;
        vm.setArraysSize(length);
        setInputFields();

        for (int i = 0; i < length; i++) {
            vm.setValue(i, (double)i);
        }

        for (int i = 0; i < length; i++) {
            assertEquals(i, (int)vm.getValue(i));
        }
    }
}


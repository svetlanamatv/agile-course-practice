package ru.unn.agile.Statistics.viewmodel;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class ArraysTests extends Core {
    private static final double[]
            A0 = {0},
            A1 = {1},
            A10 = {1, 0},
            A12 = {1, 2};

    @Test
    public void incrementFrom0To1() {
        vm().setArraysSize(1);
        assertArrayEquals(A0, vm().getValues(), 0.0);
        assertArrayEquals(A0, vm().getPossibilities(), 0.0);
    }

    @Test
    public void incrementFrom1To2() {
        vm().setValues(A1);
        vm().setPossibilities(A1);
        vm().setArraysSize(2);
        assertArrayEquals(A10, vm().getValues(), 0.0);
        assertArrayEquals(A10, vm().getPossibilities(), 0.0);
    }

    @Test
    public void decrementFrom2To1() {
        vm().setValues(A12);
        vm().setPossibilities(A12);
        vm().setArraysSize(1);
        assertArrayEquals(A1, vm().getValues(), 0.0);
        assertArrayEquals(A1, vm().getPossibilities(), 0.0);
    }

    @Test
    public void decrementFrom1To0() {
        vm().setValues(A1);
        vm().setPossibilities(A1);
        vm().setArraysSize(0);
        assertArraysAreEmpty();
    }

    @Test
    public void canSetNegativeAndGetZero() {
        vm().setArraysSize(-1);
        assertArraysAreEmpty();
    }
}


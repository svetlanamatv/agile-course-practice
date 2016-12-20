package ru.unn.agile.treesort.model;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.*;

public class TreeSortTest {

    @Test
    public void testSortedCollectionIsNotNull() {
        TreeSort sort = new TreeSort();
        assertNotNull(sort.sort(new ArrayList<Integer>()));
    }

    @Test
    public void testSortEmptyCollection() {
        TreeSort sort = new TreeSort();
        Collection<Integer> outputCollection = sort.sort(new ArrayList<Integer>());
        assertTrue(outputCollection.isEmpty());
    }

    @Test
    public void testSizeOfSortCollectionWithSingleElement() {
        TreeSort sort = new TreeSort();
        Collection<Integer> inputCollection = new ArrayList<Integer>();
        inputCollection.add(1);

        Collection<Integer> outputCollection = sort.sort(inputCollection);
        assertEquals(inputCollection.size(), outputCollection.size());
    }

    @Test
    public void testSizeOfSortCollectionWithEqualsElements() {
        TreeSort sort = new TreeSort();
        Collection<Integer> inputCollection = new ArrayList<Integer>();
        inputCollection.add(2);
        inputCollection.add(2);
        inputCollection.add(2);
        inputCollection.add(2);

        Collection<Integer> outputCollection = sort.sort(inputCollection);
        assertEquals(inputCollection.size(), outputCollection.size());
    }

    @Test
    public void testSizeOfSortCollectionWithDiferentsElements() {
        TreeSort sort = new TreeSort();
        Collection<Integer> inputCollection = new ArrayList<Integer>();
        inputCollection.add(4);
        inputCollection.add(2);
        inputCollection.add(5);
        inputCollection.add(1);

        Collection<Integer> outputCollection = sort.sort(inputCollection);
        assertEquals(inputCollection.size(), outputCollection.size());
    }

    @Test
    public void testSortCollectionWithEqualsElements() {
        TreeSort sort = new TreeSort();
        Collection<Integer> inputCollection = new ArrayList<Integer>();
        inputCollection.add(10);
        inputCollection.add(10);
        inputCollection.add(10);

        Collection<Integer> outputCollection = sort.sort(inputCollection);
        for (Integer val : outputCollection) {
            assertEquals(Integer.valueOf(10), val);
        }
    }

    @Test
    public void testSortCollectionWithMultipleElements() {
        TreeSort sort = new TreeSort();
        Integer[] inputData = {20, 30, 10, 0};
        Integer[] sortedData = {0, 10, 20, 30};

        Collection<Integer> outputCollection = sort.sort(Arrays.asList(inputData));
        Integer[] outputArray = outputCollection.toArray(new Integer[outputCollection.size()]);
        for (int i = 0; i < outputArray.length; i++) {
            assertEquals(sortedData[i], outputArray[i]);
        }
    }
}

package ru.unn.agile.treesort.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.util.Collection;

public class TreeTest {
    @Test
    public void testCreateTreeNotNullCollection() {
        Tree tree = new Tree();
        assertNotNull(tree.extractValues());
    }

    @Test
    public void testCreateEmptyTree() {
        Tree tree = new Tree();
        Collection<Integer> extractedValues = tree.extractValues();
        assertTrue(extractedValues.isEmpty());
    }

    @Test
    public void testTreeWithSingleElement() {
        Tree tree = new Tree(10);
        assertEquals(1, tree.extractValues().size());
    }

    @Test
    public void testTreeSizeWithEqualsElement() {
        Tree tree = new Tree(10);
        Integer[] vals = {10, 10, 10};
        for (Integer v : vals) {
            tree.insert(v);
        }
        assertEquals(4, tree.extractValues().size());
    }

    @Test
    public void testTreeSizeWithDifferentElement() {
        Tree tree = new Tree(10);
        Integer[] vals = {40, 5, 20, 3};
        for (Integer v : vals) {
            tree.insert(v);
        }
        assertEquals(5, tree.extractValues().size());
    }

    @Test
    public void testSortingWithEqualsElement() {
        Tree tree = new Tree(10);
        Integer[] vals = {10, 10, 10};
        for (Integer v : vals) {
            tree.insert(v);
        }
        Collection<Integer> extractedValues = tree.extractValues();
        for (Integer v : extractedValues) {
            assertEquals(Integer.valueOf(10), v);
        }
    }

    @Test
    public void testSortingWithDifferentsElement() {
        Tree tree = new Tree(10);
        final Integer[] vals = {40, 5, 20, 3};
        final Integer[] cmpVals = {3, 5, 10, 20, 40};
        for (Integer v : vals) {
            tree.insert(v);
        }
        Collection<Integer> extractedValues = tree.extractValues();
        final Integer[] extrVals = extractedValues.toArray(new Integer[extractedValues.size()]);
        for (int i = 0; i < extractedValues.size(); i++) {
            assertEquals(cmpVals[i], extrVals[i]);
        }
    }
}

package ru.unn.agile.Tree.Model;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class BinaryTreeTests {
    private BinaryTreeNode treeNode;
    @Test
    public void testCreateEmptyTree() {
        BinaryTree emptyTree = new BinaryTree();
        Collection<Integer> values = emptyTree.getValues();
        assertTrue(values.isEmpty());
    }

    @Test
    public void testCreateTreeWithSingleElement() {
        BinaryTree oneElementTree = new BinaryTree(1);
        assertEquals(1, oneElementTree.getValues().size());
    }

    @Test
    public void testCreatedTreeIsNotNull() {
        BinaryTree tree = new BinaryTree();
        assertNotNull(tree.getValues());
    }

    @Test
    public void testCreateTreeWithOneZeroElement() {
        BinaryTree tree = new BinaryTree(0);
        LinkedList<Object> treeValues = new LinkedList<>();
        treeValues.add(0);
        assertEquals(treeValues, tree.getValues());
    }

    @Test
    public void testAddTwoDiffElementsToTree() {
        BinaryTree tree = new BinaryTree(5);
        tree.addNode(6);
        tree.addNode(1);
        assertEquals(3, tree.getValues().size());
    }

    @Test
    public void testAddFourEqualElementsToTree() {
        BinaryTree tree = new BinaryTree(5);
        tree.addNode(5);
        tree.addNode(5);
        tree.addNode(5);
        tree.addNode(5);
        assertEquals(5, tree.getValues().size());
    }

    @Test
    public void testSearchOneElementsExistInTree() {
        BinaryTree tree = new BinaryTree(5);
        assertEquals(1, tree.search(5));
    }

    @Test
    public void testSearchFourElementsExistsInTree() {
        BinaryTree tree = new BinaryTree(2);
        Integer[] values = {10, 2, 5, 6, 10, 20, 10, 9, 10};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertEquals(4, tree.search(10));
    }

    @Test
    public void testSearchElementDoNotExistInTree() {
        BinaryTree tree = new BinaryTree(1);
        Integer[] values = {10, 2, 5, 6, 10, 20, 10, 9, 10};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertEquals(0, tree.search(8));
    }

    @Test
    public void testRemoveExistingElementFromTree() {
        BinaryTree tree = new BinaryTree(6);
        Integer[] values = {1, 3, 2, 4, 0, 30, 50, 1, 40};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertTrue(tree.removeNode(2));
    }

    @Test
    public void testRemoveElementFromTreeThatNotExist() {
        BinaryTree tree = new BinaryTree(4);
        Integer[] values = {50, 1, 0, 2, 30, 77, 20, 9, 10};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertFalse(tree.removeNode(8));
    }

    @Test
    public void testCorrectTreeSizeAfterRemove() {
        BinaryTree tree = new BinaryTree(8);
        Integer[] values = {1, 25, 35, 26, 11, 20, 10, 9, 10};
        for (Integer v : values) {
            tree.addNode(v);
        }
        tree.removeNode(10);
        assertEquals(9, tree.getValues().size());
    }

    @Test
    public void testGetNullNode() {
        BinaryTree tree = new BinaryTree(2);
        Integer[] values = {1, 2, 3, 4};
        for (Integer v : values) {
            tree.addNode(v);
        }
        treeNode = tree.getNode(null);
        assertTrue(treeNode == null);
    }

    @Test
    public void testGetFirstRightNode() {
        BinaryTree tree = new BinaryTree(2);
        Integer[] values = {4, 3, 1, 2};
        for (Integer v : values) {
            tree.addNode(v);
        }
        treeNode = tree.getNode(2);
        assertEquals(treeNode.getRightNode(), tree.getNode(4));
    }

    @Test
    public void testCorrectDelete() {
        BinaryTree tree = new BinaryTree(5);
        Integer[] values = {4, 6, 7, 2, 3, 1};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertTrue(tree.removeNode(2));
        assertFalse(tree.removeNode(2));
        treeNode = tree.getNode(3);
        assertTrue(treeNode.getKey() == 3);
        assertTrue(treeNode.getRightNode() == null);
        assertTrue(treeNode.getLeftNode().getKey() == 1);
    }

    @Test
    public void testNodeDoNotExists() {
        BinaryTree tree = new BinaryTree(5);
        treeNode = tree.getNode(2);
        assertNull(treeNode);
    }

    @Test
    public void testRemoveLastNodeOnTheLeft() {
        BinaryTree tree = new BinaryTree(6);
        Integer[] values = {4, 7, 8, 2, 3, 1};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertTrue(tree.removeNode(1));
        treeNode = tree.getNode(2);
        assertTrue(treeNode.getKey() == 2);
        assertTrue(treeNode.getRightNode() == tree.getNode(3));
        assertTrue(treeNode.getLeftNode() == null);
    }

    @Test
    public void testRemoveLastNodeOnTheRight() {
        BinaryTree tree = new BinaryTree(4);
        Integer[] values = {3, 6, 7, 2, 3, 1};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertTrue(tree.removeNode(3));
        treeNode = tree.getNode(2);
        assertTrue(treeNode.getKey() == 2);
        assertTrue(treeNode.getRightNode() == null);
        assertTrue(treeNode.getLeftNode() == tree.getNode(1));
    }

    @Test
    public void testRemoveNodeWithTwoChild() {
        BinaryTree tree = new BinaryTree(5);
        Integer[] values = {2, -4, 3, 12, 9, 21, 19, 25};
        for (Integer v : values) {
            tree.addNode(v);
        }
        assertTrue(tree.removeNode(12));
        treeNode = tree.getNode(19);
        assertTrue(treeNode.getKey() == 19);
        assertTrue(treeNode.getRightNode() == tree.getNode(21));
        assertTrue(treeNode.getLeftNode() == tree.getNode(9));

    }
}

package ru.unn.agile.Tree.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BinaryTreeNodeTests {
    @Test
    public void testCreateNodeWithZeroValue() {
        BinaryTreeNode zeroNode = new BinaryTreeNode(0);
        assertEquals(0, zeroNode.getKey());
    }

    @Test
    public void testSetLeftNode() {
        BinaryTreeNode leftNode = new BinaryTreeNode(0);
        leftNode.setLeftNode(new BinaryTreeNode(10));
        assertNotNull(leftNode.getLeftNode());
    }

    @Test
    public void testSetRightNode() {
        BinaryTreeNode rightNode = new BinaryTreeNode(0);
        rightNode.setRightNode(new BinaryTreeNode(20));
        assertNotNull(rightNode.getRightNode());
    }
}

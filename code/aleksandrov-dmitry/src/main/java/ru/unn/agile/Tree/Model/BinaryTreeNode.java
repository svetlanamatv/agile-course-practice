package ru.unn.agile.Tree.Model;

public class BinaryTreeNode {
    private final int key;
    private BinaryTreeNode leftNode;
    private BinaryTreeNode rightNode;


    public BinaryTreeNode(final int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setLeftNode(final BinaryTreeNode node) {
        leftNode = node;
    }

    public BinaryTreeNode getLeftNode() {
        return leftNode;
    }

    public void setRightNode(final BinaryTreeNode node) {
        rightNode = node;
    }

    public BinaryTreeNode getRightNode() {
        return rightNode;
    }

    public boolean isLeaf() {
        return (leftNode == null && rightNode == null);
    }
}

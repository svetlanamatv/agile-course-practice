package ru.unn.agile.treesort.model;

public class TreeNode {
    private final Integer key;
    private TreeNode leftTreeNode;
    private TreeNode rightTreeNode;

    public TreeNode(final Integer key) {
        if (null == key) {
            throw new IllegalArgumentException("NULL not supported for TreeNode key");
        }
        this.key = key;
    }

    public Integer getKey() {
        return key;
    }

    public TreeNode getLeftNode() {
        return leftTreeNode;
    }

    public void setLeftNode(final TreeNode node) {
        leftTreeNode = node;
    }

    public TreeNode getRightNode() {
        return rightTreeNode;
    }

    public void setRightNode(final TreeNode node) {
        rightTreeNode = node;
    }
}

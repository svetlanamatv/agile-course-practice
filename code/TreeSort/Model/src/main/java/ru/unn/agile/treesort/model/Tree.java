package ru.unn.agile.treesort.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

public class Tree {
    private TreeNode rootNode;

    /**
     * Default constructor. Initializes empty tree without nodes.
     */
    public Tree() {
        //the are no any initializations here, an empty tree will be created without any nodes.
    }

    public Tree(final Integer key) {
        rootNode = new TreeNode(key);
    }

    public void insert(final Integer key) {
        if (key == null) {
            throw new IllegalArgumentException("NULL not supported for Tree node value");
        }
        if (rootNode == null) {
            rootNode = new TreeNode(key);
        } else {
            TreeNode prevNode = null;
            TreeNode nextNode = rootNode;
            int cmpResult = 0;
            while (nextNode != null) {
                prevNode = nextNode;
                cmpResult = key.compareTo(nextNode.getKey());
                nextNode = (cmpResult < 0) ? nextNode.getLeftNode() : nextNode.getRightNode();
            }
            if (cmpResult < 0) { // node.key < key
                prevNode.setLeftNode(new TreeNode(key));
            } else {
                prevNode.setRightNode(new TreeNode(key));
            }
        }
    }

    public Collection<Integer> extractValues() {
        final Collection<Integer> extractedValues = new LinkedList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode top = rootNode;
        while (top != null || !stack.empty()) {
            if (!stack.empty()) {
                top = stack.pop();
                extractedValues.add(top.getKey());
                top = top.getRightNode() == null ? null : top.getRightNode();
            }
            while (top != null) {
                stack.push(top);
                top = top.getLeftNode();
            }
        }
        return extractedValues;
    }
}

package ru.unn.agile.Tree.Model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

public class BinaryTree {
    private BinaryTreeNode rootNode;

    public BinaryTree(final int key) {
        rootNode = new BinaryTreeNode(key);
    }

    public BinaryTree() {
        // Constructor for empty tree
    }

    public Collection<Integer> getValues() {
        final Collection<Integer> values = new LinkedList<>();
        Stack<BinaryTreeNode> nodeStack = new Stack<>();
        BinaryTreeNode top = rootNode;
        while (top != null || !nodeStack.isEmpty()) {
            if (!nodeStack.isEmpty()) {
                top = nodeStack.pop();
                values.add(top.getKey());
                top = top.getRightNode() == null ? null : top.getRightNode();
            }
            while (top != null) {
                nodeStack.push(top);
                top = top.getLeftNode();
            }
        }
        return values;
    }

    public void addNode(final Integer key) {
        BinaryTreeNode prevNode = null;
        BinaryTreeNode nextNode = rootNode;
        int compare = 0;
        while (nextNode != null) {
            prevNode = nextNode;
            compare = key.compareTo(nextNode.getKey());
            nextNode = (compare < 0) ? nextNode.getLeftNode() : nextNode.getRightNode();
        }
        if (compare < 0) { // node.key < key
            prevNode.setLeftNode(new BinaryTreeNode(key));
        } else {
            prevNode.setRightNode(new BinaryTreeNode(key));
        }
    }

    public int search(final Integer key) {
        BinaryTreeNode currentNode = rootNode;
        int result = 0;
        int compare = 0;
        while (currentNode != null) {
            compare = key.compareTo(currentNode.getKey());
            if (compare == 0) {
                result++;
            }
            currentNode = (compare < 0) ? currentNode.getLeftNode() : currentNode.getRightNode();
        }
        return result;
    }

    public boolean removeNode(final Integer key) {
        BinaryTreeNode target = null;
        BinaryTreeNode parent = null;
        BinaryTreeNode node = rootNode;
        while (node != null) {
            if (key.compareTo(node.getKey()) == 0) {
                target = node;
                break;
            } else if (key.compareTo(node.getKey()) > 0) {
                parent = node;
                node = node.getRightNode();
            } else {
                parent = node;
                node = node.getLeftNode();
            }
        }
        if (target == null) {
            return false;
        }

        boolean isLeft = (target == parent.getLeftNode());

        if (target.equals(rootNode)) {
            node = getLastNodeOnTheLeft(parent.getRightNode());
            if (node != null) {
                node.setLeftNode(parent.getLeftNode());
                node.setRightNode(parent.getRightNode());
                rootNode = node;
            }
        } else if (target.isLeaf()) {
            if (isLeft) {
                parent.setLeftNode(null);
            } else {
                parent.setRightNode(null);
            }
        } else if (target.getLeftNode() == null) {
            if (isLeft) {
                parent.setLeftNode(target.getLeftNode());
            } else {
                parent.setRightNode((target.getLeftNode()));
            }
        } else if (target.getRightNode() == null) {
            if (isLeft) {
                parent.setLeftNode(target.getRightNode());
            } else {
                parent.setRightNode(target.getRightNode());
            }
        } else {
            if (isLeft) {
                parent.setLeftNode(target.getRightNode());
                parent.getLeftNode().setLeftNode(target.getLeftNode());
            } else {
                parent.setRightNode(target.getRightNode());
                parent.getRightNode().setLeftNode(target.getLeftNode());
            }
        }
        return true;
    }

    private BinaryTreeNode getLastNodeOnTheLeft(final BinaryTreeNode nodeTree) {
        BinaryTreeNode candidate = null;
        BinaryTreeNode parent = null;
        BinaryTreeNode node = nodeTree;

        while (node != null) {
            if (node.getLeftNode() != null) {
                parent = node;
                candidate = node.getLeftNode();
            }

            node = node.getLeftNode();
        }
        if (parent != null) {
            parent.setLeftNode(null);
        }
        return candidate;
    }
}

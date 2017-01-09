package ru.unn.agile.Tree.Model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {
    private BinaryTreeNode rootNode;

    public BinaryTree(final int key) {
        rootNode = new BinaryTreeNode(key);
    }

    public BinaryTree() {
        // Constructor for empty tree
    }

    public BinaryTreeNode getRoot() {
        return rootNode;
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
        if (key == null) {
            throw new IllegalArgumentException("NULL not supported for Tree node value");
        }
        if (rootNode == null) {
            rootNode = new BinaryTreeNode(key);
        } else {
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
        if (search(key) == 0) {
            return false;
        } else {
            this.rootNode = removeNode(this.rootNode, key);
            return true;
        }
    }

    private BinaryTreeNode removeNode(final BinaryTreeNode node, final Integer key) {
        BinaryTreeNode treeNode = node;
        if (treeNode.getKey() < key) {
            treeNode.setRightNode(removeNode(treeNode.getRightNode(), key));
        } else if (treeNode.getKey() > key) {
            treeNode.setLeftNode(removeNode(treeNode.getLeftNode(), key));
        } else {
            if (treeNode.getRightNode() == null) {
                return treeNode.getLeftNode();
            }
            if (treeNode.getLeftNode() == null) {
                return treeNode.getRightNode();
            }
            BinaryTreeNode temp = treeNode;
            treeNode = min(temp.getRightNode());
            treeNode.setRightNode(deleteMin(temp.getRightNode()));
            treeNode.setLeftNode(temp.getLeftNode());
        }
        return treeNode;
    }

    private BinaryTreeNode deleteMin(final BinaryTreeNode node) {
        if (node.getLeftNode() == null) {
            return node.getRightNode();
        }
        node.setLeftNode(deleteMin(node.getLeftNode()));
        return node;
    }

    private BinaryTreeNode min(final BinaryTreeNode node) {
        if (node.getLeftNode() == null) {
            return node;
        } else {
            return min(node.getLeftNode());
        }
    }

    public BinaryTreeNode getNode(final Integer key) {
        if (key == null) {
            return null;
        }
        BinaryTreeNode node = rootNode;
        int compare;
        while (node != null) {
            compare = node.getKey().compareTo(key);
            if (compare == 0) {
                return node;
            }
            if (compare > 0) {
                node = node.getLeftNode();
            } else {
                node = node.getRightNode();
            }
        }

        return node;
    }

    public String levelOrderPrint(final BinaryTreeNode treeNode) {
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(treeNode);
        String str = new String();
        while (!queue.isEmpty()) {
            BinaryTreeNode tempNode = queue.poll();
            str += tempNode.getKey().toString();
            if (tempNode.getLeftNode() != null) {
                queue.add(tempNode.getLeftNode());
            }
            if (tempNode.getRightNode() != null) {
                queue.add(tempNode.getRightNode());
            }
            if (!queue.isEmpty()) {
                str += ',';
            }
        }
        return str;

    }
}

package ru.unn.agile.BinaryTree.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.Tree.Model.BinaryTree;
import ru.unn.agile.Tree.Model.BinaryTreeNode;

public class BinaryTreeViewModel {
    private final StringProperty addNode = new SimpleStringProperty();
    private final StringProperty remNode = new SimpleStringProperty();
    private final StringProperty getNode = new SimpleStringProperty();
    private final StringProperty search = new SimpleStringProperty();
    private final StringProperty searchResult = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final BinaryTree tree = new BinaryTree();
    private final StringProperty sourceTree = new SimpleStringProperty();

    public BinaryTreeViewModel() {
        addNode.set("");
        remNode.set("");
        getNode.set("");
        search.set("");
        sourceTree.set("");
        searchResult.set("");
        status.set(Status.WAITING.toString());
    }

    public StringProperty addNodeProperty() {
        return addNode;
    }

    public StringProperty remNodeProperty() {
        return remNode;
    }

    public StringProperty getNodeProperty() {
        return getNode;
    }

    public StringProperty searchProperty() {
        return search;
    }

    public StringProperty searchResultProperty() {
        return searchResult;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getSearchResult() {
        return searchResult.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public StringProperty sourceTreeProperty() {
        return sourceTree;
    }

    public final String getSourceTree() {
        return sourceTree.get();
    }

    public void addNodeToTree() {
        try {
            Integer number = Integer.parseInt(addNode.get());
            tree.addNode(number);
            status.set(Status.ELEMENT_ADDED.toString());
        } catch (NumberFormatException e) {
            status.set(Status.BAD_FORMAT.toString());
        }
    }

    public void searchNode() {
        if (tree.getValues().isEmpty()) {
            status.set(Status.EMPTY_TREE.toString());
        } else {
            try {
                Integer key = Integer.parseInt(search.get());
                Integer number = tree.search(key);
                if (number == 0) {
                    searchResult.set("");
                    status.set(Status.ELEMENT_NOT_FOUND.toString());
                } else {
                    searchResult.set(number.toString());
                    status.set(Status.ELEMENT_FOUND.toString());
                }
            } catch (NumberFormatException e) {
                status.set(Status.BAD_FORMAT.toString());
            }
        }
    }

    public void remNodeFromTree() {
        if (tree.getValues().isEmpty()) {
            status.set(Status.EMPTY_TREE.toString());
        } else {
            try {
                Integer key = Integer.parseInt(remNode.get());
                boolean result = tree.removeNode(key);
                if (result) {
                    status.set(Status.ELEMENT_DELETED.toString());
                } else {
                    status.set(Status.ELEMENT_NOT_FOUND.toString());
                }
            } catch (NumberFormatException e) {
                status.set(Status.BAD_FORMAT.toString());
            }
        }
    }

    public void getNodeFromTree() {
        if (tree.getValues().isEmpty()) {
            status.set(Status.EMPTY_TREE.toString());
        } else {
            try {
                Integer key = Integer.parseInt(getNode.get());
                BinaryTreeNode node = tree.getNode(key);
                if (node == null) {
                    status.set(Status.ELEMENT_NOT_FOUND.toString());
                } else {
                    status.set(Status.NODE_SUCCESSFUL_GOT.toString());
                }
            } catch (NumberFormatException e) {
                status.set(Status.BAD_FORMAT.toString());
            }
        }
    }

    public void printSourceTree() {
        if (tree.getValues().isEmpty()) {
            sourceTree.set("");
        } else {
            sourceTree.set(tree.levelOrderPrint(tree.getRoot()));
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    EMPTY_TREE("Tree is empty! Can't search"),
    BAD_FORMAT("Bad format!"),
    ELEMENT_NOT_FOUND("Element Not Found!"),
    ELEMENT_DELETED("Element successfully deleted!"),
    NODE_SUCCESSFUL_GOT("Node successfully got! "),
    ELEMENT_ADDED("Element successfully added to tree!"),
    ELEMENT_FOUND("Element successfully found in tree!");

    private final String status;

    Status(final String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }
}

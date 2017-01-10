package ru.unn.agile.BinaryTree.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.Tree.Model.BinaryTree;
import ru.unn.agile.Tree.Model.BinaryTreeNode;

import java.util.List;

public class BinaryTreeViewModel {
    private final StringProperty addNode = new SimpleStringProperty();
    private final StringProperty remNode = new SimpleStringProperty();
    private final StringProperty getNode = new SimpleStringProperty();
    private final StringProperty search = new SimpleStringProperty();

    private final StringProperty searchResult = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private final BinaryTree tree = new BinaryTree();
    private final StringProperty sourceTree = new SimpleStringProperty();

    private ILogger logger;

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public BinaryTreeViewModel() {
        init();
    }

    public BinaryTreeViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        addNode.set("");
        remNode.set("");
        getNode.set("");
        search.set("");
        sourceTree.set("");
        searchResult.set("");
        logs.set("");
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

    public StringProperty getLogsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public void addNodeToTree() {
        StringBuilder messageAddNode = new StringBuilder(LogMessages.ADD_NODE_PRESSED);
        messageAddNode.append(addNode.get()).append(" to Tree! ");
        try {
            Integer number = Integer.parseInt(addNode.get());
            tree.addNode(number);
            messageAddNode.append(Status.ELEMENT_ADDED.toString());
            status.set(Status.ELEMENT_ADDED.toString());
        } catch (NumberFormatException e) {
            messageAddNode.append(Status.BAD_FORMAT.toString());
            status.set(Status.BAD_FORMAT.toString());
        }
        logger.log(messageAddNode.toString());
        updateLogs();
    }

    public void searchNode() {
        StringBuilder messageSearchNode = new StringBuilder(LogMessages.SEARCH_NODE_PRESSED);
        messageSearchNode.append(search.get()).append(" in Tree! ");
        if (tree.getValues().isEmpty()) {
            messageSearchNode.append(Status.EMPTY_TREE.toString());
            status.set(Status.EMPTY_TREE.toString());
        } else {
            try {
                Integer key = Integer.parseInt(search.get());
                Integer number = tree.search(key);
                if (number == 0) {
                    searchResult.set("");
                    messageSearchNode.append(Status.ELEMENT_NOT_FOUND.toString());
                    status.set(Status.ELEMENT_NOT_FOUND.toString());
                } else {
                    searchResult.set(number.toString());
                    messageSearchNode.append(Status.ELEMENT_FOUND.toString());
                    status.set(Status.ELEMENT_FOUND.toString());
                }
            } catch (NumberFormatException e) {
                messageSearchNode.append(Status.BAD_FORMAT.toString());
                status.set(Status.BAD_FORMAT.toString());
            }
        }
        logger.log(messageSearchNode.toString());
        updateLogs();
    }

    public void remNodeFromTree() {
        StringBuilder messageRemNode = new StringBuilder(LogMessages.REM_NODE_PRESSED);
        messageRemNode.append(remNode.get()).append(" from Tree! ");
        if (tree.getValues().isEmpty()) {
            messageRemNode.append(Status.EMPTY_TREE.toString());
            status.set(Status.EMPTY_TREE.toString());
        } else {
            try {
                Integer key = Integer.parseInt(remNode.get());
                boolean result = tree.removeNode(key);
                if (result) {
                    messageRemNode.append(Status.ELEMENT_DELETED.toString());
                    status.set(Status.ELEMENT_DELETED.toString());
                } else {
                    messageRemNode.append(Status.ELEMENT_NOT_FOUND.toString());
                    status.set(Status.ELEMENT_NOT_FOUND.toString());
                }
            } catch (NumberFormatException e) {
                messageRemNode.append(Status.BAD_FORMAT.toString());
                status.set(Status.BAD_FORMAT.toString());
            }
        }
        logger.log(messageRemNode.toString());
        updateLogs();
    }

    public void getNodeFromTree() {
        StringBuilder messageGetNode = new StringBuilder(LogMessages.GET_NODE_PRESSED);
        messageGetNode.append(getNode.get()).append(" from Tree! ");
        if (tree.getValues().isEmpty()) {
            messageGetNode.append(Status.EMPTY_TREE.toString());
            status.set(Status.EMPTY_TREE.toString());
        } else {
            try {
                Integer key = Integer.parseInt(getNode.get());
                BinaryTreeNode node = tree.getNode(key);
                if (node == null) {
                    messageGetNode.append(Status.ELEMENT_NOT_FOUND.toString());
                    status.set(Status.ELEMENT_NOT_FOUND.toString());
                } else {
                    messageGetNode.append(Status.NODE_SUCCESSFUL_GOT.toString());
                    status.set(Status.NODE_SUCCESSFUL_GOT.toString());
                }
            } catch (NumberFormatException e) {
                messageGetNode.append(Status.BAD_FORMAT.toString());
                status.set(Status.BAD_FORMAT.toString());
            }
        }
        logger.log(messageGetNode.toString());
        updateLogs();
    }

    private void updateLogs() {
        List<String> allLogMessages = logger.getLog();
        String logMessage = new String();
        for (String log : allLogMessages) {
            logMessage += log + "\n";
        }
        logs.set(logMessage);
    }

    public void printSourceTree() {
        if (tree.getValues().isEmpty()) {
            sourceTree.set("");
        } else {
            sourceTree.set(tree.levelOrderPrint(tree.getRoot()));
        }
    }

    public final List<String> getLogger() {
        return logger.getLog();

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

final class LogMessages {
    public static final String ADD_NODE_PRESSED = "Add Node. ";
    public static final String REM_NODE_PRESSED = "Remove Node. ";
    public static final String GET_NODE_PRESSED = "Get Node. ";
    public static final String SEARCH_NODE_PRESSED = "Search Node. ";

    private LogMessages() {

    }
}

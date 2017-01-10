package ru.unn.agile.BinaryTree.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.BinaryTree.infrastructure.FileTxtLogger;
import ru.unn.agile.BinaryTree.viewmodel.BinaryTreeViewModel;


public class BinaryTree {
    @FXML
    private BinaryTreeViewModel viewModel;
    @FXML
    private TextField txtAddNode;
    @FXML
    private TextField txtRemNode;
    @FXML
    private TextField txtGetNode;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnAddNode;
    @FXML
    private Button btnRemNode;
    @FXML
    private Button btnGetNode;
    @FXML
    private Button btnSearch;
    @FXML
    private TextArea areaLog;

    @FXML
    void initialize() {
        viewModel.setLogger(new FileTxtLogger("./treeFile.log"));
        txtAddNode.textProperty().bindBidirectional(viewModel.addNodeProperty());
        txtRemNode.textProperty().bindBidirectional(viewModel.remNodeProperty());
        txtGetNode.textProperty().bindBidirectional(viewModel.getNodeProperty());
        txtSearch.textProperty().bindBidirectional(viewModel.searchProperty());
        areaLog.textProperty().bindBidirectional(viewModel.getLogsProperty());

        btnAddNode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.addNodeToTree();
                viewModel.printSourceTree();
            }
        });

        btnRemNode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.remNodeFromTree();
                viewModel.printSourceTree();
            }
        });

        btnGetNode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.getNodeFromTree();
                viewModel.printSourceTree();
            }
        });

        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.searchNode();
            }
        });
    }
}
